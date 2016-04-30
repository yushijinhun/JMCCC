package org.to2mbn.jmccc.launch.classloading;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

/**
 * A SecurityManager which is used to intercept the '{@code System.exit(int)}'
 * calls from minecraft.
 * <p>
 * If you want to prevent minecraft exiting jvm, you should call:
 * <pre>
 * System.setSecurityManager(new ExitInterceptionSecurityManager());
 * </pre>
 * 
 * @author yushijinhun
 */
public class ExitInterceptionSecurityManager extends SecurityManager {

	private SecurityManager sm;

	public ExitInterceptionSecurityManager() {
		this(null);
	}

	public ExitInterceptionSecurityManager(SecurityManager sm) {
		this.sm = sm;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean getInCheck() {
		if (sm == null)
			return super.getInCheck();
		return sm.getInCheck();
	}

	@Override
	public Object getSecurityContext() {
		if (sm == null)
			return super.getSecurityContext();
		return sm.getSecurityContext();
	}

	@Override
	public void checkPermission(Permission perm) {
		if (sm != null)
			sm.checkPermission(perm);
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
		if (sm != null)
			sm.checkPermission(perm, context);
	}

	@Override
	public void checkCreateClassLoader() {
		if (sm != null)
			sm.checkCreateClassLoader();
	}

	@Override
	public void checkAccess(Thread t) {
		if (sm != null)
			sm.checkAccess(t);
	}

	@Override
	public void checkAccess(ThreadGroup g) {
		if (sm != null)
			sm.checkAccess(g);
	}

	@Override
	public void checkExit(int status) {
		ClassLoader ctxCl = Thread.currentThread().getContextClassLoader();
		while (ctxCl != null) {
			if (ctxCl instanceof MinecraftClassLoader) {
				throw new ExitInterceptionException(status);
			}
			ctxCl = ctxCl.getParent();
		}

		if (sm != null)
			sm.checkExit(status);
	}

	@Override
	public void checkExec(String cmd) {
		if (sm != null)
			sm.checkExec(cmd);
	}

	@Override
	public void checkLink(String lib) {
		if (sm != null)
			sm.checkLink(lib);
	}

	@Override
	public void checkRead(FileDescriptor fd) {
		if (sm != null)
			sm.checkRead(fd);
	}

	@Override
	public void checkRead(String file) {
		if (sm != null)
			sm.checkRead(file);
	}

	@Override
	public void checkRead(String file, Object context) {
		if (sm != null)
			sm.checkRead(file, context);
	}

	@Override
	public void checkWrite(FileDescriptor fd) {
		if (sm != null)
			sm.checkWrite(fd);
	}

	@Override
	public void checkWrite(String file) {
		if (sm != null)
			sm.checkWrite(file);
	}

	@Override
	public void checkDelete(String file) {
		if (sm != null)
			sm.checkDelete(file);
	}

	@Override
	public void checkConnect(String host, int port) {
		if (sm != null)
			sm.checkConnect(host, port);
	}

	@Override
	public void checkConnect(String host, int port, Object context) {
		if (sm != null)
			sm.checkConnect(host, port, context);
	}

	@Override
	public void checkListen(int port) {
		if (sm != null)
			sm.checkListen(port);
	}

	@Override
	public void checkAccept(String host, int port) {
		sm.checkAccept(host, port);
	}

	@Override
	public void checkMulticast(InetAddress maddr) {
		if (sm != null)
			sm.checkMulticast(maddr);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void checkMulticast(InetAddress maddr, byte ttl) {
		if (sm != null)
			sm.checkMulticast(maddr, ttl);
	}

	@Override
	public void checkPropertiesAccess() {
		if (sm != null)
			sm.checkPropertiesAccess();
	}

	@Override
	public void checkPropertyAccess(String key) {
		if (sm != null)
			sm.checkPropertyAccess(key);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean checkTopLevelWindow(Object window) {
		if (sm == null)
			return true;
		return sm.checkTopLevelWindow(window);
	}

	@Override
	public void checkPrintJobAccess() {
		if (sm != null)
			sm.checkPrintJobAccess();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void checkSystemClipboardAccess() {
		if (sm != null)
			sm.checkSystemClipboardAccess();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void checkAwtEventQueueAccess() {
		if (sm != null)
			sm.checkAwtEventQueueAccess();
	}

	@Override
	public void checkPackageAccess(String pkg) {
		if (sm != null)
			sm.checkPackageAccess(pkg);
	}

	@Override
	public void checkPackageDefinition(String pkg) {
		if (sm != null)
			sm.checkPackageDefinition(pkg);
	}

	@Override
	public void checkSetFactory() {
		if (sm != null)
			sm.checkSetFactory();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void checkMemberAccess(Class<?> clazz, int which) {
		if (sm != null)
			sm.checkMemberAccess(clazz, which);
	}

	@Override
	public void checkSecurityAccess(String target) {
		if (sm != null)
			sm.checkSecurityAccess(target);
	}

	@Override
	public ThreadGroup getThreadGroup() {
		if (sm == null)
			return super.getThreadGroup();
		return sm.getThreadGroup();
	}

}
