package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.concurrent.Future;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedCallback;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;

class DownloadContextCachePoolDecorator<T> extends DownloadContextDecorator<T> {

	private String cachePool;

	public DownloadContextCachePoolDecorator(DownloadContext<T> delegated, String cachePool) {
		super(delegated);
		this.cachePool = cachePool;
	}

	@Override
	public <R> Future<R> submit(DownloadTask<R> task, DownloadCallback<R> callback, boolean fatal) throws InterruptedException {
		if (task.getCachePool() == null) {
			task = task.cachePool(cachePool);
		}
		return delegated.submit(task, callback, fatal);
	}

	@Override
	public <R> Future<R> submit(CombinedTask<R> task, CombinedCallback<R> callback, boolean fatal) throws InterruptedException {
		if (task.getCachePool() == null) {
			task = task.cachePool(cachePool);
		}
		return delegated.submit(task, callback, fatal);
	}
}
