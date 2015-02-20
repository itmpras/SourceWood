package com.commandengine.intentions;

import com.commandengine.command.Commands;
import com.commandengine.intentions.dao.IntentionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 23:56
 */
@Component
public class ShowStatusIntention implements Intention {

	private IdentifierContextPair pair;
	@Autowired
	private IntentionsDao intentionsDao;

	@PostConstruct
	public void init() {
		pair = intentionsDao.getIdentifierContextPairFor(Intentions.VIEWSTATUS);
	}


	public Set<String> intentionIdentifiers() {
		return pair.getIdentifiers();  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Set<String> intentionContext() {
		return pair.getContext();  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Commands getIntendedCommand() {
		return Commands.SHOWSTATUS;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Intentions getIntention() {
		return Intentions.VIEWSTATUS;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
