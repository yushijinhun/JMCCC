package org.to2mbn.jmccc.mcdownloader.download.concurrent;

import java.util.ArrayList;
import java.util.List;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;

class CombinedCallbackGroup<T> extends CallbackGroup<T> implements CombinedCallback<T> {

	private CombinedCallback<T>[] callbacks;

	public CombinedCallbackGroup(CombinedCallback<T>[] callbacks) {
		super(callbacks);
		this.callbacks = callbacks;
	}

	@Override
	public <R> DownloadCallback<R> downloadStart(DownloadTask<R> task) {
		List<DownloadCallback<R>> listeners = new ArrayList<>();
		EventDispatchException ex = null;
		for (CombinedCallback<T> callback : callbacks) {
			DownloadCallback<R> listener = null;
			try {
				listener = callback.downloadStart(task);
			} catch (Throwable e) {
				if (ex == null) {
					ex = new EventDispatchException();
				} else {
					ex.addSuppressed(e);
				}
			}
			if (listener != null) {
				listeners.add(listener);
			}
		}
		if (ex != null) {
			throw ex;
		}
		@SuppressWarnings("unchecked")
		DownloadCallback<R>[] callbacksArray = listeners.toArray(new DownloadCallback[listeners.size()]);
		return listeners.isEmpty() ? null : DownloadCallbacks.group(callbacksArray);
	}

}
