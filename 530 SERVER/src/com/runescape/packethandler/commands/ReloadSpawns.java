package com.runescape.packethandler.commands;

import java.io.FileNotFoundException;

import com.runescape.model.World;
import com.runescape.model.player.Player;

public class ReloadSpawns implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			try {
				World.getInstance().reloadSpawns(player);
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
