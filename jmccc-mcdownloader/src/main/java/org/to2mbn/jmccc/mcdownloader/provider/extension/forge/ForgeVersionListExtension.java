package org.to2mbn.jmccc.mcdownloader.provider.extension.forge;

import java.util.concurrent.Future;
import org.to2mbn.jmccc.mcdownloader.CacheOption;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedDownloadCallback;
import org.to2mbn.jmccc.mcdownloader.provider.extension.Extension;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ProviderMethod;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersionList;

public interface ForgeVersionListExtension extends Extension<ForgeVersionListProvider> {

	@ProviderMethod
	Future<ForgeVersionList> fetchForgeVersionList(CombinedDownloadCallback<ForgeVersionList> callback, CacheOption... options);

}
