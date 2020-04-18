package com.runescape.packethandler.commands;

import com.runescape.GameEngine;
import com.runescape.model.player.Player;

public class ShowClips implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			GameEngine.noClipHandler.addItemOnPos();
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
