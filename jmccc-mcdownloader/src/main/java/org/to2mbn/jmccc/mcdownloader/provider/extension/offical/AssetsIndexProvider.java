package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

public interface AssetsIndexProvider extends ExtensionProvider {

	CombinedTask<Void> assetsIndex(MinecraftDirectory mcdir, Version version);

}
