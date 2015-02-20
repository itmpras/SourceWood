package com.commandengine.intentions.dao;

import com.commandengine.intentions.IdentifierContextPair;
import com.commandengine.intentions.Intention;
import com.commandengine.intentions.Intentions;

/**
 * User: pgopal
 * Date: 20/02/15
 * Time: 00:22
 */
public interface IntentionsDao {

	IdentifierContextPair getIdentifierContextPairFor(Intentions intentions);
}
