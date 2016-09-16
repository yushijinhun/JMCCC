package org.to2mbn.jmccc.mcdownloader.provider.extension.offical;

import java.io.File;
import java.net.URI;
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.provider.extension.ExtensionProvider;
import org.to2mbn.jmccc.version.Library;

public interface LibraryProcessProvider extends ExtensionProvider {

	DownloadTask<Void> createDownloadTask(File target, Library library, URI libraryUri);

}
