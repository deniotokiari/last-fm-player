package by.deniotokiari.lastfmmusicplay.content.json.lastfm;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Playlist extends CommonJson {
	
	public static final String ROOT = "playlists";
	public static final String ITEM = "playlist";
	
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";	
	public static final String KEY_SIZE = "size";

	private String id;
	private String title;
	private String size;
	
	public Playlist(String source) {
		super(source);
		id = getString(KEY_ID);
		title = getString(KEY_TITLE);
		size = getString(KEY_SIZE);
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSize() {
		return size;
	}
	
}
