package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.concurrent.Future;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask.CacheStrategy;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedCallback;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;

class DownloadContextCacheStrategyDecorator<T> extends DownloadContextDecorator<T> {

	private CacheStrategy strategy;

	public DownloadContextCacheStrategyDecorator(DownloadContext<T> delegated, CacheStrategy strategy) {
		super(delegated);
		this.strategy = strategy;
	}

	@Override
	public <R> Future<R> submit(DownloadTask<R> task, DownloadCallback<R> callback, boolean fatal) throws InterruptedException {
		DownloadTask<R> processed;
		switch (strategy) {
			case CACHEABLE:
			case FORCIBLY_CACHE:
				processed = task.cacheable(true);
				break;

			case NON_CACHEABLE:
				processed = task.cacheable(false);
				break;

			default:
				processed = task;
				break;
		}
		return delegated.submit(processed, callback, fatal);
	}

	@Override
	public <R> Future<R> submit(CombinedTask<R> task, CombinedCallback<R> callback, boolean fatal) throws InterruptedException {
		CombinedTask<R> processed;
		switch (strategy) {
			case CACHEABLE:
				if (task.getCacheStrategy() == CacheStrategy.DEFAULT) {
					processed = task.cacheable(CacheStrategy.CACHEABLE);
				} else {
					processed = task;
				}
				break;

			case FORCIBLY_CACHE:
			case NON_CACHEABLE:
				processed = task.cacheable(strategy);
				break;

			default:
				processed = task;
				break;
		}
		return delegated.submit(processed, callback, fatal);
	}
}
