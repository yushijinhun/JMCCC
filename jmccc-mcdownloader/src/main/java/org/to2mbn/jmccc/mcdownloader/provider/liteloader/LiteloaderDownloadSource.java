package org.to2mbn.jmccc.mcdownloader.provider.liteloader;

import org.to2mbn.jmccc.internal.org.json.JSONObject;
import org.to2mbn.jmccc.mcdownloader.download.combine.CombinedTask;

public interface LiteloaderDownloadSource {

	String getLiteloaderManifestUrl();

	CombinedTask<JSONObject> liteloaderSnapshotVersionJson(LiteloaderVersion liteloader);

}
