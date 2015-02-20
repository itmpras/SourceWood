package com.commandengine.command;


import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class ShowPriceCommand implements CommandExecuter<String, String> {

	public String execute(String input) {
		return "ShowPriceCommand  executed for :" + input;  //To change body of implemented methods use File | Settings |

	}

	public Commands type() {
		return Commands.SHOWPRICE;
	}
}

