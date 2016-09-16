package org.to2mbn.jmccc.mcdownloader.provider;

import java.net.URI;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.mcdownloader.download.tasks.ResultProcessor;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Asset;
import org.to2mbn.jmccc.version.Library;
import org.to2mbn.jmccc.version.Version;

abstract public class DefaultLayoutProvider extends URIDownloadProvider {

	@Override
	public CombinedTask<Void> library(final MinecraftDirectory mcdir, final Library library) {
		if (library.isSnapshotArtifact()) {
			final String repo = getLibraryRepo(library);
			return MavenRepositories.snapshotPostfix(library.getGroupId(), library.getArtifactId(), library.getVersion(), repo)
					.andThenDownload(new ResultProcessor<String, CombinedTask<Void>>() {

						@Override
						public CombinedTask<Void> process(String postfix) throws Exception {
							String url = repo + library.getPath(postfix);
							if (library.getChecksums() != null) {
								url += ".pack.xz";
							}
							return DefaultLayoutProvider.this.library(mcdir, library, URI.create(url));
						}
					});
		}
		return super.library(mcdir, library);
	}

	@Override
	public URI getLibrary(Library library) {
		String url = getLibraryRepo(library) + library.getPath();
		if (library.getChecksums() != null) {
			url += ".pack.xz";
		}
		return URI.create(url);
	}

	private String getLibraryRepo(Library library) {
		String customizedUrl = library.getCustomizedUrl();
		return customizedUrl == null ? getLibraryBaseURL() : customizedUrl;
	}

	@Override
	public URI getGameJar(Version version) {
		return URI.create(getVersionBaseURL() + version.getRoot() + "/" + version.getRoot() + ".jar");
	}

	@Override
	public URI getGameVersionJson(String version) {
		return URI.create(getVersionBaseURL() + version + "/" + version + ".json");
	}

	@Override
	public URI getAssetIndex(Version version) {
		return URI.create(getAssetIndexBaseURL() + version.getAssets() + ".json");
	}

	@Override
	public URI getVersionList() {
		return URI.create(getVersionListURL());
	}

	@Override
	public URI getAsset(Asset asset) {
		return URI.create(getAssetBaseURL() + asset.getPath());
	}

	abstract protected String getLibraryBaseURL();
	abstract protected String getVersionBaseURL();
	abstract protected String getAssetIndexBaseURL();
	abstract protected String getVersionListURL();
	abstract protected String getAssetBaseURL();

}
