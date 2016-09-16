package org.to2mbn.jmccc.mcdownloader.provider.extension;

public interface Extension<PROVIDER extends ExtensionProvider> {

	PROVIDER getProvider();

}
