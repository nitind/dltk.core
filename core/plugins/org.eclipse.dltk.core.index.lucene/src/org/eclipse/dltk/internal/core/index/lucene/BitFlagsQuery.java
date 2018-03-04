/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index.lucene;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.search.ConstantScoreScorer;
import org.apache.lucene.search.ConstantScoreWeight;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.TwoPhaseIterator;
import org.apache.lucene.search.Weight;
import org.eclipse.dltk.ast.Modifiers;

/**
 * Query for scoring declaration modifiers represented by corresponding DLTK's
 * modifiers bit flags {@link Modifiers}.
 * 
 * @author Michal Niewrzal, Bartlomiej Laczkowski
 */
public class BitFlagsQuery extends Query {

	private final int fTrueFlags;
	private final int fFalseFlags;

	public BitFlagsQuery(final int trueFlags, final int falseFlags) {
		fTrueFlags = trueFlags;
		fFalseFlags = falseFlags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fFalseFlags;
		result = prime * result + fTrueFlags;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BitFlagsQuery other = (BitFlagsQuery) obj;
		if (fFalseFlags != other.fFalseFlags)
			return false;
		if (fTrueFlags != other.fTrueFlags)
			return false;
		return true;
	}

	@Override
	public String toString(String input) {
		return MessageFormat.format(
				"BitFlagsQuery(Field: {0}, True Flags: {1}, False Flags: {2})", //$NON-NLS-1$
				IndexFields.NDV_FLAGS, fTrueFlags, fFalseFlags);
	}

	@Override
	public Weight createWeight(IndexSearcher searcher, boolean needsScores,
			float boost) throws IOException {
		return new ConstantScoreWeight(this, 10) {

			@Override
			public Scorer scorer(LeafReaderContext context) throws IOException {

				NumericDocValues fields = context.reader()
						.getNumericDocValues(IndexFields.NDV_FLAGS);
				if (fields == null) {
					return null;
				}
				TwoPhaseIterator iterator = new TwoPhaseIterator(fields) {

					@Override
					public boolean matches() throws IOException {
						long flags = fields.longValue();
						if (fTrueFlags != 0 && (flags & fTrueFlags) == 0) {
							return false;
						}
						if (fFalseFlags != 0 && (flags & fFalseFlags) != 0) {
							return false;
						}
						return true;
					}

					@Override
					public float matchCost() {
						return 2;
					}
				};
				return new ConstantScoreScorer(this, 10, iterator);
			}
		};
	}
}
