package com.runescape.model.objects;

import com.runescape.event.AreaEvent;
import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.player.Player;
import com.runescape.net.Packet;
/**
 * 
 * @author Abexlry
 *
 */
public class Gates {
	
	public static void openGateRight(final Player player, Packet packet, Location loc, final int oX, final int oY) {
		final int playerZ = player.getLocation().getZ();
	  World.getInstance().registerCoordinateEvent(new AreaEvent(player, oX - 1, oY - 1, oX + 1, oY + 1) {
	   @Override
	   public void run() {
		   player.teleport(Location.location(oX + 1, oY, playerZ));
	   }
	  });
	 }
 
 public static void openGateUp(final Player player, Packet packet, Location loc, final int oX, final int oY) {
		final int playerZ = player.getLocation().getZ();
		World.getInstance().registerCoordinateEvent(new AreaEvent(player, oX - 1, oY - 1, oX + 1, oY + 1) {
			  
	   @Override
	   public void run() {
		   player.teleport(Location.location(oX, oY + 1, playerZ));
	   }
	  });
	 }
 
 public static void openGateLeft(final Player player, Packet packet, Location loc, final int oX, final int oY) {
		final int playerZ = player.getLocation().getZ();
		World.getInstance().registerCoordinateEvent(new AreaEvent(player, oX - 1, oY - 1, oX + 1, oY + 1) {
			  
	   @Override
	   public void run() {
		   player.teleport(Location.location(oX - 1, oY, playerZ));
	   }
	  });
	 }
 
 public static void openGateDown(final Player player, Packet packet, Location loc, final int oX, final int oY) {
		final int playerZ = player.getLocation().getZ();
		World.getInstance().registerCoordinateEvent(new AreaEvent(player, oX - 1, oY - 1, oX + 1, oY + 1) {
			  
	   @Override
	   public void run() {
		   player.teleport(Location.location(oX, oY - 1, playerZ));
	   }
	  });
	 }
 
 public static int checkFace(Player player, int oX, int oY) { 
	 switch (oX) { //add gates X (for north/south facing gates)
	 case 3189:
	 case 3188:
	 case 3181:
	 case 3180:
	 case 3197:
	 case 3198:
	 case 3146:
	 case 3132:
	 case 3131:
	 case 3145:
	 case 3078:
	 case 3077:
	 case 3261:
	 case 3176:
	 case 3444:
	 case 3443:
	 case 3177:
	 case 3109:
	 case 3107:
	 case 3111:
		 
		 switch (oY) { //add gates Y (for north/south facing gates)
		 case 3279:	
		 case 3289:
		 case 3281:
		 case 3291:
		 case 3258:
		 case 9918:
		 case 3458:
		 case 3321:
		 case 3316:
		 case 3167:
		 case 3162:
			 
			 openGateNS(player, oX, oY);
			 return 1;
		 }
		 
	 }
	 openGateWE(player, oX, oY);
	 return 0;
 }
 
 public static void openGateNS(Player player, int oX, int oY) {
		final int pY = player.getLocation().getY();
	 if (pY > oY) {
		 openGateDown(player, null, null, oX, oY);
	 } else {
		 openGateUp(player, null, null, oX, oY);
	 }
 }
 
 public static void openGateWE(Player player, int oX, int oY) {
		final int pX = player.getLocation().getX();
	 if (pX < oX) {
		 openGateRight(player, null, null, oX, oY);
	 } else {
		 openGateLeft(player, null, null, oX, oY);
	 }
 }

}
