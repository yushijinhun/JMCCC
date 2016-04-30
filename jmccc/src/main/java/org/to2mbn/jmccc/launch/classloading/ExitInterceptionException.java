package org.to2mbn.jmccc.launch.classloading;

/**
 * Thrown when {@link ExitInterceptionSecurityManager} decides to intercept a '
 * {@code System.exit(int)}' call.
 * 
 * @author yushijinhun
 */
public class ExitInterceptionException extends SecurityException {

	private static final long serialVersionUID = 1L;

	private int exitCode;

	public ExitInterceptionException(int exitCode) {
		this.exitCode = exitCode;
	}

	public int getExitCode() {
		return exitCode;
	}

}
