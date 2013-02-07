package by.deniotokiari.lastfmmusicplay.api;

public class VkAPI {

	private static final String URL_API = "https://api.vk.com/method/";
	private static final String RESULT_FORMAT = ""; // Or .xml for xml data

	private static String template(String method) {
		return String.format("%s%s%s?", URL_API, method, RESULT_FORMAT);
	}

	private static String returnUrl(String request) {
		return String.format("%s&access_token=%s", request,
				VkAuthHelper.getSession());
	}

	public static String audioSearch(String request, int limit) {
		String result = String.format("%sq=%s&count=%s",
				template("audio.search"), request.replaceAll(" ", "%20"),
				String.valueOf(limit));
		return returnUrl(result);
	}

	public static String audioGetLyrics(String id) {
		String result = String.format("%slyrics_id=%s",
				template("audio.getLyrics"), id);
		return returnUrl(result);
	}

	public static String audioGet(int count, int offset) {
		String result = String.format("%scount=%s&offset=%s",
				template("audio.get"), String.valueOf(count),
				String.valueOf(offset));
		return returnUrl(result);
	}

	public static String wallGet(int count, int offset) {
		String result = String.format("%scount=%s&offset=%s",
				template("wall.get"), String.valueOf(count),
				String.valueOf(offset));
		return returnUrl(result);
	}

	public static String newsfeedGet(int count, int offset) {
		String result = String.format("%scount=%s&offset=%s&filters=post",
				template("newsfeed.get"), String.valueOf(count),
				String.valueOf(offset));
		return returnUrl(result);
	}

}
