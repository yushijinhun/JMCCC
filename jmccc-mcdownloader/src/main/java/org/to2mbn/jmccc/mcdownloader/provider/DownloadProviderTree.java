package org.to2mbn.jmccc.mcdownloader.provider;

import java.util.Set;
import org.to2mbn.jmccc.mcdownloader.RemoteVersionList;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Asset;
import org.to2mbn.jmccc.version.Library;
import org.to2mbn.jmccc.version.Version;

class DownloadProviderTree implements MinecraftDownloadProvider {

	private MinecraftDownloadProvider left;
	private MinecraftDownloadProvider right;

	public DownloadProviderTree(MinecraftDownloadProvider left, MinecraftDownloadProvider right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public CombinedTask<RemoteVersionList> versionList() {
		CombinedTask<RemoteVersionList> result = left.versionList();
		if (result == null && right != null) {
			result = right.versionList();
		}
		checkFinalResult(result);
		return result;
	}

	@Override
	public CombinedTask<Set<Asset>> assetsIndex(MinecraftDirectory mcdir, Version version) {
		CombinedTask<Set<Asset>> result = left.assetsIndex(mcdir, version);
		if (result == null && right != null) {
			result = right.assetsIndex(mcdir, version);
		}
		checkFinalResult(result);
		return result;
	}

	@Override
	public CombinedTask<Void> gameJar(MinecraftDirectory mcdir, Version version) {
		CombinedTask<Void> result = left.gameJar(mcdir, version);
		if (result == null && right != null) {
			result = right.gameJar(mcdir, version);
		}
		checkFinalResult(result);
		return result;
	}

	@Override
	public CombinedTask<String> gameVersionJson(MinecraftDirectory mcdir, String version) {
		CombinedTask<String> result = left.gameVersionJson(mcdir, version);
		if (result == null && right != null) {
			result = right.gameVersionJson(mcdir, version);
		}
		checkFinalResult(result);
		return result;
	}

	@Override
	public CombinedTask<Void> library(MinecraftDirectory mcdir, Library library) {
		CombinedTask<Void> result = left.library(mcdir, library);
		if (result == null && right != null) {
			result = right.library(mcdir, library);
		}
		checkFinalResult(result);
		return result;
	}

	@Override
	public CombinedTask<Void> asset(MinecraftDirectory mcdir, Asset asset) {
		CombinedTask<Void> result = left.asset(mcdir, asset);
		if (result == null && right != null) {
			result = right.asset(mcdir, asset);
		}
		checkFinalResult(result);
		return result;
	}

	private void checkFinalResult(Object result) {
		if (result == null) {
			throw new IllegalArgumentException("No provider is available for this operation");
		}
	}

}
