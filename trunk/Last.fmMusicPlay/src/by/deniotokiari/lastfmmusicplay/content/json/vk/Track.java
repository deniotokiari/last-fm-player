package by.deniotokiari.lastfmmusicplay.content.json.vk;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Track extends CommonJson {

	public static final String ROOT = "response";
	public static final String KEY_URL = "url";

	private String url;

	public Track(Object t) {
		super((String) t);
		url = getArrayItem(ROOT, KEY_URL, 1);
	}

	public String getUrl() {
		return url;
	}

}
