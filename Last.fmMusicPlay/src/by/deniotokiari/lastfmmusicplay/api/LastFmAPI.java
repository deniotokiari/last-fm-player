package by.deniotokiari.lastfmmusicplay.api;

import android.net.Uri;
import by.deniotokiari.lastfmmusicplay.utilities.Md5;

public class LastFmAPI {

	private static final String API_KEY = "62c4546a7a6c00ff385eab426fc06805";
	private static final String SECRET_KEY = "ed5e08b16d2c9d39bff2d5edd0b0d502";
	private static final String URL_API = "http://ws.audioscrobbler.com/2.0/?method=";
	private static final String RESULT_FORMAT = "&format=json";

	private static String template(String method) {
		return String.format("%s%s&api_key=%s%s", URL_API, method, API_KEY,
				RESULT_FORMAT);
	}

	// User
	public static String userGetTopTags(String user, int limit, int page) {
		return String.format("%s&user=%s&limit=%s&page=%s",
				template("user.gettoptags"), Uri.encode(user),
				String.valueOf(limit), String.valueOf(page));
	}

	public static String userGetTopTracks(String user, int limit, int page) {
		return String.format("%s&user=%s&limit=%s&page=%s",
				template("user.gettoptracks"), Uri.encode(user),
				String.valueOf(limit), String.valueOf(page));
	}

	public static String userGetRecentTracks(String user, int limit, int page) {
		return String.format("%s&user=%s&limit=%s&page=%s",
				template("user.getrecenttracks"), Uri.encode(user),
				String.valueOf(limit), String.valueOf(page));
	}

	public static String userLovedTracks(String user, int limit, int page) {
		return String.format("%s&user=%s&limit=%s&page=%s",
				template("user.getlovedtracks"), Uri.encode(user),
				String.valueOf(limit), String.valueOf(page));
	}

	// Tag
	public static String tagGetTopTracks(String tag, int limit, int page) {
		return String.format("%s&tag=%s&limit=%s&page=%s",
				template("tag.gettoptracks"), Uri.encode(tag),
				String.valueOf(limit), String.valueOf(page));
	}
	
	public static String tagGetTopTags() {
		return String.format("%s", template("tag.getTopTags"));
	}

	// Library
	public static String libraryGetArtists(String user, int limit, int page) {
		return String.format("%s&user=%s&limit=%s&page=%s&autocorrect1",
				template("library.getartists"), Uri.encode(user),
				String.valueOf(limit), String.valueOf(page));

	}

	public static String libraryGetTracks(String user, int limit, int page) {
		return String.format("%s&user=%s&limit=%s&page=%s",
				template("library.gettracks"), Uri.encode(user),
				String.valueOf(limit), String.valueOf(page));
	}

	public static String libraryGetAlbums(String user, int limit, int page) {
		return String.format("%s&user=%s&limit=%s&page=%s",
				template("library.getAlbums"), Uri.encode(user),
				String.valueOf(limit), String.valueOf(page));
	}

	// Artist
	public static String artistGetTopTracks(String artist, int limit, int page) {
		return String.format("%s&artist=%s&limit=%s&page=%s",
				template("artist.gettoptracks"), Uri.encode(artist),
				String.valueOf(limit), String.valueOf(page));
	}

	public static String artistGetTopAlbums(String artist, int limit, int page) {
		return String.format("%s&artist=%s&limit=%s&page=%s",
				template("artist.gettopalbums"), Uri.encode(artist),
				String.valueOf(limit), String.valueOf(page));
	}

	// Album
	public static String albumGetInfo(String artist, String album) {
		return String.format("%s&artist=%s&album=%s&autocorrect1",
				template("album.getInfo"), Uri.encode(artist),
				Uri.encode(album));
	}

	// Auth
	public static String authGetToken() {
		return template("auth.gettoken");
	}

	public static String grantAccessUrl(String token) {
		return String.format(
				"http://www.last.fm/api/auth/?api_key=%s&token=%s", API_KEY,
				token);
	}

	public static String authGetSession(String token) {
		String sig = "api_key" + API_KEY + "methodauth.getsessiontoken" + token
				+ SECRET_KEY;
		sig = Md5.md5(sig);
		return String.format("%s&token=%s&api_sig=%s",
				template("auth.getsession"), token, sig);
	}

}
