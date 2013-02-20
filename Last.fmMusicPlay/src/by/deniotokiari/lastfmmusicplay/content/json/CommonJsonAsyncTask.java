package by.deniotokiari.lastfmmusicplay.content.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.CommonAsyncTask;
import by.deniotokiari.lastfmmusicplay.http.HttpManager.TYPE;

public class CommonJsonAsyncTask extends CommonAsyncTask<List<String>> {

	private String[] mKeys;
	private static final String EXTRA_ATTRIBUTES = "@attr";
	private static final String ROOT_VK = "response";

	public CommonJsonAsyncTask(Callback<List<String>> callback, String[] keys,
			String... params) {
		super(callback, TYPE.GET, params);
		mKeys = keys;
	}

	private List<String> vk(List<String> list, JSONObject jsonObject) {
		JSONArray array = jsonObject.optJSONArray(mKeys[0]);
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.optJSONObject(i);
				if (obj != null) {
					if (mKeys.length == 1) {
						list.add(obj.toString());
					} else {
						JSONArray jsonArray = obj.optJSONArray(mKeys[1]);
						if (jsonArray != null) {
							for (int j = 0; j < jsonArray.length(); j++) {
								JSONObject object = jsonArray.optJSONObject(j);
								if (object.optString(mKeys[2]).equals(mKeys[3])) {
									list.add(object.toString());
								}
							}
						}
					}
				}
			}
			return list;
		}
		return null;
	}

	private List<String> playlistTrack(List<String> list, JSONObject jsonObject)
			throws JSONException {
		JSONArray array = jsonObject.optJSONArray(mKeys[0]);
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.optJSONObject(i);
				obj.put(mKeys[1], mKeys[2]);
				list.add(obj.toString());
			}
			return list;
		}
		return null;
	}

	private List<String> albumTrack(List<String> list, JSONArray array,
			JSONObject jsonObject) throws JSONException {
		if (jsonObject != null) {
			jsonObject = jsonObject.optJSONObject(mKeys[1]);
			if (jsonObject != null) {
				array = jsonObject.optJSONArray(mKeys[2]);
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					obj.put(mKeys[3], mKeys[4]);
					list.add(obj.toString());
				}
				return list;
			}
		}
		return null;
	}

	private List<String> anotherSource(List<String> list, JSONArray array,
			JSONObject jsonObject) throws JSONException {
		array = jsonObject.optJSONArray(mKeys[1]);
		if (array != null) {
			if (array.length() == 0) {
				return null;
			}
			JSONObject attr = jsonObject.optJSONObject(EXTRA_ATTRIBUTES);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.optJSONObject(i);
				if (attr != null) {
					obj.put("attr", attr);
				}
				list.add(obj.toString());
			}
			return list;
		} else {
			JSONObject object = jsonObject.optJSONObject(mKeys[1]);
			if (object != null) {
				list.add(object.toString());
				return list;
			}
		}
		return null;
	}

	@Override
	protected List<String> process(String source) throws JSONException {
		if (source == null || source.length() == 0) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		JSONObject jsonObject = new JSONObject(source);
		// for vk
		if (mKeys[0].equals(ROOT_VK)) {
			return vk(list, jsonObject);
		}
		// for playlist track
		if (mKeys.length == 3) {
			return playlistTrack(list, jsonObject);
		}
		jsonObject = jsonObject.optJSONObject(mKeys[0]);
		JSONArray array = null;
		// for album tracks
		if (mKeys.length > 2) {
			return albumTrack(list, array, jsonObject);
			// for other
		} else {
			return anotherSource(list, array, jsonObject);
		}
	}
}