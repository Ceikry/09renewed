package com.runescape.packethandler.commands;

import com.runescape.model.World;
import com.runescape.model.player.Player;

public class KickAll implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		for (Player p : World.getInstance().getPlayerList()) {
			if (p != null) {
				p.getActionSender().forceLogout();
			}
		}
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
