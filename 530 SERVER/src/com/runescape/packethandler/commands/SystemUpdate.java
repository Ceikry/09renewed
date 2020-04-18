package com.runescape.packethandler.commands;

import com.runescape.content.events.SystemUpdateEvent;
import com.runescape.model.World;
import com.runescape.model.player.Player;

public class SystemUpdate implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		if (!World.getInstance().isUpdateInProgress()) {
			World.getInstance().registerEvent(new SystemUpdateEvent());
			World.getInstance().setUpdateInProgress(true);
		}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
