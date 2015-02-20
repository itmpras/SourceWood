package com.commandengine.stringparser.stringtokenizer;

import com.commandengine.stringparser.TextParser;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.Set;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 22:22
 */
@Component
public class StringTextParserImpl implements TextParser {

	/**
	 * Function to parse given command to String tokens
	 *
	 * @param command
	 * @return
	 */
	public Set<String> parseString(String command) {
		if (Strings.isNullOrEmpty(command)) {
			throw new IllegalArgumentException("Expecting a valid command");
		}
		ImmutableSet.Builder<String> builder = ImmutableSet.builder();

		String[] split = command.split("\\s");

		if (split.length > 0) {
			for (String s : split) {
				builder.add(s.toLowerCase());
			}
		}

		return builder.build();
	}
}
