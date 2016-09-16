package org.to2mbn.jmccc.mcdownloader.download.concurrent;

import java.util.Collection;
import java.util.Objects;

public final class CombinedCallbacks {

	@SafeVarargs
	public static <T> CombinedCallback<T> group(CombinedCallback<T>... callbacks) {
		Objects.requireNonNull(callbacks);
		return new CombinedCallbackGroup<>(callbacks);
	}

	public static <T> CombinedCallback<T> group(Collection<CombinedCallback<T>> callbacks) {
		Objects.requireNonNull(callbacks);
		@SuppressWarnings("unchecked")
		CombinedCallback<T>[] result = callbacks.toArray(new CombinedCallback[callbacks.size()]);
		return new CombinedCallbackGroup<>(result);
	}

	public static <T> CombinedCallback<T> fromCallback(Callback<T> callback) {
		return new AdaptedCallback<>(callback);
	}

	public static <T> CombinedCallback<T> whatever(Runnable callback) {
		Callback<T> c = Callbacks.whatever(callback);
		return fromCallback(c);
	}

	public static <T> CombinedCallback<T> empty() {
		return new EmptyCallback<>();
	}

	private CombinedCallbacks() {
	}

}
