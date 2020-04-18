package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

/**
 * 
 * Display graphic command
 * @author Luke132
 */
public class Graphic implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		player.graphics(Integer.valueOf(cmd[1]), 0, 100);
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}
}
