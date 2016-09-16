package org.to2mbn.jmccc.mcdownloader.provider.extension.liteloader;

import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderVersionList;

public interface LiteloaderVersionListProvider extends ExtensionProvider {

	CombinedTask<LiteloaderVersionList> fetchVersionList();

}
