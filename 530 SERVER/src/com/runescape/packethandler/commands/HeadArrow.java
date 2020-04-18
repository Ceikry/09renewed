package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class HeadArrow implements Command {
	
	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			//LearningTheRopes.start(player);
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}


}
