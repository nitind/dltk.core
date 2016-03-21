package org.eclipse.dltk.internal.testing.util;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;

public abstract class ConsoleLineNotifier implements IPatternMatchListener,
		IPropertyChangeListener {

	private TextConsole fConsole = null;

	@Override
	public void connect(TextConsole console) {
		fConsole = console;
		fConsole.addPropertyChangeListener(this);
	}

	@Override
	public synchronized void disconnect() {
		try {
			IDocument document = fConsole.getDocument();
			if (document != null) {
				int lastLine = document.getNumberOfLines() - 1;
				if (document.getLineDelimiter(lastLine) == null) {
					IRegion lineInformation = document
							.getLineInformation(lastLine);
					lineAppended(lineInformation, document.get(lineInformation
							.getOffset(), lineInformation.getLength()));
				}
			}
		} catch (BadLocationException e) {
		}
	}

	public synchronized void consoleClosed() {
		fConsole = null;
	}

	@Override
	public void matchFound(PatternMatchEvent event) {
		try {
			IDocument document = fConsole.getDocument();
			int lineOfOffset = document.getLineOfOffset(event.getOffset());
			String delimiter = document.getLineDelimiter(lineOfOffset);
			int strip = delimiter == null ? 0 : delimiter.length();
			Region region = new Region(event.getOffset(), event.getLength()
					- strip);
			lineAppended(region, document.get(region.getOffset(), region
					.getLength()));
		} catch (BadLocationException e) {
		}
	}

	public abstract void lineAppended(IRegion region, String content);

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(
				IConsoleConstants.P_CONSOLE_OUTPUT_COMPLETE)) {
			fConsole.removePropertyChangeListener(this);
			consoleClosed();
		}
	}

	@Override
	public String getPattern() {
		return ".*\\r(\\n?)|.*\\n"; //$NON-NLS-1$
	}

	@Override
	public int getCompilerFlags() {
		return 0;
	}

	@Override
	public String getLineQualifier() {
		return "\\n|\\r"; //$NON-NLS-1$
	}

}
