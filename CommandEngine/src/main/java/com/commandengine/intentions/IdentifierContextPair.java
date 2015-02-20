package com.commandengine.intentions;

import java.util.HashSet;
import java.util.Set;

/**
 * User: pgopal
 * Date: 20/02/15
 * Time: 00:19
 */
public class IdentifierContextPair {
	private Set<String> identifiers;
	private Set<String> context;

	public IdentifierContextPair() {
		identifiers = new HashSet<String>();
		context = new HashSet<String>();
	}


	public IdentifierContextPair(Set<String> identifiers, Set<String> context) {
		this.identifiers = identifiers;
		this.context = context;
	}

	public Set<String> getIdentifiers() {
		return identifiers;
	}

	public Set<String> getContext() {
		return context;
	}
}
