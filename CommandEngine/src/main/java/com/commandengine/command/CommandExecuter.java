package com.commandengine.command;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 23:22
 */
public interface CommandExecuter<U, T> {

	public T execute(U input);

	public Commands type();
}

