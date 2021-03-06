package com.runescape.packethandler.commands;

import com.runescape.model.Location;
import com.runescape.model.player.Player;

public class MoveDown implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		int playerX = player.getLocation().getX();
		int playerY = player.getLocation().getY();
		int playerZ = player.getLocation().getZ();
		   player.teleport(Location.location(playerX, playerY - 1, playerZ));
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
