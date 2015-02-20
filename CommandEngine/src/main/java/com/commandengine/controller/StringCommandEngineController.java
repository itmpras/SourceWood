package com.commandengine.controller;

import com.commandengine.CommandEngineContext;
import com.commandengine.command.Commands;
import com.commandengine.command.CommandExecuter;
import com.commandengine.intentions.Intention;
import com.commandengine.stringparser.TextParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 23:45
 */
@Component
public class StringCommandEngineController implements CommandEngineController<String, String> {

	private Map<Commands, CommandExecuter<String, String>> commandExecuterMap;

	@Autowired
	List<Intention> intentions;

	@Autowired
	TextParser textParser;

	@Resource(name = CommandEngineContext.COMMAND_EXECUTOR_MAP)
	@Required
	public void setCommandExecuterMap(Map<Commands, CommandExecuter<String, String>> commandExecuterMap) {
		this.commandExecuterMap = commandExecuterMap;
	}

	public List<CommandExecuter<String, String>> getCommandExecuter(String input) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
