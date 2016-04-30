package org.to2mbn.jmccc.launch.classloading;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

class MinecraftClassLoader extends URLClassLoader {

	private File nativesDir;

	public MinecraftClassLoader(URL[] urls, File nativesDir) {
		super(urls);
		this.nativesDir = nativesDir;
	}

	@Override
	protected String findLibrary(String libname) {
		String filename = System.mapLibraryName(libname);
		if (filename != null) {
			File file = new File(nativesDir, filename);
			if (file.isFile()) {
				return file.getAbsolutePath();
			}
		}
		return super.findLibrary(libname);
	}

}
