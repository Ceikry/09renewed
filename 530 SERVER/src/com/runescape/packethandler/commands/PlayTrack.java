package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class PlayTrack implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		//Music.playRegionTrack(player);
		player.getActionSender().sendMusic(Integer.valueOf(cmd[1]), "Playing Song: " + Integer.valueOf(cmd[1]));
		/*player.getActionSender().sendMessage("Unlocked 89 music tracks.");
		player.getActionSender().sendConfig(20, 20);
		player.getActionSender().sendConfig(21, 21);
		player.getActionSender().sendConfig(22, 22);
		player.getActionSender().sendConfig(23, 23);
		player.getActionSender().sendConfig(24, 24);
		player.getActionSender().sendConfig(25, 25);
		player.getActionSender().sendConfig(298, 298);
		player.getActionSender().sendConfig(311, 311);
		player.getActionSender().sendConfig(346, 346);
		player.getActionSender().sendConfig(414, 414);
		player.getActionSender().sendConfig(464, 464);
		player.getActionSender().sendConfig(598, 598);*/
		//player.getActionSender().sendMusic(76);

		}
		
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}
	
}
