package com.runescape.packethandler.commands;

import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.player.Player;

public class TeleToPlayer implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			String cmd[] = command.split(" ");
			Player p2 = World.getInstance().getPlayerForName(cmd[1]);
			if (p2 != null) {
				player.teleport(Location.location(p2.getLocation().getX(), p2.getLocation().getY(), p2.getLocation().getZ()));
				player.getActionSender().sendMessage("You Teleport to " + p2.getUsername());
			}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
