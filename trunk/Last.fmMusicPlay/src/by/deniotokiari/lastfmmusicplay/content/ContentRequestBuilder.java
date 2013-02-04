package by.deniotokiari.lastfmmusicplay.content;

import java.io.Serializable;

import android.net.Uri;

public class ContentRequestBuilder implements Serializable {

	private static final long serialVersionUID = 3529309877538503578L;
	private String mUrl;
	private String[] mKeys;
	private String mUri;
	private String mId;

	public String getUri() {
		return mUri;
	}

	public ContentRequestBuilder setUri(Uri uri) {
		this.mUri = uri.toString();
		return this;
	}

	public String getUrl() {
		return mUrl;
	}

	public ContentRequestBuilder setUrl(String url) {
		this.mUrl = url;
		return this;
	}

	public String[] getKeys() {
		return mKeys;
	}

	public ContentRequestBuilder setKeys(String[] keys) {
		this.mKeys = keys;
		return this;
	}
	
	public ContentRequestBuilder setId(String id) {
		mId = id;
		return this;
	}
	
	public String getId() {
		return mId;
	}

}
