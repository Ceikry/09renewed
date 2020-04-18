package com.runescape.model;

import com.runescape.model.player.PathFinder;
import com.runescape.model.player.Player;
import com.runescape.world.clip.region.Region;

/**
 * Represents a location in the world.
 * 
 * Immutable.
 * @author Graham
 *
 */
public class Location implements Cloneable {

    public Location(){}
	
	private int x, y, z;
	
	public Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getLocalX() {
		return x - (8 * (getRegionX() - 6));
	}
	
	public int getLocalY() {
		return y - (8 * (getRegionY() - 6));
	}
	
	public int getLocalX(Location loc) {
		return x - (8 * (loc.getRegionX() - 6));
	}
	
	public int getLocalY(Location loc) {
		return y - (8 * (loc.getRegionY() - 6));
	}
	
	public int getRegionX() {
		return getParticleX();
	}
	
	public int getRegionY() {
		return getParticleY();
	}
	
	public int getRegionLocalX() {
		return getX() - 8 * (getRegionX() - 6);
	}

	public int getRegionLocalY() {
		return getY() - 8 * (getRegionY() - 6);
	}
	
	public int getParticleX() {
		return getX() >> 3;
	}

	public int getParticleY() {
		return getY() >> 3;
	}
	
	public int getParticleBaseX() {
		return getParticleX() << 3;
	}
	
	public int getParticleBaseY() {
		return getParticleY() << 3;
	}
	
	public int getParticleLocalX() {
		return getX() - getParticleBaseX();
	}
	
	public int getParticleLocalY() {
		return getY() - getParticleBaseY();
	}
	
	public int getRegionX2() {
		return getParticleX() / 8;
	}
	
	public int getRegionY2() {
		return getParticleY() / 8;
	}
	
	public int getRegionBaseX() {
		return (getRegionX() << 3) * 8;
	}
	
	public int getRegionBaseY() {
		return (getRegionY() << 3) * 8;
	}
	
	public int getRegionId() {
		return (getRegionX() << 8) + getRegionY();
	}

	
	public static Location location(int x, int y, int z) {
		return new Location(x, y, z);
	}
	
	public static Location location(Location l) {
		return new Location(l.getX(), l.getY(), l.getZ());
	}
	
	@Override
	public Location clone() {
		return new Location(x, y, z);
	}
	
	@Override
	public int hashCode() {
		return z << 30 | x << 15 | y;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Location)) {
			return false;
		}
		Location loc = (Location) other;
		return loc.x == x && loc.y == y && loc.z == z;
	}
	
	@Override
	public String toString() {
		return "["+x+","+y+","+z+"]";
	}
	
	public boolean inArea(int a, int b, int c, int d) {
		return x >= a && y >= b && x <= c && y <= d;
	}
	
	public boolean withinDistance(Location other, int dist) {
		if(other.z != z) {
			return false;
		}
		int deltaX = other.x - x, deltaY = other.y - y;
		return (deltaX <= (dist-1) && deltaX >= -dist && deltaY <= (dist-1) && deltaY >= -dist);
	}
	
	public boolean withinDistance(Location other) {
		if(other.z != z) {
			return false;
		}
		int deltaX = other.x - x, deltaY = other.y - y;
		return deltaX <= 14 && deltaX >= -15 && deltaY <= 14 && deltaY >= -15;
	}

	public boolean withinInteractionDistance(Location l) {
		return withinDistance(l, 3);
	}
	
	public boolean withinOpenDoorDistance(Location l) {
		return withinDistance(l, 2);
	}
	
	public int distanceToPoint(Location l) {
		return (int) Math.sqrt(Math.pow(x - l.getX(), 2) + Math.pow(y - l.getY(), 2));
	}
	
	public double getDistance(Location other) {
		int xdiff = this.getX() - other.getX();
		int ydiff = this.getY() - other.getY();
		return Math.sqrt(xdiff*xdiff + ydiff*ydiff);
	}
	
	public static int wildernessLevel(Location l) {
		int y = l.getY();
		if(y > 3520 && y < 4000) {
			return (((int)(Math.ceil((double)(y)-3520D)/8D)+1));
		}
		return 0;
	}
	
	public static boolean projectilePathBlocked(Entity attacker, Entity victim) {
		
		double offsetX = Math.abs(attacker.getLocation().getX() - victim.getLocation().getX());
		double offsetY = Math.abs(attacker.getLocation().getY() - victim.getLocation().getY());
		
		int distance = TileControl.calculateDistance(attacker, victim);
		
		if (distance == 0) {
			return true;
		}
		
		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];
		
		int curX = attacker.getLocation().getX();
		int curY = attacker.getLocation().getY();
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;
		
		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while(distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > victim.getLocation().getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;	
					currentTileXCount -= offsetX;
				}		
			} else if (curX < victim.getLocation().getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > victim.getLocation().getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;	
					currentTileYCount -= offsetY;
				}	
			} else if (curY < victim.getLocation().getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = attacker.getLocation().getZ();
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;	
		}
		for (int i = 0; i < path.length; i++) {
			if (!Region.getClipping(path[i][0], path[i][1], path[i][2], path[i][3], path[i][4])) {
				return true;	
			}
		}
		return false;
	}
	
	public static boolean meleePathBlocked(Entity attacker, Entity victim) {
		
		double offsetX = Math.abs(attacker.getLocation().getX() - victim.getLocation().getX());
		double offsetY = Math.abs(attacker.getLocation().getY() - victim.getLocation().getY());
		
		int distance = TileControl.calculateDistance(attacker, victim);
		
		if (distance == 0) {
			return true;
		}
		
		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];
		
		int curX = attacker.getLocation().getX();
		int curY = attacker.getLocation().getY();
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;
		
		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while(distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > victim.getLocation().getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;	
					currentTileXCount -= offsetX;
				}		
			} else if (curX < victim.getLocation().getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > victim.getLocation().getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;	
					currentTileYCount -= offsetY;
				}	
			} else if (curY < victim.getLocation().getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = attacker.getLocation().getZ();
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;	
		}
		for (int i = 0; i < path.length; i++) {
			if (!Region.getClipping(path[i][0], path[i][1], path[i][2], path[i][3], path[i][4])) {
				return true;	
			}
		}
		return false;
	}
	
}

