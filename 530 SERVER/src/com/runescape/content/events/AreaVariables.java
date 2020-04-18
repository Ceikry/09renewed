package com.runescape.content.events;

import com.runescape.event.Event;
import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.player.Player;
import com.runescape.util.Area;

public class AreaVariables extends Event {

	public AreaVariables() {
		super(500);
	}

	@Override
	public void execute() {
		for (Player p : World.getInstance().getPlayerList()) {
			if (p != null) {
				updateVariables(p);
			}
		}
	}
	
	/*
	 * NOTE: Anything that goes in here and varies between HD and LD, 
	 * reset the variable in ActionSender.configureGameScreen
	 */
	public void updateVariables(Player p) {
		int currentLevel = wildernessLevel(p.getLocation());
		if (currentLevel != p.getLastWildLevel()) {
			if (currentLevel > 0) {
				p.setLastwildLevel(currentLevel);
				if (p.getTemporaryAttribute("inWild") == null) {
					p.getActionSender().sendPlayerOption("Attack", 1, 1);
					p.getActionSender().sendOverlay(381);
					p.setTemporaryAttribute("inWild", true);
				}
			} else {
				if (p.getTemporaryAttribute("inWild") != null) {
					p.getActionSender().sendPlayerOption("null", 1, 1);
					p.getActionSender().sendRemoveOverlay();
					p.setLastwildLevel(0);
					p.removeTemporaryAttribute("inWild");
				}
			}
		}
		
			
		
		
	}

	public int wildernessLevel(Location l) {
		int y = l.getY();
		if (!Area.inWilderness(l)) {
			return -1;
		}
		if(y > 3523 && y < 4000) {
			return (((int)(Math.ceil((double)(y)-3520D) / 8D) + 1));
		}
		return -1;
	}

}
