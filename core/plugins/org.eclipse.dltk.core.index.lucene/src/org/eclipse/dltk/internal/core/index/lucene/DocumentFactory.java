/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index.lucene;

import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_DOC;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_ELEMENT_NAME;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_METADATA;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_PARENT;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_PATH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_QUALIFIER;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_CC_NAME;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_ELEMENT_NAME_LC;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_PARENT;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_PATH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_QUALIFIER;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_FLAGS;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_LENGTH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_NAME_LENGTH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_NAME_OFFSET;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_OFFSET;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_TIMESTAMP;

import org.apache.lucene.document.BinaryDocValuesField;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.util.BytesRef;
import org.eclipse.dltk.core.index2.IIndexingRequestor.DeclarationInfo;
import org.eclipse.dltk.core.index2.IIndexingRequestor.ReferenceInfo;

/**
 * <p>
 * Factory for creating different types of Lucene documents.
 * </p>
 * <p>
 * To boost the performance of documents search and related data retrieval,
 * numeric and binary document values are being used in pair with non-stored
 * fields. It basically means that non-stored fields are used for document
 * search purposes while numeric and binary document values are used to retrieve
 * the related data for particular search matches.
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
public enum DocumentFactory {
	INSTANCE;

	private String EMPTY = new String();

	private Document timestamp = new Document();
	private StringField timestampPath;
	private NumericDocValuesField timestampValue;

	private Document reference = new Document();
	private StringField referenceFPath;
	private StringField referenceFQualifier;
	private StringField referenceFElementNameLC;
	private NumericDocValuesField referenceNDVOffset;
	private NumericDocValuesField referenceNDVLength;
	private BinaryDocValuesField referenceBPath;
	private BinaryDocValuesField referenceBElementName;
	private BinaryDocValuesField referenceBQualifier;
	private BinaryDocValuesField referenceBMetadata;

	private Document declaration = new Document();

	private StringField declarationFPath;
	private StringField declarationFQualifier;
	private StringField declarationFParent;
	private StringField declarationFElementNameLC;
	private StringField declarationFElementNameCC;
	private NumericDocValuesField declarationNDVOffset;
	private NumericDocValuesField declarationNDVLength;
	private NumericDocValuesField declarationNDVNameOffset;
	private NumericDocValuesField declarationNDVNameLength;
	private NumericDocValuesField declarationNDVFlags;

	private BinaryDocValuesField declarationBPath;
	private BinaryDocValuesField declarationBElementName;
	private BinaryDocValuesField declarationBQualifier;
	private BinaryDocValuesField declarationBMetadata;
	private BinaryDocValuesField declarationBParent;
	private BinaryDocValuesField declarationBDoc;

	private DocumentFactory() {
		timestampPath = addStringEntry(timestamp, F_PATH, true);
		timestampValue = addLongEntry(timestamp, NDV_TIMESTAMP);

		referenceFPath = addStringEntry(reference, F_PATH, false);
		referenceFQualifier = addStringEntry(reference, F_QUALIFIER, false);
		referenceFElementNameLC = addStringEntry(reference, F_ELEMENT_NAME_LC,
				false);

		// Add numeric doc values
		referenceNDVOffset = addLongEntry(reference, NDV_OFFSET);
		referenceNDVLength = addLongEntry(reference, NDV_LENGTH);
		// Add text as binary doc values
		referenceBPath = addBinaryEntry(reference, BDV_PATH);
		referenceBElementName = addBinaryEntry(reference, BDV_ELEMENT_NAME);
		referenceBQualifier = addBinaryEntry(reference, BDV_QUALIFIER);
		referenceBMetadata = addBinaryEntry(reference, BDV_METADATA);

		declarationFPath = addStringEntry(declaration, F_PATH, false);
		declarationFParent = addStringEntry(declaration, F_PARENT, false);
		declarationFQualifier = addStringEntry(declaration, F_QUALIFIER, false);
		declarationFElementNameLC = addStringEntry(declaration,
				F_ELEMENT_NAME_LC, false);
		declarationFElementNameCC = addStringEntry(declaration, F_CC_NAME,
				false);
		// Add numeric doc values
		declarationNDVOffset = addLongEntry(declaration, NDV_OFFSET);
		declarationNDVLength = addLongEntry(declaration, NDV_LENGTH);
		declarationNDVNameOffset = addLongEntry(declaration, NDV_NAME_OFFSET);
		declarationNDVNameLength = addLongEntry(declaration, NDV_NAME_LENGTH);
		declarationNDVFlags = addLongEntry(declaration, NDV_FLAGS);
		// Add text as binary doc values
		declarationBPath = addBinaryEntry(declaration, BDV_PATH);
		declarationBElementName = addBinaryEntry(declaration, BDV_ELEMENT_NAME);
		declarationBParent = addBinaryEntry(declaration, BDV_PARENT);
		declarationBQualifier = addBinaryEntry(declaration, BDV_QUALIFIER);
		declarationBMetadata = addBinaryEntry(declaration, BDV_METADATA);
		declarationBDoc = addBinaryEntry(declaration, BDV_DOC);
	}

	/**
	 * Creates and returns a document for provided reference info.
	 * 
	 * @param source
	 * @param info
	 * @return a document for provided reference info
	 */
	public Document createForReference(String source, ReferenceInfo info) {
		// Fields for search (no store, doc values will be used instead)
		referenceFPath.setStringValue(source);
		referenceFQualifier.setStringValue(
				info.qualifier != null ? info.qualifier : EMPTY);
		referenceFElementNameLC.setStringValue(info.elementName.toLowerCase());
		// Add numeric doc values
		referenceNDVOffset.setLongValue(info.offset);
		referenceNDVLength.setLongValue(info.length);

		// Add text as binary doc values
		referenceBPath.setBytesValue(source.getBytes());
		referenceBElementName.setBytesValue(info.elementName.getBytes());
		referenceBQualifier
				.setBytesValue(info.qualifier == null ? BytesRef.EMPTY_BYTES
						: info.qualifier.getBytes());
		referenceBMetadata
				.setBytesValue(info.metadata == null ? BytesRef.EMPTY_BYTES
						: info.metadata.getBytes());
		return reference;
	}

	/**
	 * Creates and returns a document for provided declaration info.
	 * 
	 * @param source
	 * @param info
	 * @return a document for provided declaration info
	 */
	public Document createForDeclaration(String source, DeclarationInfo info) {
		// Fields for search (no store, doc values will be used instead)
		declarationFPath.setStringValue(source);
		declarationFParent
				.setStringValue(info.parent != null ? info.parent : EMPTY);
		declarationFQualifier.setStringValue(
				info.qualifier != null ? info.qualifier : EMPTY);
		declarationFElementNameLC
				.setStringValue(info.elementName.toLowerCase());
		declarationFElementNameCC.setStringValue(ccValue(info.elementName));
		// Add numeric doc values

		declarationNDVOffset.setLongValue(info.offset);
		declarationNDVLength.setLongValue(info.length);
		declarationNDVNameOffset.setLongValue(info.nameOffset);
		declarationNDVNameLength.setLongValue(info.nameLength);
		declarationNDVFlags.setLongValue(info.flags);

		// Add text as binary doc values
		declarationBPath.setBytesValue(source.getBytes());
		declarationBElementName.setBytesValue(
				info.elementName != null ? info.elementName.getBytes()
						: BytesRef.EMPTY_BYTES);
		declarationBParent
				.setBytesValue(info.parent != null ? info.parent.getBytes()
						: BytesRef.EMPTY_BYTES);
		declarationBQualifier.setBytesValue(
				info.qualifier != null ? info.qualifier.getBytes()
						: BytesRef.EMPTY_BYTES);
		declarationBMetadata
				.setBytesValue(info.metadata != null ? info.metadata.getBytes()
						: BytesRef.EMPTY_BYTES);
		declarationBDoc.setBytesValue(
				info.doc != null ? info.doc.getBytes() : BytesRef.EMPTY_BYTES);
		return declaration;
	}

	/**
	 * Creates and returns a document for source file time stamp.
	 * 
	 * @param source
	 * @param timestamp
	 * @return a document for source file time stamp
	 */
	public Document createForTimestamp(String source, long timestamp) {
		timestampValue.setLongValue(timestamp);
		timestampPath.setStringValue(source);

		return this.timestamp;
	}

	private NumericDocValuesField addLongEntry(Document doc, String category) {
		NumericDocValuesField f = new NumericDocValuesField(category, 0L);
		doc.add(f);

		return f;
	}

	private StringField addStringEntry(Document doc, String category,
			boolean store) {
		StringField f = new StringField(category, EMPTY,
				store ? Field.Store.YES : Field.Store.NO);
		doc.add(f);
		return f;
	}

	private String ccValue(String value) {
		StringBuilder camelCaseNameBuf = new StringBuilder();
		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if (Character.isUpperCase(ch)) {
				camelCaseNameBuf.append(ch);
			} else if (i == 0) {
				// Not applicable for camel case search
				break;
			}
		}
		return camelCaseNameBuf.toString();
	}

	private BinaryDocValuesField addBinaryEntry(Document doc, String category) {
		BinaryDocValuesField f = new BinaryDocValuesField(category,
				new BytesRef());
		doc.add(f);

		return f;
	}

}
