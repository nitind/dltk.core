package org.eclipse.dltk.launching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

public class StandardSourcepathProvider extends StandardBuildpathProvider {

	@Override
	public IRuntimeBuildpathEntry[] computeUnresolvedBuildpath(
			ILaunchConfiguration configuration) throws CoreException {
		boolean useDefault = configuration.getAttribute(
				ScriptLaunchConfigurationConstants.ATTR_DEFAULT_SOURCEPATH,
				true);
		IRuntimeBuildpathEntry[] entries = null;
		if (useDefault) {
			// the default source lookup path is the same as the classpath
			entries = super.computeUnresolvedBuildpath(configuration);
		} else {
			// recover persisted source path
			entries = recoverRuntimePath(configuration,
					ScriptLaunchConfigurationConstants.ATTR_SOURCEPATH);
		}
		return entries;

	}

	@Override
	public IRuntimeBuildpathEntry[] resolveBuildpath(
			IRuntimeBuildpathEntry[] entries,
			ILaunchConfiguration configuration) throws CoreException {
		List all = new UniqueList(entries.length);
		for (int i = 0; i < entries.length; i++) {
			switch (entries[i].getType()) {
			case IRuntimeBuildpathEntry.PROJECT:
				// a project resolves to itself for source lookup (rather than
				// the class file output locations)
				all.add(entries[i]);
				break;
			default:
				IRuntimeBuildpathEntry[] resolved = ScriptRuntime
						.resolveRuntimeBuildpathEntry(entries[1],
								configuration);
				all.add(resolved);
				break;
			}
		}

		return (IRuntimeBuildpathEntry[]) all
				.toArray(new IRuntimeBuildpathEntry[all.size()]);
	}

	class UniqueList<T> extends ArrayList<T> {
		private static final long serialVersionUID = -7402160651027036270L;
		HashSet<T> set;

		public UniqueList(int length) {
			super(length);
			set = new HashSet<T>(length);
		}

		@Override
		public void add(int index, T element) {
			if (set.add(element))
				super.add(index, element);
		}

		@Override
		public boolean add(T o) {
			if (set.add(o))
				return super.add(o);
			return false;
		}

		@Override
		public boolean addAll(Collection<? extends T> c) {
			if (set.addAll(c))
				return super.addAll(c);
			return false;
		}

		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			if (set.addAll(c))
				return super.addAll(index, c);
			return false;
		}

		@Override
		public void clear() {
			set.clear();
			super.clear();
		}

		@Override
		public boolean contains(Object elem) {
			return set.contains(elem);
		}

		@Override
		public void ensureCapacity(int minCapacity) {
			super.ensureCapacity(minCapacity);
		}

		@Override
		public T remove(int index) {
			T object = super.remove(index);
			set.remove(object);
			return object;
		}

		@Override
		protected void removeRange(int fromIndex, int toIndex) {
			for (int index = fromIndex; index <= toIndex; index++)
				remove(index);
		}

		@Override
		public T set(int index, T element) {
			set.remove(element);
			if (set.add(element))
				return super.set(index, element);
			return null; // should not happen.
		}
	}
}
