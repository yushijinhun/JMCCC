package org.to2mbn.jmccc.launch;

import org.to2mbn.jmccc.launch.classloading.ClassloadingLauncher;
import org.to2mbn.jmccc.launch.classloading.ExitInterceptionSecurityManager;

/**
 * Builder for {@link Launcher}.
 * 
 * @author yushijinhun
 */
public class LauncherBuilder {

	/**
	 * Creates a new <code>LauncherBuilder</code> instance.
	 * 
	 * @return a new <code>LauncherBuilder</code> instance
	 */
	public static LauncherBuilder create() {
		return new LauncherBuilder();
	}

	/**
	 * Creates a new <code>Launcher</code> instance with default configurations.
	 * 
	 * @return a <code>Launcher</code> instance
	 */
	public static Launcher buildDefault() {
		return new LauncherBuilder().build();
	}

	protected boolean nativeFastCheck = false;
	protected boolean debugPrintCommandline = false;
	protected boolean launchInCurrentJVM = false;
	protected boolean stopThreadsAfterTerminated = false;
	protected boolean stopForcibly = false;

	protected LauncherBuilder() {
	}

	/**
	 * Sets whether to do a fast check on natives.
	 * <p>
	 * By default, this feature is off. In this case, when decompressing
	 * natives, the jmccc will fully compare the existing natives and the
	 * natives in jars. If, and only if, a existing native is modified, jmccc
	 * will replace it. Because replacing a native in use may cause the running
	 * JVM to be crashed.<br>
	 * If the feature is on, the jmccc won't compare the full content of
	 * natives. Jmccc only compares the sizes. This can improve the launching
	 * speed. But we cannot ensure the content of the natives are correct.
	 * 
	 * @param nativeFastCheck true to let jmccc do a fast check on natives
	 * @return the builder itself
	 */
	public LauncherBuilder setNativeFastCheck(boolean nativeFastCheck) {
		this.nativeFastCheck = nativeFastCheck;
		return this;
	}

	/**
	 * Sets whether to print the launch commandline for debugging.
	 * <p>
	 * By default, this feature is off.<br>
	 * The commandline will be printed to stderr in the following format:
	 * <blockquote>
	 * 
	 * <pre>
	 * jmccc:
	 * &lt;argument1&gt;
	 * &lt;argument2&gt;
	 * ......
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param debugPrintCommandline whether to print the launch commandline for
	 *            debugging.
	 * @return the builder itself
	 */
	public LauncherBuilder setDebugPrintCommandline(boolean debugPrintCommandline) {
		this.debugPrintCommandline = debugPrintCommandline;
		return this;
	}

	/**
	 * Sets whether to launch minecraft in the current jvm.
	 * <p>
	 * By default, this feature is off. If this feature is on, jmccc will invoke
	 * the 'main' method of minecraft in the current jvm, instead of starting a
	 * new process.
	 * <p>
	 * Minecraft invokes {@link System#exit(int)} when the game terminates. To
	 * prevent the JVM from exiting, use an
	 * {@link ExitInterceptionSecurityManager}.
	 * <p>
	 * This feature is <b>experimental</b>. Turning on this may cause some
	 * problems.
	 * 
	 * @param launchInCurrentJVM whether to launch minecraft in the current jvm
	 * @return the builder itself
	 * @see ExitInterceptionSecurityManager
	 * @see #setStopThreadsAfterTerminated(boolean)
	 * @see #setStopForcibly(boolean)
	 */
	public LauncherBuilder setLaunchInCurrentJVM(boolean launchInCurrentJVM) {
		this.launchInCurrentJVM = launchInCurrentJVM;
		return this;
	}

	/**
	 * Sets whether to invoke {@code Thread.stop()} method to stop the threads
	 * started by minecraft so as to clean up the environment.
	 * <p>
	 * By default, this feature is off. This option is available only when
	 * {@code launchInCurrentJVM} is on.
	 * <p>
	 * Use this only when you really need to stop all the minecraft threads,
	 * because {@code Thread.stop()} sometimes causes problems.<br>
	 * You don't need to turn on this if no
	 * {@link ExitInterceptionSecurityManager} is set, because the jvm exits
	 * when minecraft terminates (minecraft invokes {@link System#exit(int)}).
	 * 
	 * @param stopThreadsAfterTerminated whether to invoke {@code Thread.stop()}
	 *            method to stop the threads started by minecraft so as to clean
	 *            up the environment.
	 * @see #setStopForcibly(boolean)
	 * @see #setLaunchInCurrentJVM(boolean)
	 */
	public void setStopThreadsAfterTerminated(boolean stopThreadsAfterTerminated) {
		this.stopThreadsAfterTerminated = stopThreadsAfterTerminated;
	}

	/**
	 * Sets whether to use reflection and tricks to invoke the internal methods
	 * of jdk so as to stop the minecraft threads forcibly.
	 * <p>
	 * By default, this feature is off. This option is available only when
	 * {@code stopThreadsAfterTerminated} is on.
	 * 
	 * @param stopForcibly whether to use reflection and tricks to invoke the
	 *            internal methods of jdk so as to stop the minecraft threads
	 *            forcibly
	 * @see #setStopThreadsAfterTerminated(boolean)
	 * @see #setLaunchInCurrentJVM(boolean)
	 */
	public void setStopForcibly(boolean stopForcibly) {
		this.stopForcibly = stopForcibly;
	}

	/**
	 * Creates a new <code>Launcher</code> instance according to the
	 * configurations.
	 * 
	 * @return a <code>Launcher</code> instance
	 */
	public Launcher build() {
		if (launchInCurrentJVM) {
			ClassloadingLauncher launcher = new ClassloadingLauncher();
			launcher.setNativeFastCheck(nativeFastCheck);
			launcher.setStopThreads(stopThreadsAfterTerminated);
			launcher.setUseInternalStop(stopForcibly);
			return launcher;
		} else {
			ProcessLauncher launcher = new ProcessLauncher();
			launcher.setNativeFastCheck(nativeFastCheck);
			launcher.setDebugPrintCommandline(debugPrintCommandline);
			return launcher;
		}
	}

}
