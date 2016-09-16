package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import java.util.concurrent.Future;
import org.to2mbn.jmccc.mcdownloader.CacheOption;
import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedDownloadCallback;
import org.to2mbn.jmccc.mcdownloader.provider.extension.Extension;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ProviderMethod;

public interface VersionListExtension extends Extension<VersionListProvider> {

	@ProviderMethod
	Future<RemoteVersionList> fetchVersionList(CombinedDownloadCallback<RemoteVersionList> callback, CacheOption... options);

}
