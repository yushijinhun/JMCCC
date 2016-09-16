package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import org.to2mbn.jmccc.mcdownloader.DownloadOption;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedCallback;
import org.to2mbn.jmccc.mcdownloader.provider.extension.Extension;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ProviderMethod;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

public interface MinecraftInstallExtension extends Extension<MinecraftInstallProvider> {

	@ProviderMethod
	CombinedTask<Version> installMinecraft(MinecraftDirectory dir, String version, CombinedCallback<Version> callback, DownloadOption... options);

}
