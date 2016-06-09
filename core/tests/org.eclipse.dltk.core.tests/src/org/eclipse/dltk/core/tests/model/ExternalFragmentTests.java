package org.eclipse.dltk.core.tests.model;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.core.tests.util.EnvironmentProvider;
import org.junit.Assert;

public class ExternalFragmentTests extends AbstractModelTests {
	private static final String PRJ_NAME = "Environment1";

	public ExternalFragmentTests(String name) {
		super(ModelTestsPlugin.PLUGIN_NAME, name);
	}

	@Override
	public void setUp() throws Exception {
		super.setUpSuite();
		setUpScriptProjectTo(PRJ_NAME, "Environment1");
	}

	@Override
	public void tearDown() throws Exception {
		deleteProject(PRJ_NAME);
		super.tearDownSuite();
	}

	private static class AbstractFileHandle implements IFileHandle {

		private final IEnvironment environment;
		private final IPath path;

		public AbstractFileHandle(IEnvironment environment, IPath path) {
			if (environment == null)
				throw new NullPointerException();
			if (path == null)
				throw new NullPointerException();
			this.environment = environment;
			if (path.isAbsolute())
				throw new IllegalArgumentException();
			this.path = path;
		}

		@Override
		public IEnvironment getEnvironment() {
			return environment;
		}

		@Override
		public String getEnvironmentId() {
			return getEnvironment().getId();
		}

		@Override
		public IPath getPath() {
			return path;
		}

		@Override
		public String toOSString() {
			return null;
		}

		@Override
		public String getCanonicalPath() {
			return environment.getCanonicalPath(path);
		}

		@Override
		public IPath getFullPath() {
			return EnvironmentPathUtils.getFullPath(getEnvironmentId(), path);
		}

		@Override
		public String getName() {
			return path.lastSegment();
		}

		@Override
		public URI toURI() {
			return environment.getURI(path);
		}

		@Override
		public IFileHandle getParent() {
			if (path.isEmpty())
				return null;
			return getEnvironment().getFile(path.removeLastSegments(1));
		}

		@Override
		public IFileHandle[] getChildren() {
			return null;
		}

		@Override
		public IFileHandle getChild(String path) {
			return null;
		}

		@Override
		public boolean exists() {
			return true;
		}

		@Override
		public InputStream openInputStream(IProgressMonitor monitor)
				throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public OutputStream openOutputStream(IProgressMonitor monitor)
				throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isSymlink() {
			return false;
		}

		@Override
		public boolean isDirectory() {
			return false;
		}

		@Override
		public boolean isFile() {
			return true;
		}

		@Override
		public long lastModified() {
			return 0;
		}

		@Override
		public long length() {
			return 0;
		}

		@Override
		public void move(IFileHandle destination) throws CoreException {
			throw new UnsupportedOperationException();
		}

	}

	public static class FolderHandle extends AbstractFileHandle {

		private final Collection<String> children = new ArrayList<String>();

		public FolderHandle(IEnvironment environment, IPath path) {
			super(environment, path);
			if (children == null)
				throw new NullPointerException();
		}

		@Override
		public boolean isDirectory() {
			return true;
		}
		
		@Override
		public IFileHandle[] getChildren() {
			ArrayList<IFileHandle> rv = new ArrayList<IFileHandle>();
			for (String name : children)
				rv.add(getEnvironment().getFile(getPath().append(name)));
			return rv.toArray(new IFileHandle[0]);
		}

		@Override
		public IFileHandle getChild(String path) {
			return getEnvironment().getFile(getPath().append(path));
		}

		public void addChild(String name) {
			children.add(name);
		}


	}

	public static class FileHandle extends AbstractFileHandle {
		static {
			Platform.getAdapterManager()
					.registerAdapters(new IAdapterFactory() {

						@Override
						public Class<?>[] getAdapterList() {
							return new Class<?>[] { Charset.class };
						}

						@Override
						public <T> T getAdapter(Object adaptableObject,
								Class<T> adapterType) {
							FileHandle file = (FileHandle) adaptableObject;
							if (adapterType.isAssignableFrom(Charset.class)) {
								return adapterType.cast(file.getCharset());
							}
							return null;
						}
					}, FileHandle.class);
		}

		private final byte[] content;
		private final Charset charset;

		public FileHandle(IEnvironment environment, IPath path,
				byte[] content, Charset charset) {
			super(environment, path);
			this.content = content;
			this.charset = charset;
		}

		protected Charset getCharset() {
			return charset;
		}

		@Override
		public InputStream openInputStream(IProgressMonitor monitor)
				throws IOException {
			return new ByteArrayInputStream(content);
		}

		@Override
		public long length() {
			return content.length;
		}

	}


	class Environment extends PlatformObject implements IEnvironment {
		private final Map<IPath, IFileHandle> files = new HashMap<IPath, IFileHandle>();

		@Override
		public boolean isLocal() {
			return false;
		}

		@Override
		public IFileHandle getFile(final IPath path) {
			return files.get(path.makeRelative());
		}

		@Override
		public String getId() {
			return "testEnv";
		}

		@Override
		public String getSeparator() {
			return "" + IPath.SEPARATOR;
		}

		@Override
		public char getSeparatorChar() {
			return IPath.SEPARATOR;
		}

		@Override
		public String getPathsSeparator() {
			return ":";
		}

		@Override
		public char getPathsSeparatorChar() {
			return ':';
		}

		@Override
		public String getName() {
			return "Test environment";
		}

		@Override
		public String convertPathToString(IPath path) {
			return path.toPortableString();
		}

		@Override
		public URI getURI(IPath location) {
			try {
				return new URI(getId(), location.toPortableString(), null);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public IFileHandle getFile(URI locationURI) {
			return getFile(Path
					.fromPortableString(locationURI.getSchemeSpecificPart()));
		}

		@Override
		public String getCanonicalPath(IPath path) {
			return path.toPortableString();
		}

		@Override
		public boolean isConnected() {
			return true;
		}

		@Override
		public boolean connect() {
			return true;
		}

		private FolderHandle getFolder(IPath path) {
			IFileHandle folder = files.get(path);
			if (folder instanceof FolderHandle)
				return (FolderHandle) folder;
			if (folder == null) {
				FolderHandle nfolder = new FolderHandle(this, path);
				put(nfolder);
				return nfolder;
			}
			return null;
		}

		public void put(IFileHandle fileHandle) {
			IPath path = fileHandle.getPath();
			files.put(path, fileHandle);
			if (!path.isEmpty())
				getFolder(path.removeLastSegments(1))
						.addChild(path.lastSegment());
		}

	}

	public void testWindowsLocale() throws ModelException, IOException {
		Charset charset = Charset.forName("windows-1251");
		checkCharsetFlow(charset, "Русский текст1");
	}

	public void testUtfLocale() throws ModelException, IOException {
		Charset charset = Charset.forName(Util.UTF_8);
		checkCharsetFlow(charset, "Русский текст2");
	}

	private void checkCharsetFlow(Charset charset, String content)
			throws ModelException, IOException {
		Environment env = new Environment();
		Path path = new Path("X.txt");
		env.put(new FileHandle(env, path, content.getBytes(charset), charset));
		Closeable environmentUnsetter = EnvironmentProvider.setEnvironment(env);
		try {
			ISourceModule module = getSourceModule(PRJ_NAME, "testEnv/:/",
					path);
			Assert.assertEquals(content, module.getSource());
		} finally {
			environmentUnsetter.close();
		}
	}
}
