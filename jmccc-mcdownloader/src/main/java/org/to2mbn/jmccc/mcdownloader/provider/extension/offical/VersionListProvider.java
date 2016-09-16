package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;

public interface VersionListProvider extends ExtensionProvider {

	CombinedTask<RemoteVersionList> versionList();

}
