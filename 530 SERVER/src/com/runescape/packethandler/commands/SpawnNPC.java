package com.runescape.packethandler.commands;

import com.runescape.model.World;
import com.runescape.model.npc.NPC;
import com.runescape.model.player.Player;

public class SpawnNPC implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		int id = Integer.valueOf(cmd[1]);
		NPC npc = new NPC(id);
		npc.setLocation(player.getLocation());
		npc.readResolve();
		World.getInstance().getNpcList().add(npc);
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
