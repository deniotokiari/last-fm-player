package by.deniotokiari.lastfmmusicplay.content.json.vk;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class WallNewsTrack extends CommonJson {

	public static final String ROOT = "response";
	public static final String ROOT_ITEMS = "attachments";
	public static final String KEY_TYPE = "type";
	public static final String ROOT_ITEM_AUDIO = "audio";
	public static final String KEY_ARTIST = "performer";
	public static final String KEY_TITLE = "title";

	private String artist;
	private String title;

	public WallNewsTrack(String source) {
		super(source);
		artist = getString(ROOT_ITEM_AUDIO, KEY_ARTIST);
		title = getString(ROOT_ITEM_AUDIO, KEY_TITLE);
	}

	public String getArtist() {
		return artist.replaceAll("&amp;", "&");
	}

	public String getTitle() {
		return title.replaceAll("&amp;", "&");
	}

}
