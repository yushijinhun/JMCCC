package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Asset;

public interface AssetProvider extends ExtensionProvider {

	CombinedTask<Void> asset(MinecraftDirectory mcdir, Asset asset);

}
