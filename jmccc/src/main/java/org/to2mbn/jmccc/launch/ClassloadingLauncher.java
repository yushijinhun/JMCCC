package org.to2mbn.jmccc.launch;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.atomic.AtomicInteger;
import org.to2mbn.jmccc.exec.GameProcessListener;

/**
 * Launches Minecraft by invoking 'main()' in the current jvm.
 * 
 * @author yushijinhun
 */
public class ClassloadingLauncher extends AbstractLauncher {

	private static final AtomicInteger threadGroupCounter = new AtomicInteger();

	private static class NativesClassLoader extends URLClassLoader {

		private File nativesDir;

		public NativesClassLoader(URL[] urls, File nativesDir) {
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

	@Override
	protected LaunchResult doLaunch(LaunchArgument arg, GameProcessListener listener) throws LaunchException {
		try {
			URL[] classpath = new URL[arg.getLibraries().size()];
			int i = 0;
			for (File file : arg.getLibraries()) {
				classpath[i++] = file.toURI().toURL();
			}

			ClassLoader cl = new NativesClassLoader(classpath, arg.getNativesPath());
			Method mainMethod = cl.loadClass(arg.getLaunchOption().getVersion().getMainClass())
					.getMethod("main", String[].class);
			String[] args = arg.getFormattedMinecraftArguments().toArray(new String[0]);

			int idx = threadGroupCounter.getAndIncrement();
			ThreadGroup targetGroup = new ThreadGroup("launch-target-" + idx);
			Thread targetThread = new Thread(targetGroup, () -> {
				boolean exitedNormally = false;
				try {
					invokeMain(cl, mainMethod, args);
					exitedNormally = true;
				} catch (Throwable e) {
					for (String log : generateLaunchErrorLog(cl, arg, e, Thread.currentThread())) {
						if (listener != null) {
							listener.onErrorLog(log);
						}
					}
					if (listener != null) {
						listener.onExit(1);
					}
				}
				if (exitedNormally) {
					if (listener != null) {
						listener.onExit(0);
					}
				}
			}, "launch-target-main-" + idx);
			targetThread.start();
		} catch (Throwable e) {
			throw new LaunchException(e);
		}

		return null;
	}

	private void invokeMain(ClassLoader cl, Method mainMethod, String[] args) throws InvocationTargetException, IllegalAccessException {
		ClassLoader oldCtxCl = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(cl);
		try {
			mainMethod.invoke(null, (Object) args);
		} finally {
			Thread.currentThread().setContextClassLoader(oldCtxCl);
		}
	}

	private String[] generateLaunchErrorLog(ClassLoader cl, LaunchArgument arg, Throwable e, Thread thread) {
		StringBuilder sb = new StringBuilder();
		sb.append("JMCCC Error:\n");
		sb.append("An exception has occurred during invoking 'main' method.\n");
		sb.append("Stacktrace:\n");
		sb.append(getStackTrace(e));
		sb.append('\n');
		sb.append("Thread: ").append(thread).append('\n');
		sb.append("LaunchArgument: ").append(arg).append('\n');
		sb.append("ClassLoader: ").append(cl).append('\n');

		return sb.toString().replace("\r", "").split("\\n");
	}

	private String getStackTrace(Throwable e) {
		CharArrayWriter buf = new CharArrayWriter();
		e.printStackTrace(new PrintWriter(buf));
		return buf.toString();
	}

}
