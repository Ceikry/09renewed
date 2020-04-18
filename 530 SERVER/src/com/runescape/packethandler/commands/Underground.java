package com.runescape.packethandler.commands;

import com.runescape.model.Location;
import com.runescape.model.player.Player;

public class Underground implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			player.teleport(Location.location(player.getLocation().getX(), player.getLocation().getY() + 6400, 0));
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
