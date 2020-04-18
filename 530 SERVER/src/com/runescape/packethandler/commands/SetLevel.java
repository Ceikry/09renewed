package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

public class SetLevel implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		if (cmd[1] == null) {
			player.getActionSender().sendMessage("You did not specify a skill ID.");
			return;
		}
		try {
			player.getLevels().setLevel(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
			player.getLevels().setXp(Integer.valueOf(cmd[1]), player.getLevels().getXpForLevel(Integer.valueOf(cmd[2])));
		} catch (Exception e) {
		}
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
		player.getActionSender().sendSkillLevel(Integer.valueOf(cmd[1]));
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
