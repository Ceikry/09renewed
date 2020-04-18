package com.runescape.packethandler.commands;

import java.io.FileNotFoundException;

import com.runescape.model.World;
import com.runescape.model.player.Player;

public class ReloadNPCDefs implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			try {
				World.getInstance().reloadNPCDefs();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
