package by.deniotokiari.lastfmmusicplay.content.json.lastfm;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Artist extends CommonJson {

	public static final String EXTRA_ATTRIBUTES = "attr";
	
	public static final String ROOT = "artists";
	public static final String ITEM = "artist";
	public static final String ROOT_TAG_ARTISTS = "topartists";

	public static final String KEY_NAME = "name";
	public static final String KEY_RANK = "playcount";
	public static final String KEY_ROOT_IMAGE = "image";
	public static final String KEY_IMAGE = "#text";
	public static final String KEY_ROOT_TAG_RANK = "@attr";
	public static final String KEY_TAG_RANK = "rank";
	public static final String KEY_TAG = "tag";
	public static final int IMAGE_NUMBER = 2;

	private String name;

	public Artist(String source) {
		super(source);
		name = getString(KEY_NAME);
	}

	public String getName() {
		return name;
	}

	public String getRank() {
		String rank = getString(KEY_ROOT_TAG_RANK, KEY_TAG_RANK);
		if (rank.trim().length() > 0) {
			return rank;
		}
		return getString(KEY_RANK);
	}

	public String getImage() {
		return getArrayItem(KEY_ROOT_IMAGE, KEY_IMAGE, IMAGE_NUMBER);
	}
	
	public String getTag() {
		return getString(EXTRA_ATTRIBUTES, KEY_TAG);
	}

}
