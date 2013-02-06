package by.deniotokiari.lastfmmusicplay.content.json.other;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Track extends CommonJson {

	public static final String ROOT = "trackList";
	
	public static final String KEY_PLAYLIST_ID = "playlistid";
	public static final String KEY_ROOT_ARTIST = "artist";
	public static final String KEY_ARTIST = "name";
	public static final String KEY_TITLE = "title";
	
	private String playlistId;
	private String title;
	private String artist;
	
	public Track(String source) {
		super(source);
		playlistId = getString(KEY_PLAYLIST_ID);
		title = getString(KEY_TITLE);
		artist = getString(KEY_ROOT_ARTIST, KEY_ARTIST);
	}
	
	public String getPlaylistId() {
		return playlistId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}

}
