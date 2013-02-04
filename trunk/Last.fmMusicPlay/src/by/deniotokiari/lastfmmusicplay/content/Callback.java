package by.deniotokiari.lastfmmusicplay.content;

public interface Callback<T> {

	void onSuccess(T t, Object... objects);

	void onError(Throwable e, Object... objects);

}
