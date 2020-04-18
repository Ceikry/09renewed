package com.runescape.packethandler.commands;

import com.runescape.model.World;
import com.runescape.model.player.Player;

public class Yell implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded()) {
		for (Player p : World.getInstance().getPlayerList()) {
			if (p != null) {
				if (player.getRights() == 0) {
					p.getActionSender().sendMessage("[<col=0000FF>Player</col>]" + player.getPlayerDetails().getDisplayName() + ": " + command.substring(5));
				}
				if (player.getRights() == 1) {
					p.getActionSender().sendMessage("[<col=FF0000>Mod</col>]" + player.getPlayerDetails().getDisplayName() + ": " + command.substring(5));
				}
				if (player.getRights() == 2) {
					p.getActionSender().sendMessage("[<col=008000>Admin</col>]" + player.getPlayerDetails().getDisplayName() + ": " + command.substring(5));
				}
			}
		}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 0;
	}

}
