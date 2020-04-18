package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class TestConfig implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			String cmd[] = command.split(" ");
			player.getActionSender().sendConfig(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
