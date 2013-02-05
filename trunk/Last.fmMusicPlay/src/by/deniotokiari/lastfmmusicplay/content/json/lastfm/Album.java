package by.deniotokiari.lastfmmusicplay.content.json.lastfm;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Album extends CommonJson {

	public static final String EXTRA_ATTRIBUTES = "attr";

	public static final String ROOT_LIBRARY_ALBUMS = "albums";
	public static final String ROOT_ARTIST_TOP_ALBUMS = "topalbums";
	public static final String ROOT_TAG_TOP_ALBUMS = "topalbums";

	public static final String ITEM = "album";

	public static final String KEY_NAME = "name";
	public static final String KEY_RANK_LIBRARY_ALBUMS = "playcount";
	public static final String KEY_ROOT_ARTIST = "artist";
	public static final String KEY_ARTIST = "name";
	public static final String KEY_ROOT_IMAGE = "image";
	public static final String KEY_IMAGE = "#text";

	public static final String KEY_ROOT_RANK_ARTIST_TOP_ALBUMS = "@attr";
	public static final String KEY_RANK_ARTIST_TOP_ALBUMS = "#text";
	public static final String KEY_RANK_TAG_TRACKS = "rank";
	public static final String KEY_TAG = "tag";

	private String name;

	public Album(String source) {
		super(source);
		name = getString(KEY_NAME);
	}

	public String getName() {
		return name;
	}

	public String getRank() {
		String rank = getString(KEY_ROOT_RANK_ARTIST_TOP_ALBUMS,
				KEY_RANK_ARTIST_TOP_ALBUMS);
		if (rank.trim().length() > 0) {
			return rank;
		}
		rank = getString(KEY_ROOT_RANK_ARTIST_TOP_ALBUMS, KEY_RANK_TAG_TRACKS);
		if (rank.trim().length() > 0) {
			return rank;
		}
		return getString(KEY_RANK_LIBRARY_ALBUMS);
	}

	public String getArtist() {
		return getString(KEY_ROOT_ARTIST, KEY_ARTIST);
	}

	public String getImage() {
		return getArrayItem(KEY_ROOT_IMAGE, KEY_IMAGE, 2);
	}

	public String getTag() {
		return getString(EXTRA_ATTRIBUTES, KEY_TAG);
	}

}
