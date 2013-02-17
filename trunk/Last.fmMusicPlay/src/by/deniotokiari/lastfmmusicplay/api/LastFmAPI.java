package by.deniotokiari.lastfmmusicplay.api;

import java.util.Date;

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

	// Track
	public static String trackScrobble(String artist, String track, Date date) {
		String result = template("track.scrobble") + "&sk="
				+ LastfmAuthHelper.getSession();
		result += "&artist=" + Uri.encode(artist);
		result += "&track=" + Uri.encode(track);
		result += "&timestamp=" + String.valueOf(date.getTime() / 1000);
		String sign = "api_key" + API_KEY;
		sign += "artist" + artist;
		sign += "methodtrack.scrobblesk" + LastfmAuthHelper.getSession();
		sign += "timestamp" + String.valueOf(date.getTime() / 1000);
		sign += "track" + track;
		sign += SECRET_KEY;
		result += "&api_sig=" + Md5.md5(sign);
		return result;
	}

	public static String trackGetInfo(String track, String artist, String user) {
		return String.format("%s&track=%s&artist=%s&username=%s&autocorrect1",
				template("track.getInfo"), Uri.encode(track),
				Uri.encode(artist), user);
	}

	public static String trackLove(String track, String artist) {
		String result = template("track.love") + "&sk="
				+ LastfmAuthHelper.getSession();
		result += "&artist=" + Uri.encode(artist);
		result += "&track=" + Uri.encode(track);
		String sign = "api_key" + API_KEY;
		sign += "artist" + artist;
		sign += "methodtrack.lovesk" + LastfmAuthHelper.getSession();
		sign += "track" + track;
		sign += SECRET_KEY;
		result += "&api_sig=" + Md5.md5(sign);
		return result;
	}
	
	public static String trackUnlove(String track, String artist) {
		String result = template("track.unlove") + "&sk="
				+ LastfmAuthHelper.getSession();
		result += "&artist=" + Uri.encode(artist);
		result += "&track=" + Uri.encode(track);
		String sign = "api_key" + API_KEY;
		sign += "artist" + artist;
		sign += "methodtrack.unlovesk" + LastfmAuthHelper.getSession();
		sign += "track" + track;
		sign += SECRET_KEY;
		result += "&api_sig=" + Md5.md5(sign);
		return result;
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

	public static String userGetPlaylists(String user) {
		return String.format("%s&user=%s", template("user.getPlaylists"),
				Uri.encode(user));
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

	public static String tagGetTopArtists(String tag, int limit, int page) {
		return String.format("%s&tag=%s&limit=%s&page=%s",
				template("tag.getTopArtists"), Uri.encode(tag),
				String.valueOf(limit), String.valueOf(page));
	}

	public static String tagGetTopAlbums(String tag, int limit, int page) {
		return String.format("%s&tag=%s&limit=%s&page=%s",
				template("tag.getTopAlbums"), Uri.encode(tag),
				String.valueOf(limit), String.valueOf(page));
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

	public static String artistGetInfo(String artist) {
		return String.format("%s&artist=%s&autocorrect1",
				template("artist.getInfo"), Uri.encode(artist));
	}

	// Album
	public static String albumGetInfo(String artist, String album) {
		return String.format("%s&artist=%s&album=%s&autocorrect1",
				template("album.getInfo"), Uri.encode(artist),
				Uri.encode(album));
	}

	// Other
	public static String playlistFetch(String id) {
		return String
				.format("http://lastfm-api-ext.appspot.com/2.0/?method=playlist.fetch&playlistURL=lastfm://playlist/%s&api_key=%s&outtype=json",
						id, API_KEY);
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
