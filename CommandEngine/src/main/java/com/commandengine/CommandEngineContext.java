package com.commandengine;

import com.commandengine.command.CommandExecuter;
import com.commandengine.command.Commands;
import com.commandengine.intentions.Intention;
import com.commandengine.stringparser.TextParser;
import com.commandengine.stringparser.stringtokenizer.StringTextParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pgopal
 * Date: 20/02/15
 * Time: 00:09
 */
@Configuration
@ComponentScan(basePackageClasses = {CommandEngineContext.class})
public class CommandEngineContext {

	public static final String COMMAND_EXECUTOR_MAP = "commandExecutorMap";
	@Autowired
	List<CommandExecuter<String, String>> commandExecuters;

	@Autowired
	List<Intention> intentions;

	private Map<Commands, CommandExecuter<String, String>> commandExecuterMap;

	@PostConstruct
	private void init() {
		commandExecuterMap = new HashMap<Commands, CommandExecuter<String, String>>();
		for (CommandExecuter<String, String> executer : commandExecuters) {
			commandExecuterMap.put(executer.type(), executer);
		}
	}

	@Bean(name = COMMAND_EXECUTOR_MAP)
	public Map<Commands, CommandExecuter<String, String>> commandExecuterMap() {
		return commandExecuterMap;
	}
}
