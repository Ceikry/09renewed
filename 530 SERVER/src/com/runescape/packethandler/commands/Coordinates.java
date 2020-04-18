package com.runescape.packethandler.commands;

import com.runescape.model.Location;
import com.runescape.model.player.Player;

public class Coordinates implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded()) {
		Location loc = player.getLocation();
		player.getActionSender().sendMessage("X = " + loc.getX() + " Y = " + loc.getY());
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 0;
	}

}
