package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import org.to2mbn.jmccc.mcdownloader.MinecraftDownloadOption;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedDownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CombinedDownloadCallback;
import org.to2mbn.jmccc.mcdownloader.provider.extension.Extension;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ProviderMethod;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

public interface MinecraftInstallExtension extends Extension<MinecraftInstallProvider> {

	@ProviderMethod
	CombinedDownloadTask<Version> installMinecraft(MinecraftDirectory dir, String version, CombinedDownloadCallback<Version> callback, MinecraftDownloadOption... options);

}
