package com.runescape.content.skills.magic;

import com.runescape.event.Event;
import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.player.Player;
import com.runescape.util.Area;
import com.runescape.util.Misc;

public class Teleport extends MagicData {

	public Teleport() {
		
	}
	
	public static void homeTeleport(final Player p) {
		if (p.getTemporaryAttribute("teleporting") != null || p.getTemporaryAttribute("homeTeleporting") != null || p.getTemporaryAttribute("unmovable") != null || p.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		
		p.getActionSender().closeInterfaces();
		p.setTemporaryAttribute("teleporting", true);
		p.setTemporaryAttribute("homeTeleporting", true);
		p.getWalkingQueue().reset();
		p.getActionSender().clearMapFlag();
		
		World.getInstance().registerEvent(new Event(500) {
			int currentStage = 0;
			@Override
			public void execute() {
				if (p.getTemporaryAttribute("homeTeleporting") == null) {
					p.animate(65535, 0);
					p.graphics(65535, 0);
					resetTeleport(p);
					this.stop();
					return;
				}
				if (this.currentStage++ >= 16) {
					resetTeleport(p);
					p.teleport(Location.location(HOME_TELE[0] + Misc.random(HOME_TELE[2]), HOME_TELE[1] + Misc.random(HOME_TELE[3]), 0));
					this.stop();
					return;
				}
				p.animate(HOME_ANIMATIONS[currentStage], 0);
				p.graphics(HOME_GRAPHICS[currentStage], 0);
			}
		});
	}
	
	public static void teleport(final Player p, final int teleport) {
		if (!canTeleport(p, teleport)) {
			//return;
		}
		if (!RuneManager.deleteRunes(p, TELEPORT_RUNES[teleport], TELEPORT_RUNES_AMOUNT[teleport])) {
			return;
		}
		p.removeTemporaryAttribute("lootedBarrowChest"); // so it resets instantly.
		p.removeTemporaryAttribute("autoCasting");
		p.setTarget(null);
		final boolean ancients = teleport > 6 ? true : false;
		int playerMagicSet = p.getSettings().getMagicType();
		boolean correctMagicSet = (!ancients && playerMagicSet == 1) || (ancients && playerMagicSet == 2);
		if (!correctMagicSet) {
			return;
		}
		final int x = TELE_X[teleport] + Misc.random(TELE_EXTRA_X[teleport]);
		final int y = TELE_Y[teleport] + Misc.random(TELE_EXTRA_Y[teleport]);
		p.getActionSender().closeInterfaces();
		p.animate(ancients ? 9599 : 8939, 0);
		p.graphics(ancients ? 1681 : 1576, 0);
		p.getActionSender().sendBlankClientScript(1297);
		p.getWalkingQueue().reset();
		p.getActionSender().clearMapFlag();
		p.setTemporaryAttribute("teleporting", true);
		
		World.getInstance().registerEvent(new Event(ancients ? 2750 : 1800) {
			@Override
			public void execute() {
				p.teleport(Location.location(x, y, 0));
				if (!ancients) {
					p.animate(8941, 0);
					p.graphics(1577, 0);
				}
				World.getInstance().registerEvent(new Event(ancients ? 500 : 2000) {
					@Override
					public void execute() {
						p.getLevels().addXp(MAGIC, TELEPORT_XP[teleport]);
						resetTeleport(p);
						this.stop();
					}
				});
				this.stop();
			}
		});
	}

	private static boolean canTeleport(Player p, int teleport) {
		if (p.getTemporaryAttribute("teleporting") != null) {
			return false;
		}
		
		if (p.getTemporaryAttribute("teleblocked") != null) {
			p.getActionSender().sendMessage("A magical force prevents you from teleporting!");
			return false;
		}
		if (p.getTemporaryAttribute("unmovable") != null || p.getTemporaryAttribute("cantDoAnything") != null) {
			return false;
		}
		if (p.getLevels().getLevel(MAGIC) < TELEPORT_LVL[teleport]) {
			p.getActionSender().sendMessage("You need a Magic level of " + TELEPORT_LVL[teleport] + " to use this teleport!");
			return false;
		}
		if (!RuneManager.hasRunes(p, TELEPORT_RUNES[teleport], TELEPORT_RUNES_AMOUNT[teleport])) {
			p.getActionSender().sendMessage("You do not have enough runes to cast this teleport.");
			return false;
		}
		if (Area.inWilderness(p.getLocation()) && p.getLastWildLevel() >= 20) {
			p.getActionSender().sendMessage("You cannot teleport above level 20 wilderness!");
			return false;
		}
		if (p.isDead()){
			return false;
		}
		return true;
	}
	
	public static boolean useTeletab(final Player p, int item, int slot) {
		int index = -1;
		for (int i = 0; i < TELETABS.length; i++) {
			if (item == TELETABS[i]) {
				index = i;
			}
		}
		if (index == -1) {
			return false;
		}
		if (p.getTemporaryAttribute("teleporting") != null || p.getTemporaryAttribute("homeTeleporting") != null || p.getTemporaryAttribute("unmovable") != null || p.getTemporaryAttribute("cantDoAnything") != null) {
			return false;
		}
		if (p.getTemporaryAttribute("teleblocked") != null) {
			p.getActionSender().sendMessage("A magical force prevents you from teleporting!");
			return false;
		}
		
		if (Area.inWilderness(p.getLocation()) && p.getLastWildLevel() >= 20) {
			p.getActionSender().sendMessage("You cannot teleport above level 20 wilderness!");
			return false;
		}
		
		final int x = TELE_X[index] + Misc.random(TELE_EXTRA_X[index]);
		final int y = TELE_Y[index] + Misc.random(TELE_EXTRA_Y[index]);
		p.getActionSender().closeInterfaces();
		p.getActionSender().sendBlankClientScript(1297);
		p.getWalkingQueue().reset();
		p.getActionSender().clearMapFlag();
		
		if (p.getInventory().deleteItem(item, slot, 1)) {
			p.setTemporaryAttribute("unmovable", true);
			p.setTemporaryAttribute("teleporting", true);
			p.animate(9597);
			p.graphics(1680,0,0);
			//p.graphics(678, 0, 0); // blue gfx
			World.getInstance().registerEvent(new Event(900) {
				int i = 0;
				@Override
				public void execute() {
					if (i == 0) {
						p.animate(4071);
						i++;
					} else {
						p.animate(65535);
						p.removeTemporaryAttribute("unmovable");
						p.teleport(Location.location(x, y, 0));
						resetTeleport(p);
						this.stop();
					}
				}
			});
			return true;
		}
		return true;
	}
	
	public static void resetTeleport(Player p) {
		p.removeTemporaryAttribute("teleporting");
		p.removeTemporaryAttribute("homeTeleporting");
	}
}
