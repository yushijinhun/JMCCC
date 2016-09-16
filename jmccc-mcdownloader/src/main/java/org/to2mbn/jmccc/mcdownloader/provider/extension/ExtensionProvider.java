package org.to2mbn.jmccc.mcdownloader.provider.extension;

public interface ExtensionProvider {

	/**
	 * Gets the processing priority ranking of the provider.
	 * 
	 * @return the processing priority ranking
	 * @see ExtensionProviderRankings
	 */
	int getRanking();

}
