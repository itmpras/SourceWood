package com.commandengine;

import com.commandengine.intentions.IdentifierContextPair;
import com.commandengine.intentions.Intentions;
import com.commandengine.intentions.dao.IntentionsDao;
import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: pgopal
 * Date: 20/02/15
 * Time: 08:18
 */
@Configuration
public class CommandEngineTestContext {

	@Bean
	public IntentionsDao getIntentionsDao() {
		IntentionsDao mock = mock(IntentionsDao.class);
		when(mock.getIdentifierContextPairFor(Intentions.VIEWSTATUS)).thenReturn(getShowStatusIdentifierPair());
		when(mock.getIdentifierContextPairFor(Intentions.VIEWPRICE)).thenReturn(getShowPriceIdentifierPair());

		return mock;
	}

	public IdentifierContextPair getShowStatusIdentifierPair() {
		ImmutableSet.Builder<String> identifierBuilder = ImmutableSet.builder();
		identifierBuilder.add("show");
		identifierBuilder.add("list");
		identifierBuilder.add("give");
		identifierBuilder.add("update");

		ImmutableSet.Builder<String> context = ImmutableSet.builder();
		context.add("status");

		return new IdentifierContextPair(identifierBuilder.build(), context.build());
	}

	public IdentifierContextPair getShowPriceIdentifierPair() {
		ImmutableSet.Builder<String> identifierBuilder = ImmutableSet.builder();
		identifierBuilder.add("show");
		identifierBuilder.add("list");
		identifierBuilder.add("give");
		identifierBuilder.add("update");

		ImmutableSet.Builder<String> context = ImmutableSet.builder();
		context.add("price");

		return new IdentifierContextPair(identifierBuilder.build(), context.build());
	}
}
