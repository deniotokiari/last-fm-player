package by.deniotokiari.lastfmmusicplay.content.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommonJson {

	private JSONObject mJsonObject;

	public CommonJson(String jsonObject) {
		try {
			mJsonObject = new JSONObject(jsonObject);
		} catch (JSONException e) {
			mJsonObject = null;
		}
	}

	public CommonJson(JSONObject jsonObject) {
		mJsonObject = jsonObject;
	}

	public CommonJson(JSONObject jsonObject, String root) {
		mJsonObject = jsonObject.optJSONObject(root);
	}

	public CommonJson(String jsonObject, String root) {
		try {
			mJsonObject = new JSONObject(jsonObject);
		} catch (JSONException e) {
			mJsonObject = null;
		}
		mJsonObject = mJsonObject.optJSONObject(root);

	}

	public String getString(String key) {
		if (mJsonObject != null) {
			return mJsonObject.optString(key);
		}
		return "";
	}

	protected String getString(String root, String key) {
		if (mJsonObject != null) {
			JSONObject object = mJsonObject.optJSONObject(root);
			if (object != null) {
				return object.optString(key);
			}
		}
		return "";
	}

	public String getArrayItem(String root, String key, int index) {
		if (mJsonObject != null) {
			JSONArray array = mJsonObject.optJSONArray(root);
			if (array != null) {
				JSONObject image = array.optJSONObject(index);
				if (image != null) {
					return image.optString(key);
				}
			}
		}
		return "";
	}
	
	/** "item,item" **/
	protected String getArrayItemsAsString(String root, String key) {
		if (mJsonObject != null) {
			JSONObject jsonObject = mJsonObject.optJSONObject(root);
			if (jsonObject != null) {
				JSONArray array = jsonObject.optJSONArray(key);
				if (array != null) {
					return array.toString();
				}
			}
		}
		return "";
	}

	@Override
	public String toString() {
		return mJsonObject.toString();
	}

	protected JSONObject getObj() {
		return mJsonObject;
	}

}
