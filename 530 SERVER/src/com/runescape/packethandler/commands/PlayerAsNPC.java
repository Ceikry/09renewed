package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class PlayerAsNPC implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		if (Integer.valueOf(cmd[1]) == -1) {
			player.getAppearance().setNpcId(-1);
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
		} else {
			player.getAppearance().setNpcId(Integer.valueOf(cmd[1]));
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
		}
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
