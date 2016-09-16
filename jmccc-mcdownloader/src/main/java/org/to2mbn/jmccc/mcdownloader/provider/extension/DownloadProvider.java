package org.to2mbn.jmccc.mcdownloader.provider.extension;

public interface DownloadProvider {

	<T extends ExtensionProvider> T getExtensionProvider(Class<T> type);

}
