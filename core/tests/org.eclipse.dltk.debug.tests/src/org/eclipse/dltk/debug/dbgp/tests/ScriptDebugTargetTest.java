/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/

package org.eclipse.dltk.debug.dbgp.tests;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.dltk.dbgp.IDbgpSession;
import org.eclipse.dltk.dbgp.IDbgpStackLevel;
import org.eclipse.dltk.dbgp.breakpoints.DbgpBreakpointConfig;
import org.eclipse.dltk.dbgp.commands.IDbgpCoreCommands;
import org.eclipse.dltk.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.dbgp.internal.DbgpSessionInfo;
import org.eclipse.dltk.dbgp.internal.DbgpStackLevel;
import org.eclipse.dltk.dbgp.internal.DbgpStatus;
import org.eclipse.dltk.dbgp.internal.managers.IDbgpStreamManager;
import org.eclipse.dltk.debug.core.DLTKDebugLaunchConstants;
import org.eclipse.dltk.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.debug.core.IDbgpService;
import org.eclipse.dltk.debug.core.IDebugOptions;
import org.eclipse.dltk.debug.core.model.DefaultDebugOptions;
import org.eclipse.dltk.debug.core.model.IScriptBreakpointLineMapper;
import org.eclipse.dltk.debug.core.model.IScriptBreakpointPathMapper;
import org.eclipse.dltk.debug.core.model.IScriptStackFrame;
import org.eclipse.dltk.internal.debug.core.model.IScriptBreakpointPathMapperExtension;
import org.eclipse.dltk.internal.debug.core.model.ScriptDebugTarget;
import org.junit.Assert;
import org.junit.Test;

public class ScriptDebugTargetTest {
	private final IDbgpService dbgpService = mock(IDbgpService.class);
	private final ILaunch launch = mock(ILaunch.class);
	private final IDebugOptions options = spy(DefaultDebugOptions.getDefaultInstance());
	private final IDbgpSession session = mock(IDbgpSession.class);
	private final IDbgpCoreCommands coreCommands = mock(IDbgpCoreCommands.class);

