package com.commandengine.controller;

import com.commandengine.CommandEngineContext;
import com.commandengine.CommandEngineTestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	public void testController() throws Exception {

	}
}
