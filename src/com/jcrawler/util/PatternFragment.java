package com.jcrawler.util;

import java.util.regex.Pattern;

public class PatternFragment {

	/** regex flags */
	private static final int flags = Pattern.CASE_INSENSITIVE | Pattern.DOTALL |
        Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ;

	/** URL patern */
	private String pattern;
	/** URL patern, compiled*/
	private Pattern patternCompiled;
	/** If true => URL allowed. Otherwise, denied. */
	private boolean permission;

	public PatternFragment() {
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		// and compile it now for better performance
		this.patternCompiled = Pattern.compile(pattern, this.flags);
	}

	public String getPattern() {
		return this.pattern;
	}

	public Pattern getPatternCompiled() {
		return this.patternCompiled;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	/**
	 * @return true if URL allowed
	 */
	public boolean getPermission() {
		return this.permission;
	}

}