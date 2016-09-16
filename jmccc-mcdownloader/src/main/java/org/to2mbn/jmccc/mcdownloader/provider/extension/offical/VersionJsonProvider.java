package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedDownloadTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;
import org.to2mbn.jmccc.option.MinecraftDirectory;

public interface VersionJsonProvider extends ExtensionProvider {

	CombinedDownloadTask<Void> versionJson(MinecraftDirectory mcdir, String version);

}
