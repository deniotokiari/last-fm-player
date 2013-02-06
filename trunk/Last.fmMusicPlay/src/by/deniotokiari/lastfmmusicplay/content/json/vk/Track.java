package by.deniotokiari.lastfmmusicplay.content.json.vk;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Track extends CommonJson {

	public static final String ROOT = "response";

	public static final String KEY_ARTIST = "artist";
	public static final String KEY_TITLE = "title";

	private String artist;
	private String title;

	public Track(String source) {
		super(source);
		artist = getString(KEY_ARTIST);
		title = getString(KEY_TITLE);
	}

	public String getArtist() {
		return artist;
	}

	public String getTitle() {
		return title;
	}

}
