package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class MaxHit implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		System.out.println("Maxhit = " + player.getMaxHit());
		player.getActionSender().sendMessage("Maxhit = " + player.getMaxHit());
	}
	}

	@Override
	public int minimumRightsNeeded() {
		// TODO Auto-generated method stub
		return 1;
	}

}
