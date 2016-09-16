package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.Objects;

public class CombinedTaskDecorator<T> extends CombinedTask<T> {

	protected final CombinedTask<T> delegated;

	public CombinedTaskDecorator(CombinedTask<T> delegated) {
		this.delegated = Objects.requireNonNull(delegated);
	}

	@Override
	public void execute(DownloadContext<T> context) throws Exception {
		delegated.execute(context);
	}

	@Override
	public CacheStrategy getCacheStrategy() {
		return delegated.getCacheStrategy();
	}

	@Override
	public String getCachePool() {
		return delegated.getCachePool();
	}
}
