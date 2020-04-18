package com.runescape.packethandler.commands;

import com.runescape.model.World;
import com.runescape.model.player.Player;

public class Players implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded()) {
		player.getActionSender().sendMessage("Players online : " + World.getInstance().getPlayerList().size());
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 0;
	}

}
