package com.commandengine.controller;

import com.commandengine.CommandEngineContext;
import com.commandengine.command.Commands;
import com.commandengine.command.CommandExecuter;
import com.commandengine.intentions.Intention;
import com.commandengine.intentions.Intentions;
import com.commandengine.stringparser.TextParser;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 23:45
 */
@Component
public class StringCommandEngineController implements CommandEngineController<String, String> {

	private Map<Commands, CommandExecuter<String, String>> commandExecuterMap;
	Logger logger = LoggerFactory.getLogger(StringCommandEngineController.class);


	@Autowired
	List<Intention> intentions;

	@Autowired
	TextParser textParser;

	@Resource(name = CommandEngineContext.COMMAND_EXECUTOR_MAP)
	@Required
	public void setCommandExecuterMap(Map<Commands, CommandExecuter<String, String>> commandExecuterMap) {
		this.commandExecuterMap = commandExecuterMap;
	}

	public List<CommandExecuter<String, String>> getCommandExecuterWithMatchingContext(String input) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public List<CommandExecuter<String, String>> getCommandExecuter(String input) {
		logger.info("Fetching command executer for input {} ", input);
		Map<Intentions, CommandExecuter<String, String>> intentionForInput = new HashMap<Intentions,
				CommandExecuter<String, String>>();
		ImmutableList.Builder<CommandExecuter<String, String>> listBuilder = ImmutableList.builder();

		Set<String> strings = textParser.parseString(input);

		if (strings == null) {
			throw new IllegalArgumentException("No valid tokens found");
		}


		for (String token : strings) {
			Map<Intentions, CommandExecuter<String, String>> intentionForWord = getIntentionForWord(token);
			intentionForInput.putAll(intentionForWord);
		}


		logger.info("Command Executer Size  : ", intentionForInput.size());
		Set<Intentions> keys = intentionForInput.keySet();

		for (Intentions key : keys) {
			listBuilder.add(intentionForInput.get(key));
		}

		return listBuilder.build();
	}

	private Map<Intentions, CommandExecuter<String, String>> getIntentionForWord(String token) {
		Map<Intentions, CommandExecuter<String, String>> result = new HashMap<Intentions, CommandExecuter<String,
				String>>();
		for (Intention intention : this.intentions) {

			Set<String> strings = intention.intentionIdentifiers();
			Set<String> context = intention.intentionContext();

			if (strings.contains(token) || context.contains(token)) {
				Intentions intentions = intention.getIntention();
				if (!result.containsKey(intentions)) {
					Commands intendedCommand = intention.getIntendedCommand();
					if (!commandExecuterMap.containsKey(intendedCommand)) {
						throw new IllegalStateException("Unable to find command execute for {}" + intendedCommand);
					}
					result.put(intention.getIntention(), commandExecuterMap.get(intendedCommand));
				}
			}

		}
		return result;
	}


}
