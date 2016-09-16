package org.to2mbn.jmccc.mcdownloader.provider.extension.liteloader;

import java.util.concurrent.Future;
import org.to2mbn.jmccc.mcdownloader.CacheOption;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedCallback;
import org.to2mbn.jmccc.mcdownloader.provider.extension.Extension;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ProviderMethod;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderVersionList;

public interface LiteloaderVersionListExtension extends Extension<LiteloaderVersionListProvider> {

	@ProviderMethod
	Future<LiteloaderVersionList> fetchForgeVersionList(CombinedCallback<LiteloaderVersionList> callback, CacheOption... options);

}
