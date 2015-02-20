package com.commandengine.stringparser;


import java.util.Set;

/**
 * Interface for parsing a Sentance to String tokens
 */
public interface TextParser {
	public Set<String> parseString(String command);
}
