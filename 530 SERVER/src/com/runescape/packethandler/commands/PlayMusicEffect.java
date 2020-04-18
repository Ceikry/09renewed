package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class PlayMusicEffect implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		player.getActionSender().sendMusicEffect(Integer.valueOf(cmd[1]));
		player.getActionSender().sendMessage("Playing music effect " + cmd[1]);
		}
		
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}
	
}
