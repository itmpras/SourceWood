package com.commandengine.controller;

import com.commandengine.CommandEngineContext;
import com.commandengine.CommandEngineTestContext;
import com.commandengine.command.CommandExecuter;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 * User: pgopal
 * Date: 20/02/15
 * Time: 08:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommandEngineContext.class, CommandEngineTestContext.class})
public class StringCommandEngineControllerTest {

	@Autowired
	private StringCommandEngineController controller;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testControllerToFindShowCommand() throws Exception {

		List<CommandExecuter<String, String>> commandExecuter = controller.getCommandExecuter("Show price");
		assertThat(commandExecuter, IsNull.notNullValue());
		assertThat(commandExecuter, hasSize(1));

	}

	@Test
	public void testControllerShouldNotReturnAnyCommand() throws Exception {

		List<CommandExecuter<String, String>> commandExecuter = controller.getCommandExecuter("Blah");
		assertThat(commandExecuter, IsNull.notNullValue());
		assertThat(commandExecuter, hasSize(0));

	}
}
