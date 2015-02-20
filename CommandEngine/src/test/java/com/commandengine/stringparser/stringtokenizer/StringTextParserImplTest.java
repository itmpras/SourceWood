package com.commandengine.stringparser.stringtokenizer;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 22:23
 */
public class StringTextParserImplTest {
	StringTextParserImpl stringTextParser;

	@Before
	public void setUp() throws Exception {
		stringTextParser = new StringTextParserImpl();
	}

	@Test
	public void testParsingGivenCommand() throws Exception {
		Set<String> commands = stringTextParser.parseString("Show me my Status");
		assertThat(commands, containsInAnyOrder("show", "me", "my", "status"));
	}

	@Test
	public void testParsingWithDuplicateWords() throws Exception {
		Set<String> strings = stringTextParser.parseString("Show Show Show");
		assertThat(strings, hasSize(1));
		assertThat(strings, contains("show"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPassingNullString() throws Exception {
		stringTextParser.parseString(null);
	}

	@After
	public void tearDown() throws Exception {

	}
}
