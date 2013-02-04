package by.deniotokiari.lastfmmusicplay.content.json.lastfm;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Track extends CommonJson {

	public static final String ROOT_LIBRARY_TRACKS = "tracks";
	public static final String ROOT_LOVED_TRACKS = "lovedtracks";
	public static final String ROOT_ARTIST_TOP_TRACKS = "toptracks";
	public static final String ROOT_ALBUM_TRACKS = "album";
	
	public static final String ITEM_ROOT_ALBUM_TRACKS = "tracks";
	
	public static final String ITEM = "track";

	public static final String KEY_TITLE = "name";
	public static final String KEY_ROOT_ARTIST = "artist";
	public static final String KEY_ARTIST = "name";
	public static final String KEY_ALBUM_ART_ROOT = "image";
	public static final String KEY_ALBUM_ART = "#text";
	public static final String KEY_ALBUM_ROOT = "album";
	public static final String KEY_ALBUM = "name";
	public static final String KEY_RANK_LIBRARY_TRACK = "playcount";
	public static final String KEY_RANK_ROOT_LOVED_TRACK = "date";
	public static final String KEY_RANK_LOVED_TRACK = "uts";
	public static final String KEY_RANK_ROOT_ARTIST_TOP_TRACK = "@attr";
	public static final String KEY_RANK_ARTIST_TOP_TRACK = "rank";

	private String title;
	private String artist;
	private String albumArt;

	public Track(String source) {
		super(source);
		title = getString(KEY_TITLE);
		artist = getString(KEY_ROOT_ARTIST, KEY_ARTIST);
		albumArt = getArrayItem(KEY_ALBUM_ART_ROOT, KEY_ALBUM_ART, 3);
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbumArt() {
		return albumArt;
	}

	public String getAlbum() {
		String album = getString(KEY_ALBUM_ROOT);
		if (album.trim().length() > 0) {
			return album;
		}
		return getString(KEY_ALBUM_ROOT, KEY_ALBUM);
	}

	public String getRank() {
		String rank = getString(KEY_RANK_ROOT_ARTIST_TOP_TRACK,
				KEY_RANK_ARTIST_TOP_TRACK);
		if (rank.trim().length() > 0) {
			return rank;
		}
		rank = getString(KEY_RANK_ROOT_LOVED_TRACK, KEY_RANK_LOVED_TRACK);
		if (rank.trim().length() > 0) {
			return rank;
		}
		rank = getString(KEY_RANK_LIBRARY_TRACK);
		if (rank.trim().length() > 0) {
			return rank;
		}
		return "";
	}

}
