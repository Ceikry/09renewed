package com.runescape.packethandler.commands;

import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.player.Player;

public class TeleToMe implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			String cmd[] = command.split(" ");
			Player p2 = World.getInstance().getPlayerForName(cmd[1]);
			if (p2 != null) {
				p2.teleport(Location.location(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
				p2.getActionSender().sendMessage("You have been teleported by " + player.getUsername());
			}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
