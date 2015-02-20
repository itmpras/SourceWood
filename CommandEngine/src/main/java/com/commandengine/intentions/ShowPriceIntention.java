package com.commandengine.intentions;

import com.commandengine.command.Commands;
import com.commandengine.intentions.dao.IntentionsDao;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * User: pgopal
 * Date: 20/02/15
 * Time: 00:05
 */
@Component
public class ShowPriceIntention implements Intention {
	private IdentifierContextPair pair;
	private IntentionsDao intentionsDao;

	@PostConstruct
	public void init() {
		pair = intentionsDao.getIdentifierContextPairFor(Intentions.VIEWPRICE);
	}


	public Set<String> intentionIdentifiers() {
		return pair.getIdentifiers();  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Set<String> intentionContext() {
		return pair.getContext();  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Commands getIntendedCommand() {
		return Commands.SHOWPRICE;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Intentions getIntention() {
		return Intentions.VIEWPRICE;
	}
}

