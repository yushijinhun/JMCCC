package org.to2mbn.jmccc.mcdownloader.provider;

import java.util.Set;
import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Asset;
import org.to2mbn.jmccc.version.Library;
import org.to2mbn.jmccc.version.Version;

abstract public class AbstractMinecraftDownloadProvider implements MinecraftDownloadProvider {

	// @formatter:off
	@Override public CombinedTask<RemoteVersionList> versionList() { return null; }
	@Override public CombinedTask<Set<Asset>> assetsIndex(MinecraftDirectory mcdir, Version version) { return null; }
	@Override public CombinedTask<Void> gameJar(MinecraftDirectory mcdir, Version version) { return null; }
	@Override public CombinedTask<String> gameVersionJson(MinecraftDirectory mcdir, String version) { return null; }
	@Override public CombinedTask<Void> library(MinecraftDirectory mcdir, Library library) { return null; }
	@Override public CombinedTask<Void> asset(MinecraftDirectory mcdir, Asset asset) { return null; }
	// @formatter:on

}
