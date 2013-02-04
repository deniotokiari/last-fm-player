package by.deniotokiari.lastfmmusicplay.content.json.lastfm;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Artist extends CommonJson {

	public static final String ROOT = "artists";
	public static final String ITEM = "artist";
	
	public static final String KEY_NAME = "name";
	public static final String KEY_RANK = "playcount";
	public static final String KEY_ROOT_IMAGE = "image";
	public static final String KEY_IMAGE = "#text";
	
	private String name;
	private String rank;
	
	public Artist(String source) {
		super(source);
		name = getString(KEY_NAME);
		rank = getString(KEY_RANK);
	}
	
	public String getName() {
		return name;
	}
	
	public String getRank() {
		return rank;
	}
	
	public String getImage() {
		return getArrayItem(KEY_ROOT_IMAGE, KEY_IMAGE, 2);
	}
	
}
