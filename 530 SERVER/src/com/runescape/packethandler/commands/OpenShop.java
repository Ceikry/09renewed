package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;
import com.runescape.model.player.ShopSession;

public class OpenShop implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			String cmd[] = command.split(" ");
		player.setShopSession(new ShopSession(player, Integer.valueOf(cmd[1])));
	}
	}

	@Override
	public int minimumRightsNeeded() {
		return 1;
	}

}
