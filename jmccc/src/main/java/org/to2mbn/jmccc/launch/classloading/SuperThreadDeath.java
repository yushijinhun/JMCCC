package org.to2mbn.jmccc.launch.classloading;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Thrown if the thread must die.
 * 
 * @author yushijinhun
 */
class SuperThreadDeath extends ThreadDeath {

	private static final long serialVersionUID = 1L;

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	// Have fun~

	@Override
	public String getMessage() {
		throw new SuperThreadDeath();
	}

	@Override
	public String getLocalizedMessage() {
		throw new SuperThreadDeath();
	}

	@Override
	public Throwable getCause() {
		throw new SuperThreadDeath();
	}

	@Override
	public void printStackTrace() {
		throw new SuperThreadDeath();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		throw new SuperThreadDeath();
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		throw new SuperThreadDeath();
	}

	@Override
	public String toString() {
		throw new SuperThreadDeath();
	}

}
