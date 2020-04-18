package com.runescape.packethandler.commands;

import com.runescape.model.World;
import com.runescape.model.player.Player;

public class Kick implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		cmd[1].toLowerCase();
		for (Player p : World.getInstance().getPlayerList()) {
			if (p != null) {
				if (p.getUsername().toLowerCase().equalsIgnoreCase(cmd[1])) {
					p.getActionSender().logout();
				}
			}
		}
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
