package com.commandengine.controller;

import com.commandengine.command.CommandExecuter;

import java.util.List;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 23:36
 */
public interface CommandEngineController<U, T> {

	public List<CommandExecuter<U, T>> getCommandExecuter(U input);

	public List<CommandExecuter<U, T>> getCommandExecuterWithMatchingContext(U input);
}
