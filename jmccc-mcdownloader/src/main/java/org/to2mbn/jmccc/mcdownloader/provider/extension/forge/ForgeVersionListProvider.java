package org.to2mbn.jmccc.mcdownloader.provider.extension.forge;

import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersionList;

public interface ForgeVersionListProvider extends ExtensionProvider {

	CombinedTask<ForgeVersionList> fetchVersionList();

}
