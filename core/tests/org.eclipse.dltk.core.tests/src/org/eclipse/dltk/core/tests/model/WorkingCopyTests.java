/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.core.tests.model;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.IBuffer;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.internal.core.BufferManager;
import org.eclipse.dltk.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.team.core.RepositoryProvider;
import org.junit.Assert;

public class WorkingCopyTests extends ModifyingResourceTests {
	private static final String[] TEST_NATURE = new String[] { "org.eclipse.dltk.core.tests.testnature" };
	private ISourceModule cu = null;
	private ISourceModule copy = null;
	private IScriptProject scriptProject = null;

	public class TestWorkingCopyOwner extends WorkingCopyOwner {
		@Override
		public IBuffer createBuffer(ISourceModule workingCopy) {
			return new TestBuffer(workingCopy);
		}
	}

	public WorkingCopyTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {

			this.scriptProject = AbstractModelTests.createScriptProject("P", TEST_NATURE, new String[] { "src" });
			ModifyingResourceTests.createFolder("P/src/x/y");
			this.createFile("P/src/x/y/A.txt",
					"package x.y;\n" + "import java.io.File;\n" + "public class A {\n" + "  public class Inner {\n"
							+ "    public class InnerInner {\n" + "    }\n" + "    int innerField;\n"
							+ "    void innerMethod() {\n" + "    }\n" + "  }\n" + "  static String FIELD;\n" + "  {\n"
							+ "    FIELD = File.pathSeparator;\n" + "  }\n" + "  int field1;\n" + "  boolean field2;\n"
							+ "  public void foo() {\n" + "  }\n" + "}");
			this.cu = this.getSourceModule("P/src/x/y/A.txt");
			this.copy = cu.getWorkingCopy(null);
		} catch (CoreException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void tearDown() throws Exception {
		if (this.copy != null)
			this.copy.discardWorkingCopy();
		AbstractModelTests.deleteProject("P");
		super.tearDown();
	}

	/*
	 * Ensures that cancelling a make consistent operation doesn't leave the working
	 * copy closed (regression test for bug 61719 Incorrect fine grain delta after
	 * method copy-rename)
	 */
	public void testCancelMakeConsistent() throws ModelException {
		String newContents = "package x.y;\n" + "public class A {\n" + "  public void bar() {\n" + "  }\n" + "}";
		this.copy.getBuffer().setContents(newContents);
		NullProgressMonitor monitor = new NullProgressMonitor();
		monitor.setCanceled(true);
		try {
			this.copy.makeConsistent(monitor);
		} catch (OperationCanceledException e) {
			// got exception
		}
		assertTrue("Working copy should be opened", this.copy.isOpen());
	}

	/**
	 */
	public void testChangeContent() throws CoreException {
		String newContents = "package x.y;\n" + "public class A {\n" + "  public void bar() {\n" + "  }\n" + "}";
		this.copy.getBuffer().setContents(newContents);
		this.copy.reconcile(false, null, null);
		assertSourceEquals("Unexpected working copy contents", newContents, this.copy.getBuffer().getContents());
		this.copy.commitWorkingCopy(true, null);
		assertSourceEquals("Unexpected original cu contents", newContents, this.cu.getBuffer().getContents());
	}

	/*
	 * Ensures that one cannot commit the contents of a working copy on a read only
	 * cu.
	 */
	public void testChangeContentOfReadOnlyCU1() throws CoreException {
		IResource resource = this.cu.getUnderlyingResource();
		boolean readOnlyFlag = isReadOnly(resource);
		boolean didComplain = false;
		try {
			setReadOnly(resource, true);
			this.copy.getBuffer().setContents("invalid");
			this.copy.commitWorkingCopy(true, null);
		} catch (ModelException e) {
			didComplain = true;
		} finally {
			setReadOnly(resource, readOnlyFlag);
		}
		assertTrue("Should have complained about modifying a read-only unit:", didComplain);
		assertTrue("ReadOnly buffer got modified:", !this.cu.getBuffer().getContents().equals("invalid"));
	}

	/*
	 * Ensures that one can commit the contents of a working copy on a read only cu
	 * if a pessimistic repository provider allows it.
	 */
	public void testChangeContentOfReadOnlyCU2() throws CoreException {
		String newContents = "package x.y;\n" + "public class A {\n" + "  public void bar() {\n" + "  }\n" + "}";
		IResource resource = this.cu.getUnderlyingResource();
		IProject project = resource.getProject();
		boolean readOnlyFlag = isReadOnly(resource);
		try {
			RepositoryProvider.map(project, TestPessimisticProvider.NATURE_ID);
			TestPessimisticProvider.markWritableOnSave = true;
			setReadOnly(resource, true);
			this.copy.getBuffer().setContents(newContents);
			this.copy.commitWorkingCopy(true, null);
			assertSourceEquals("Unexpected original cu contents", newContents, this.cu.getBuffer().getContents());
		} finally {
			TestPessimisticProvider.markWritableOnSave = false;
			RepositoryProvider.unmap(project);
			setReadOnly(resource, readOnlyFlag);
		}
	}

	/**
	 * Ensures that the primary cu can be retrieved.
	 */
	public void testGetPrimaryCU() {
		IModelElement primary = this.copy.getPrimaryElement();
		assertTrue("Element is not a cu",
				primary instanceof ISourceModule && !((ISourceModule) primary).isWorkingCopy());
		assertTrue("Element should exist", primary.exists());
	}

	private static final class NonResourceSourceModule extends SourceModule {
		private long originStamp = IResource.NULL_STAMP;

		public NonResourceSourceModule(ModelElement parent, WorkingCopyOwner owner, String name) {
			super(parent, name, owner);
		}

		@Override
		public boolean isReadOnly() {
			return false;
		}

		@Override
		protected IBuffer createBuffer() {
			return BufferManager.createBuffer(this);
		}

		@Override
		protected char[] getBufferContent() throws ModelException {
			return new char[0];
		}

		@Override
		protected void openParent(Object childInfo, HashMap newElements, IProgressMonitor pm) throws ModelException {
		}

		@Override
		protected IStatus validateSourceModule(IResource resource) {
			return Status.OK_STATUS;
		}

		@Override
		public IResource getResource() {
			return null;
		}

		@Override
		protected String getNatureId() {
			return "test_nature";
		}

		@Override
		protected long getOriginTimestamp() {
			return originStamp;
		}
	}

	private static final IProblemRequestor DUMMY_REQUESTOR = new IProblemRequestor() {

		@Override
		public boolean isActive() {
			return true;
		}

		@Override
		public void endReporting() {
		}

		@Override
		public void beginReporting() {
		}

		@Override
		public void acceptProblem(IProblem problem) {
		}
	};

	public void testResourceLessSourceWorkingCopy() throws ModelException {
		NonResourceSourceModule subject = new NonResourceSourceModule((ModelElement) scriptProject,
				DefaultWorkingCopyOwner.PRIMARY, "test.py");
		Assert.assertFalse(subject.hasResourceChanged());
		subject.becomeWorkingCopy(DUMMY_REQUESTOR, new NullProgressMonitor());
		Assert.assertFalse(subject.hasResourceChanged());
		subject.discardWorkingCopy();
	}

	public void testResourceLessTimestamps() throws ModelException {
		NonResourceSourceModule subject = new NonResourceSourceModule((ModelElement) scriptProject,
				DefaultWorkingCopyOwner.PRIMARY, "test.py");
		subject.originStamp = 2;
		Assert.assertFalse(subject.hasResourceChanged());
		subject.becomeWorkingCopy(DUMMY_REQUESTOR, new NullProgressMonitor());
		Assert.assertFalse(subject.hasResourceChanged());
		subject.originStamp = 3;
		Assert.assertTrue(subject.hasResourceChanged());
		subject.discardWorkingCopy();
	}
}
