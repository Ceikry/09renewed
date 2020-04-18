package com.runescape.packethandler.commands;

import com.runescape.content.CharacterDesign;
import com.runescape.model.player.Player;

public class CharacterAppearance implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		CharacterDesign.open(player);
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
