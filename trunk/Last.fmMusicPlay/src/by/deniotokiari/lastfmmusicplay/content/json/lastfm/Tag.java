package by.deniotokiari.lastfmmusicplay.content.json.lastfm;

import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;

public class Tag extends CommonJson {

	public static final String ROOT = "toptags";
	public static final String ITEM = "tag";

	public static final String KEY_NAME = "name";
	public static final String KEY_RANK = "count";

	private String name;
	private String rank;

	public Tag(String source) {
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

}
