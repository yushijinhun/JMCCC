package org.to2mbn.jmccc.mcdownloader.download.combine;

class CombinedTaskCachePoolDecorator<T> extends CombinedTaskDecorator<T> {

	private String cachePool;

	public CombinedTaskCachePoolDecorator(CombinedTask<T> delegated, String cachePool) {
		super(delegated);
		this.cachePool = cachePool;
	}

	@Override
	public void execute(DownloadContext<T> context) throws Exception {
		delegated.execute(new DownloadContextCachePoolDecorator<>(context, cachePool));
	}

	@Override
	public String getCachePool() {
		return cachePool;
	}

}
