package com.commandengine.command;

import org.springframework.stereotype.Component;

/**
 * User: pgopal
 * Date: 19/02/15
 * Time: 23:28
 */
@Component
public class ShowStatusCommand implements CommandExecuter<String, String> {

	public String execute(String input) {
		return "ShowStatusCommand executed on " + input;

	}

	public Commands type() {
		return Commands.SHOWSTATUS;
	}
}
