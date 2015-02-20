package com.commandengine;

import com.commandengine.intentions.dao.IntentionsDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * User: pgopal
 * Date: 20/02/15
 * Time: 08:18
 */
@Configuration
public class CommandEngineTestContext {

	@Bean
	public IntentionsDao getIntentionsDao() {
		return mock(IntentionsDao.class);
	}
}
