package com.runescape.content.events;

import com.runescape.event.Event;
import com.runescape.model.World;
import com.runescape.model.player.Player;

public class SystemUpdateEvent extends Event {

	private int time = 180;
	
	public SystemUpdateEvent() {
		super(1000);
	}

	@Override
	public void execute() {
		for(Player p : World.getInstance().getPlayerList()) {
			if (p != null) {
				p.getActionSender().newSystemUpdate(time);
			}
		}
		if (time-- <= 0) {
			this.stop();
			for(Player p : World.getInstance().getPlayerList()) {
				p.getActionSender().forceLogout();
			}
		}
	}
}
