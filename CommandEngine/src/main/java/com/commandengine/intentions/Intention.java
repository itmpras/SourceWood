package com.commandengine.intentions;

import com.commandengine.command.Commands;

import java.util.Set;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 23:53
 */
public interface Intention {

	public Set<String> intentionIdentifiers();

	public Set<String> intentionContext();

	public Commands getIntendedCommand();

	public Intentions getIntention();
}
