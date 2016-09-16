package org.to2mbn.jmccc.mcdownloader.provider.extension;

public interface InjectableExtensionProvider extends ExtensionProvider {

	void setHeadProvider(DownloadProvider head);

	void setTailProvider(DownloadProvider tail);

}
