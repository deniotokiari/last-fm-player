package by.deniotokiari.lastfmmusicplay.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpManager {

	private static HttpManager instance;
	private DefaultHttpClient client;
	public static final int SO_TIMEOUT = 20000;
	private static final String UTF_8 = "UTF-8";

	public static enum TYPE {
		POST, GET
	};

	private HttpManager() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, UTF_8);
		params.setBooleanParameter("http.protocol.expect-continue", false);
		HttpConnectionParams.setConnectionTimeout(params, SO_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		final SSLSocketFactory sslSocketFactory = SSLSocketFactory
				.getSocketFactory();
		sslSocketFactory
				.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		registry.register(new Scheme("https", sslSocketFactory, 443));
		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				params, registry);
		client = new DefaultHttpClient(manager, params);
	}

	public static HttpManager getInstance() {
		if (instance == null) {
			instance = new HttpManager();
		}
		return instance;
	}

	public String loadAsString(String url, TYPE name) throws IOException {
		if (name.equals(TYPE.GET)) {
			return loadAsString(new HttpGet(url));
		} else if (name.equals(TYPE.POST)) {
			return loadAsString(new HttpPost(url));
		}
		return null;

	}

	public String loadAsString(HttpRequestBase request) throws IOException {
		InputStream content = null;
		BufferedReader reader = null;
		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				String entityValue = null;
				entityValue = EntityUtils.toString(response.getEntity());
				throw new IOException(response.getStatusLine()
						.getReasonPhrase()
						+ " "
						+ entityValue
						+ " "
						+ statusCode);
			}
			content = response.getEntity().getContent();
			Header contentEncoding = response
					.getFirstHeader("Content-Encoding");
			if (contentEncoding != null
					&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
				reader = new BufferedReader(new InputStreamReader(
						new GZIPInputStream(content)), 8192);
			} else {
				reader = new BufferedReader(new InputStreamReader(content),
						8192);
			}
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line)
							.append(System.getProperty("line.separator"));
				}
			} catch (IOException e) {
				throw e;
			}
			String value = sb.toString();
			content.close();
			return value;
		} catch (IOException e) {
			throw e;
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}

	public Bitmap loadAsBitmap(String url) {
		return loadAsBitmap(new HttpGet(url));
	}

	public Bitmap loadAsBitmap(HttpRequestBase request) {
		InputStream content = null;
		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				String entityValue = null;
				entityValue = EntityUtils.toString(response.getEntity());
				throw new IOException(response.getStatusLine()
						.getReasonPhrase()
						+ " "
						+ entityValue
						+ " "
						+ statusCode);
			}
			content = response.getEntity().getContent();
			Bitmap bitmap = BitmapFactory.decodeStream(content);
			return bitmap;
		} catch (IOException e) {
			return null;
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
				}
			}
		}
	}

}