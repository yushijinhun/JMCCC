package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.Callback;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedCallback;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;

public class DownloadContextDecorator<T> implements DownloadContext<T> {

	protected final DownloadContext<T> delegated;

	public DownloadContextDecorator(DownloadContext<T> delegated) {
		this.delegated = Objects.requireNonNull(delegated);
	}

	@Override
	public void done(T result) {
		delegated.done(result);
	}

	@Override
	public void failed(Throwable e) {
		delegated.failed(e);
	}

	@Override
	public void cancelled() {
		delegated.cancelled();
	}

	@Override
	public <R> Future<R> submit(Callable<R> task, Callback<R> callback, boolean fatal) throws InterruptedException {
		return delegated.submit(task, callback, fatal);
	}

	@Override
	public <R> Future<R> submit(DownloadTask<R> task, DownloadCallback<R> callback, boolean fatal) throws InterruptedException {
		return delegated.submit(task, callback, fatal);
	}

	@Override
	public <R> Future<R> submit(CombinedTask<R> task, CombinedCallback<R> callback, boolean fatal) throws InterruptedException {
		return delegated.submit(task, callback, fatal);
	}

	@Override
	public void awaitAllTasks(Callable<Void> callback) throws InterruptedException {
		delegated.awaitAllTasks(callback);
	}

}
