package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class Interface implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		player.getActionSender().displayInterface(Integer.parseInt(cmd[1]));
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
