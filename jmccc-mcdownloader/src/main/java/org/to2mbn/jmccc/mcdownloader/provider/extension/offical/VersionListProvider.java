package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedDownloadTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;

public interface VersionListProvider extends ExtensionProvider {

	CombinedDownloadTask<RemoteVersionList> versionList();

}
