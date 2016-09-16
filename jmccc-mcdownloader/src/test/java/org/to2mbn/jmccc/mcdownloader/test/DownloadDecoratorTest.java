package org.to2mbn.jmccc.mcdownloader.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask.CacheStrategy;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.tasks.MemoryDownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.tasks.ResultProcessor;

public class DownloadDecoratorTest {

	@Test
	public void testDownloadTask() {
		DownloadTask<byte[]> task = new MemoryDownloadTask("http://test/uri");

		DownloadTask<byte[]> proxied1 = task.cacheable();
		assertEquals(true, proxied1.isCacheable());
		assertEquals(task.getCachePool(), proxied1.getCachePool());
		assertEquals(task.getURI(), proxied1.getURI());

		DownloadTask<byte[]> proxied2 = proxied1.cachePool("cache.pool.test");
		assertEquals("cache.pool.test", proxied2.getCachePool());
		assertEquals(proxied1.isCacheable(), proxied2.isCacheable());
		assertEquals(proxied1.getURI(), proxied2.getURI());

		DownloadTask<Object> proxied3 = proxied2.andThen(new ResultProcessor<byte[], Object>() {

			@Override
			public Object process(byte[] arg) throws Exception {
				return null;
			}
		});
		assertEquals(proxied2.isCacheable(), proxied3.isCacheable());
		assertEquals(proxied2.getCachePool(), proxied3.getCachePool());
		assertEquals(proxied2.getURI(), proxied3.getURI());
	}

	@Test
	public void testCombinedTask() {
		CombinedTask<?> task = CombinedTask.single(new MemoryDownloadTask("http://test/uri"));

		CombinedTask<?> proxied1 = task.cacheable(CacheStrategy.FORCIBLY_CACHE);
		assertEquals(CacheStrategy.FORCIBLY_CACHE, proxied1.getCacheStrategy());
		assertEquals(task.getCachePool(), proxied1.getCachePool());

		CombinedTask<?> proxied2 = proxied1.cachePool("cache.pool.test");
		assertEquals("cache.pool.test", proxied2.getCachePool());
		assertEquals(proxied1.getCacheStrategy(), proxied2.getCacheStrategy());
	}

}
