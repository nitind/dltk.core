package org.eclipse.dltk.internal.debug.ui;

/**
 * Information about a detail formatter.
 */
public class DetailFormatter implements Comparable<DetailFormatter> {

	private boolean fEnabled;

	private String fTypeName;

	private String fSnippet;

	public DetailFormatter(String typeName, String snippet, boolean enabled) {
		fTypeName = typeName;
		fSnippet = snippet;
		fEnabled = enabled;
	}

	/**
	 * Indicate if this pretty should be used or not.
	 *
	 * @return boolean
	 */
	public boolean isEnabled() {
		return fEnabled;
	}

	/**
	 * Returns the code snippet.
	 *
	 * @return String
	 */
	public String getSnippet() {
		return fSnippet;
	}

	/**
	 * Returns the type name.
	 *
	 * @return String
	 */
	public String getTypeName() {
		return fTypeName;
	}

	/**
	 * Sets the enabled flag.
	 *
	 * @param enabled
	 *                    the new value of the flag
	 */
	public void setEnabled(boolean enabled) {
		fEnabled = enabled;
	}

	/**
	 * Sets the code snippet.
	 *
	 * @param snippet
	 *                    the snippet to set
	 */
	public void setSnippet(String snippet) {
		fSnippet = snippet;
	}

	/**
	 * Sets the type name.
	 *
	 * @param typeName
	 *                     the type name to set
	 */
	public void setTypeName(String typeName) {
		fTypeName = typeName;
	}

	@Override
	public int compareTo(DetailFormatter another) {
		if (fTypeName == null) {
			if (another.fTypeName == null) {
				return 0;
			}
			return another.fTypeName.compareTo(fTypeName);
		}
		return fTypeName.compareTo(another.fTypeName);
	}

}
