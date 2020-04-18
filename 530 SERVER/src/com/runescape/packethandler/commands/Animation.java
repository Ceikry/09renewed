package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

/**
 * 
 * Perform animation command.
 * @author Luke132
 */
public class Animation implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		player.animate(Integer.valueOf(cmd[1]));
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
