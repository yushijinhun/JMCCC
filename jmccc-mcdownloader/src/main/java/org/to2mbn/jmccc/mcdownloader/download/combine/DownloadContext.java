package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.Callback;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedCallback;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;

public interface DownloadContext<T> extends Callback<T> {

	<R> Future<R> submit(Callable<R> task, Callback<R> callback, boolean fatal) throws InterruptedException;

	<R> Future<R> submit(DownloadTask<R> task, DownloadCallback<R> callback, boolean fatal) throws InterruptedException;

	<R> Future<R> submit(CombinedTask<R> task, CombinedCallback<R> callback, boolean fatal) throws InterruptedException;

	void awaitAllTasks(Callable<Void> callback) throws InterruptedException;

}
