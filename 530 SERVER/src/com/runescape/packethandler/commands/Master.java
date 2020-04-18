package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;
import com.runescape.model.player.Skills;
import com.runescape.util.log.Logger;

public class Master implements Command {

	@Override
	public void execute(Player player, String command) {
		String[] cmd = command.split(" ");
		cmd[0] = cmd[0].toLowerCase();
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
            if (cmd.length == 1) {
                for (int i = 0; i < Skills.SKILL_COUNT; i++) {
                    player.getLevels().addXp(i, 14000000);
                }
            } else if (cmd.length == 2) {
                int skill = Integer.valueOf(cmd[1]);
                player.getLevels().addXp(skill, 14000000);
            } else {
                player.getActionSender().sendMessage("Syntax is ::master [id=all].");
            }
		} 
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
