package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class ChatHeads implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			String cmd[] = command.split(" ");
			if (cmd[1] != null) {
				player.getActionSender().sendPlayerChat1("Testing chat head " + cmd[1], Integer.parseInt(cmd[1]));
			}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}