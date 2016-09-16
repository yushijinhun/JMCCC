package org.to2mbn.jmccc.mcdownloader.provider.extension.forge;

import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedDownloadTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersionList;

public interface ForgeVersionListProvider extends ExtensionProvider {

	CombinedDownloadTask<ForgeVersionList> fetchVersionList();

}