	{
		doReturn(coreCommands).when(session).getCoreCommands();
		when(launch.getAttribute(DLTKDebugLaunchConstants.ATTR_BREAK_ON_FIRST_LINE)).thenReturn("true");
		try {
			doReturn(new DbgpStatus(DbgpStatus.STATUS_BREAK, DbgpStatus.REASON_OK)).when(coreCommands).run();
			doReturn(new DbgpStatus(DbgpStatus.STATUS_BREAK, DbgpStatus.REASON_OK)).when(coreCommands).stepInto();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		doReturn(new DbgpSessionInfo("appId", "ideKey", "session", "threadId", "parentId", "language",
				URI.create("fileUri"), null)).when(session).getInfo();
		doReturn(mock(IDbgpStreamManager.class)).when(session).getStreamManager();
		doReturn(options).when(session).getDebugOptions();

	}

	@Deprecated
	@Test
	public void legacyPathMapperShouldBeFlushed() throws DebugException {
		IScriptBreakpointPathMapperExtension pathMapper = mock(IScriptBreakpointPathMapperExtension.class);
		ScriptDebugTarget subject = new ScriptDebugTarget("", dbgpService, "", launch, null,
				DefaultDebugOptions.getDefaultInstance()) {
			@Override
			protected IScriptBreakpointPathMapper createPathMapper() {
				return pathMapper;
			}
		};
		verifyZeroInteractions(pathMapper);
		subject.terminate();
		verify(pathMapper).clearCache();
	}

	@Deprecated
	@Test
	public void legacyReverseMappingMultiline() throws DbgpException, DebugException {
		IDbgpStackLevel[] stackLevels = new IDbgpStackLevel[] { createMethodLevel() };
		doReturn(stackLevels).when(coreCommands).getStackLevels();
		DLTKDebugPlugin.setSourceOffsetRetriever((frame, line, column, isEnd) -> {
			if (line == 15 && column == 11 && !isEnd)
				return 100;
			if (line == 16 && column == 17 && isEnd)
				return 200;
			return -1;
		});

		ScriptDebugTarget subject = new ScriptDebugTarget("testModelId", dbgpService, "testSessionId", launch, null,
				DefaultDebugOptions.getDefaultInstance()) {
			@Override
			protected IScriptBreakpointLineMapper createLineMapper() {
				return new IScriptBreakpointLineMapper() {

					@Override
					public void toDebuggerBreakpoint(URI uri, int line, DbgpBreakpointConfig bpConfig) {
						throw new UnsupportedOperationException();
					}

					@Override
					public IDbgpStackLevel getSourceStackLevel(IDbgpStackLevel iDbgpStackLevel) {
						return new DbgpStackLevel(URI.create("legIdeURI"), "where", 1, 15, -1, "methodName", 15, 11, 16,
								17);
					}
				};
			}
		};
		subject.getDbgpThreadAcceptor().acceptDbgpThread(session, null);
		join();
		IScriptStackFrame frame = (IScriptStackFrame) subject.getThreads()[0].getStackFrames()[0];
		Assert.assertEquals(URI.create("legIdeURI"), frame.getSourceURI());
		Assert.assertEquals(15, frame.getLineNumber());
		Assert.assertEquals(100, frame.getCharStart());
		Assert.assertEquals(201, frame.getCharEnd());
	}

	private DbgpStackLevel createMethodLevel() {
		return new DbgpStackLevel(URI.create("debuggerURI"), "where", 1, -1, 8, "methodName", -1, -1, -1, -1);
	}

	@Deprecated
	@Test
	public void legacyReverseMappingSpanningManyLines() throws DbgpException, DebugException {
		IDbgpStackLevel[] stackLevels = new IDbgpStackLevel[] { createMethodLevel() };
		doReturn(stackLevels).when(coreCommands).getStackLevels();
		DLTKDebugPlugin.setSourceOffsetRetriever((frame, line, column, isEnd) -> {
			if (line == 15 && column == 11 && !isEnd)
				return 100;
			if (line == 20 && column == 21 && isEnd)
				return 200;
			if (line == 15 && column == -1 && isEnd)
				return 300;

			return -1;
		});

		ScriptDebugTarget subject = new ScriptDebugTarget("testModelId", dbgpService, "testSessionId", launch, null,
				DefaultDebugOptions.getDefaultInstance()) {
			@Override
			protected IScriptBreakpointLineMapper createLineMapper() {
				return new IScriptBreakpointLineMapper() {

					@Override
					public void toDebuggerBreakpoint(URI uri, int line, DbgpBreakpointConfig bpConfig) {
						throw new UnsupportedOperationException();
					}

					@Override
					public IDbgpStackLevel getSourceStackLevel(IDbgpStackLevel iDbgpStackLevel) {
						return new DbgpStackLevel(URI.create("legIdeURI"), "where", 1, 15, -1, "methodName", 15, 11, 20,
								21);
					}
				};
			}
		};
		subject.getDbgpThreadAcceptor().acceptDbgpThread(session, null);
		join();
		IScriptStackFrame frame = (IScriptStackFrame) subject.getThreads()[0].getStackFrames()[0];
		Assert.assertEquals(URI.create("legIdeURI"), frame.getSourceURI());
		Assert.assertEquals(15, frame.getLineNumber());
		Assert.assertEquals(100, frame.getCharStart());
		Assert.assertEquals(301, frame.getCharEnd());
	}

	private static void join() {
		IJobManager jm = Job.getJobManager();
		try {
			wait: do {
				for (Job job : jm.find(null)) {
					int state = job.getState();
					if (state != Job.SLEEPING) {
						job.join(100000, null);
						continue wait;
					}
				}

			} while (false);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
