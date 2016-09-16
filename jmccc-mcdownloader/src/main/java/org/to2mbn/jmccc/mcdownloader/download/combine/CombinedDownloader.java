package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import org.to2mbn.jmccc.mcdownloader.download.Downloader;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedCallback;

public interface CombinedDownloader extends Downloader {

	/**
	 * Submits a combined download task asynchronously.
	 * 
	 * @param task download task
	 * @param callback download callback
	 * @param <T> the type of the CombinedDownloadTask
	 * @return future representing pending completion of the download
	 * @throws NullPointerException if <code>task==null</code>
	 * @throws RejectedExecutionException if the downloader has been shutdown
	 */
	<T> Future<T> download(CombinedTask<T> task, CombinedCallback<T> callback);

	/**
	 * Submits a combined download task asynchronously.
	 * 
	 * @param task download task
	 * @param callback download callback
	 * @param tries the max number of tries for each sub download task
	 * @param <T> the type of the CombinedDownloadTask
	 * @return future representing pending completion of the download
	 * @throws NullPointerException if <code>task==null</code>
	 * @throws IllegalArgumentException if <code>tries &lt; 1</code>
	 * @throws RejectedExecutionException if the downloader has been shutdown
	 */
	<T> Future<T> download(CombinedTask<T> task, CombinedCallback<T> callback, int tries);

}
