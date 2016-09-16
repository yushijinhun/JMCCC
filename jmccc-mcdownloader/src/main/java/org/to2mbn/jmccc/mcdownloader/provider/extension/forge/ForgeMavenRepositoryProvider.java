package org.to2mbn.jmccc.mcdownloader.provider.extension.forge;

import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;
import org.to2mbn.jmccc.version.Artifact;

public interface ForgeMavenRepositoryProvider {

	CombinedTask<byte[]> forgeRepositoryArtifact(Artifact artifact);

}
