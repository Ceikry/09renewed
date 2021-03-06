package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;
import com.runescape.packetbuilder.PlayerUpdate;

public class Female implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		if (player.getAppearance().getGender() == 0) {
			player.getActionSender().sendMessage("Changing to female...");
			player.getAppearance().setGender(1);
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
		}
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}