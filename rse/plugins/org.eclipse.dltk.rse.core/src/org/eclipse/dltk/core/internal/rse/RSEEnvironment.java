package org.eclipse.dltk.core.internal.rse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.core.subsystems.ISubSystem;
import org.eclipse.rse.internal.efs.RSEFileSystem;
import org.eclipse.rse.subsystems.files.core.subsystems.IRemoteFileSubSystem;

@SuppressWarnings("restriction")
public class RSEEnvironment implements IEnvironment {
	private IRemoteFileSubSystem fs;
	private IHost host;
	private static final Map<IRemoteFileSubSystem, Boolean> tryToConnect = new HashMap<>();

	public RSEEnvironment(IRemoteFileSubSystem fs) {
		this.fs = fs;
		this.host = fs.getConnectorService().getHost();
	}

	@Override
	public boolean isLocal() {
		return false;
	}

	@Override
	public IFileHandle getFile(IPath path) {
		if (path == null || Path.EMPTY.equals(path)) {
			throw new IllegalArgumentException(
					Messages.RSEEnvironment_EmptyFileNameError);
		}
		return new RSEFileHandle(this, getURIFor(host, path.toString()));
	}

	@Override
	public String getId() {
		return RSEEnvironmentProvider.RSE_ENVIRONMENT_PREFIX
				+ host.getAliasName();
	}

	@Override
	public String getSeparator() {
		return fs.getSeparator();
	}

	@Override
	public char getSeparatorChar() {
		return fs.getSeparatorChar();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IEnvironment) {
			IEnvironment other = (IEnvironment) obj;
			return getId().equals(other.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String getName() {
		return host.getAliasName()
				+ Messages.RSEEnvironment_EnvironmentNameSuffix;
	}

	public IHost getHost() {
		return host;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Class<T> adapter) {
		return (T) Platform.getAdapterManager()
				.loadAdapter(this, adapter.getName());
	}

	@Override
	public URI getURI(IPath location) {
		return getURIFor(host, location.toString());
	}

	@Override
	public String convertPathToString(IPath path) {
		if (host.getSystemType().isWindows()) {
			return path.toString().replace('/', '\\');
		} else {
			return path.toString();
		}
	}

	@Override
	public IFileHandle getFile(URI locationURI) {
		if (RSEEnvironmentProvider.RSE_SCHEME.equalsIgnoreCase(locationURI
				.getScheme())
				&& locationURI.getHost().equals(host.getAliasName())) {
			return new RSEFileHandle(this, locationURI);
		} else {
			final URI[] resolved = EnvironmentManager.resolve(locationURI);
			for (int i = 0; i < resolved.length; ++i) {
				final URI newLocation = resolved[i];
				if (RSEEnvironmentProvider.RSE_SCHEME
						.equalsIgnoreCase(newLocation.getScheme())
						&& newLocation.getHost().equals(host.getAliasName())) {
					return new RSEFileHandle(this, newLocation);
				}
			}
			return null;
		}
	}

	@Override
	public String getPathsSeparator() {
		return Character.toString(getPathsSeparatorChar());
	}

	@Override
	public char getPathsSeparatorChar() {
		return host.getSystemType().isWindows() ? ';' : ':';
	}

	@Override
	public String getCanonicalPath(IPath path) {
		IFileHandle file = getFile(path);
		if (file instanceof RSEFileHandle) {
			RSEFileHandle handle = (RSEFileHandle) file;
			return handle.resolvePath();
		}
		return convertPathToString(path);
	}

	public static URI getURIFor(IHost host, String path) {
		return RSEFileSystem.getURIFor(host.getHostName(), path);
	}

	public static IFileStore getStoreFor(URI locationURI) {
		return RSEFileSystem.getInstance().getStore(locationURI);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean isConnected() {
		// IConnectorService[] services = host.getConnectorServices();
		// int connected = 0;
		// for (IConnectorService service : services) {
		// if (service.isConnected()) {
		// connected++;
		// }
		// }
		// return connected == services.length;
		ISubSystem[] subSystems = host.getSubSystems();
		for (ISubSystem subsystem : subSystems) {
			if (subsystem instanceof IRemoteFileSubSystem) {
				return subsystem.isConnected();
			}
		}
		return false;
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean connect() {
		return connect(false);
	}

	/**
	 * @since 2.0
	 */
	public boolean connect(boolean force) {
		if (isConnected()) {
			return true;
		}
		connectUnsafe(force);
		return isConnected();
	}

	private void connectUnsafe(boolean force) {
		boolean tryToConnect = isTryToConnect();
		if (force || tryToConnect) {
			RSEConnectionQueryManager.getInstance().connectTo(host);
			setTryToConnect(false);
		}
	}

	/**
	 * @since 2.0
	 */
	public boolean isTryToConnect() {
		boolean tryToConnect = true;
		synchronized (RSEEnvironment.tryToConnect) {
			final Boolean value = RSEEnvironment.tryToConnect.get(fs);
			if (value != null) {
				tryToConnect = value.booleanValue();
			}
		}
		return tryToConnect;
	}

	/**
	 * @since 2.0
	 */
	public void setTryToConnect(boolean value) {
		synchronized (RSEEnvironment.tryToConnect) {
			RSEEnvironment.tryToConnect.put(fs, Boolean.valueOf(value));
		}
	}

}
