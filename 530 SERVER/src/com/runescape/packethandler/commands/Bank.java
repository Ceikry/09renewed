package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class Bank implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			if (!player.inCombat()) {
				player.getBank().openBank();
			}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
