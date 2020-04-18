package com.runescape.util;

import com.runescape.event.AreaEvent;
import com.runescape.event.Event;
import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.masks.ForceMovement;
import com.runescape.model.player.Player;
import com.runescape.world.ObjectLocations.Region;

public class Area {

	//TODO change all this to polygons or some shit so we dont just have to use squares
	
	public Area() {
		
	}
	
	public static boolean inWilderness(Location l) {
		return l.inArea(2945, 3524, 3391, 3975);
	}

	

	public static void crossDitch(final Player p, final int x, final int y) {
		if (p.getTemporaryAttribute("unmovable") != null) {
			return;
		}
		World.getInstance().registerCoordinateEvent(new AreaEvent(p, x, y - 1, x, y + 2) {

			@Override
			public void run() {
				p.getActionSender().closeInterfaces();
				p.getWalkingQueue().reset();
				p.setTemporaryAttribute("unmovable", true);
				final int newY = p.getLocation().getY() >= 3523 ? p.getLocation().getY()-3 : p.getLocation().getY()+3;
				final int dir = newY == 3 ? 0 : 4;
				Location faceLocation = Location.location(p.getLocation().getX(), dir == 3 ? 3523 : 3520, 0);
				p.setFaceLocation(faceLocation);
				World.getInstance().registerEvent(new Event(500) {
					@Override
					public void execute() {
						this.stop();
						p.animate(6132);
						int regionX = p.getUpdateFlags().getLastRegion().getRegionX();
						int regionY = p.getUpdateFlags().getLastRegion().getRegionY();
						int lX = (p.getLocation().getX() - ((regionX - 6) * 8));
						int lY = (p.getLocation().getY() - ((regionY - 6) * 8));
						ForceMovement movement = new ForceMovement(lX, lY, lX, newY, 33, 60, dir);
						p.setForceMovement(movement);		
						p.setFaceLocation(Location.location(x, y, 0));
						World.getInstance().registerEvent(new Event(1250) {

							@Override
							public void execute() {
								this.stop();
								int playerY = p.getLocation().getY();
								int nY = playerY >= 3523 ? 3520 : 3523;
								p.teleport(Location.location(p.getLocation().getX(), nY, 0));
								p.removeTemporaryAttribute("unmovable");
							}	
						});
					}
				});
			}
		});
	}
}
