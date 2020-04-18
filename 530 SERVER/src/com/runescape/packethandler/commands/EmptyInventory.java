package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class EmptyInventory implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		player.getInventory().deleteAll();
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
