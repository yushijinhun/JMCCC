package org.to2mbn.jmccc.launch.classloading;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.to2mbn.jmccc.exec.GameProcessListener;
import org.to2mbn.jmccc.launch.AbstractLauncher;
import org.to2mbn.jmccc.launch.LaunchArgument;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.LaunchResult;

/**
 * Launches Minecraft by invoking 'main()' in the current jvm.
 * 
 * @author yushijinhun
 */
public class ClassloadingLauncher extends AbstractLauncher {

	private static final AtomicInteger threadGroupCounter = new AtomicInteger();

	private boolean stopThreads;
	private boolean useInternalStop;

	public boolean isStopThreads() {
		return stopThreads;
	}

	public void setStopThreads(boolean stopThreads) {
		this.stopThreads = stopThreads;
	}

	public boolean isUseInternalStop() {
		return useInternalStop;
	}

	public void setUseInternalStop(boolean useInternalStop) {
		this.useInternalStop = useInternalStop;
	}

	@Override
	protected LaunchResult doLaunch(LaunchArgument arg, GameProcessListener listener) throws LaunchException {
		try {
			URL[] classpath = new URL[arg.getLibraries().size()];
			int i = 0;
			for (File file : arg.getLibraries()) {
				classpath[i++] = file.toURI().toURL();
			}

			ClassLoader cl = new MinecraftClassLoader(classpath, arg.getNativesPath());
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
					if (e instanceof InvocationTargetException && e.getCause() instanceof ExitInterceptionException) {
						listener.onExit(((ExitInterceptionException) e.getCause()).getExitCode());
					} else {
						for (String log : generateLaunchErrorLog(cl, arg, e, Thread.currentThread())) {
							if (listener != null) {
								listener.onErrorLog(log);
							}
						}
						if (listener != null) {
							listener.onExit(-1);
						}
					}
				}
				if (exitedNormally) {
					if (listener != null) {
						listener.onExit(0);
					}
				}

				cleanupThreads(targetGroup, cl);

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

	@SuppressWarnings("deprecation")
	private void cleanupThreads(ThreadGroup group, ClassLoader ctxCl) {
		Thread cleaner = new Thread(null, () -> {
			Thread.currentThread().setContextClassLoader(null);
			group.setDaemon(true);

			filterThreads(group, ctxCl).forEach(Thread::interrupt);

			Thread.interrupted();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}

			if (stopThreads) {
				if (useInternalStop) {
					// if the threads didn't go dying, let's help them
					try {
						Method stop0 = Thread.class.getDeclaredMethod("stop0", Object.class);
						stop0.setAccessible(true);

						for (Thread t : filterThreads(group, ctxCl)) {
							if (t.isAlive()) {
								t.resume();
								try {
									stop0.invoke(t, new SuperThreadDeath());
								} catch (Throwable e) {
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					for (Thread t : filterThreads(group, ctxCl)) {
						if (t.isAlive()) {
							try {
								t.stop();
							} catch (Throwable e) {
							}
						}
					}
				}
			}

		}, "thread-killer[" + group.getName() + "]");
		cleaner.setPriority(Thread.MAX_PRIORITY);
		cleaner.setDaemon(true);
		cleaner.start();
	}

	private List<Thread> filterThreads(ThreadGroup group, ClassLoader ctxCl) {
		Thread[] allThreads = new Thread[Thread.activeCount()];
		Thread.enumerate(allThreads);
		List<Thread> threads = new ArrayList<>();
		for (Thread t : allThreads) {
			boolean matches = false;
			if (t.getContextClassLoader() == ctxCl) {
				matches = true;
			} else {
				for (ThreadGroup g = t.getThreadGroup(); g != null; g = g.getParent()) {
					if (g == group) {
						matches = true;
						break;
					}
				}
			}
			if (matches) {
				threads.add(t);
			}
		}
		return threads;
	}

}
