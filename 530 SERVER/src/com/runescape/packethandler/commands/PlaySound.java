package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class PlaySound implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		player.getActionSender().playSound(player, Integer.parseInt(cmd[1]), 1, 0);
		player.getActionSender().sendMessage("Playing sound effect " + cmd[1]);
		}
		
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}
	
}
