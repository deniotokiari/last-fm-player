package by.deniotokiari.lastfmmusicplay.content.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.CommonAsyncTask;

public class LastfmListJsonAsyncTask extends CommonAsyncTask<List<String>> {

	private String[] mKeys;
	private static final String EXTRA_ATTRIBUTES = "@attr";

	public LastfmListJsonAsyncTask(Callback<List<String>> callback,
			String[] keys, String... params) {
		super(callback, params);
		mKeys = keys;
	}

	// TODO need refactor
	@Override
	protected List<String> process(String source) throws JSONException {
		if (source == null || source.length() == 0) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		JSONObject jsonObject = new JSONObject(source);
		// for playlist track
		if (mKeys.length == 3) {
			JSONArray array = jsonObject.optJSONArray(mKeys[0]);
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					obj.put(mKeys[1], mKeys[2]);
					list.add(obj.toString());
				}
				return list;
			}
		}
		jsonObject = jsonObject.optJSONObject(mKeys[0]);
		JSONArray array;
		// for album tracks
		if (mKeys.length > 2) {
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
			// for other
		} else {
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
		}
		return null;
	}
}