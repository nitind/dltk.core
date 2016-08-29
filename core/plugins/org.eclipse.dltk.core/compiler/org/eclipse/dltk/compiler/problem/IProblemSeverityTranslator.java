package org.eclipse.dltk.compiler.problem;

public interface IProblemSeverityTranslator {

	ProblemSeverity getSeverity(IProblemIdentifier problemId,
			ProblemSeverity defaultServerity);

	/**
	 * Implementation of {@link IProblemSeverityTranslator} which always returns
	 * the default value.
	 */
	static IProblemSeverityTranslator IDENTITY = (problemId,
			defaultSeverity) -> {
		if (defaultSeverity == null
				|| defaultSeverity == ProblemSeverity.DEFAULT) {
			return ProblemSeverity.WARNING;
		} else {
			return defaultSeverity;
		}
	};

}
