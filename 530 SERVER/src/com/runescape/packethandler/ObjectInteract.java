package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.content.skills.prayer.Prayer;
import com.runescape.model.Location;
import com.runescape.model.masks.FaceLocation;
import com.runescape.model.player.Player;
import com.runescape.net.Packet;
import com.runescape.util.Area;


/**
 * 
 * Object clicking packets.
 * @author Luke132
 */
public class ObjectInteract implements PacketHandler {

	private static final int FIRST_CLICK = 254; // d
	private static final int SECOND_CLICK = 194; // d
	private static final int THIRD_CLICK = 84; // d
	private static final int FOURTH_CLICK = 247;
	
	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		switch(packet.getId()) {
			case FIRST_CLICK:
				handleFirstClickObject(player, packet);
				break;
				
			case SECOND_CLICK:
				handleSecondClickObject(player, packet);
				break;
				
			case THIRD_CLICK:
				handleThirdClickObject(player, packet);
				break;
				
			case FOURTH_CLICK:
				handleFourthClickObject(player, packet);
				break;
		}
	}

	private void handleFirstClickObject(final Player player, Packet packet) {
		int objectX = packet.readLEShort();
		int objectId = packet.readShortA();
		int objectY = packet.readShort();
		Location loc = player.getLocation();
		if (player.getObjectClickDelay() > System.currentTimeMillis()) {
			return;
		} else if (player.getObjectClickDelay() < System.currentTimeMillis()) {
			player.setObjectClickDelay(1000);
		}
		if (objectX < 1000 || objectY < 1000 || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}

		player.getActionSender().closeInterfaces();
		if (player.getRights() == 2) {
			player.getActionSender().sendMessage("1st Object Click " + objectId + " " + objectX + " " + objectY);
			//System.out.println("First object click = " + objectId + " " + objectX + " " + objectY);
		}
		
		player.setFaceLocation(new FaceLocation(objectX, objectY));
		switch(objectId) {
		
		case 36972: //altar
			Prayer.prayAtAltar(player, objectId, Location.location(objectX, objectY, player.getLocation().getZ()));
			break;
		
			
		
				
			case 2213: // Catherby bank booth.
			case 11402: // Varrock bank booth.
			case 11758: // Falador bank booth.
			case -28750: // Lumbridge bank booth.
			case -29889: // Al-Kharid bank booth.
			case 25808: // Seers bank booth.
			case -30784: // Ardougne bank booth.
			case 26972: // Edgeville bank booth.
			case 29085: // Ooglog bank booth.
				player.getBank().openBank(true, objectX, objectY);
				break;
				
		
				
			case 23271: // Wilderness ditch
				Area.crossDitch(player, objectX, objectY);
				break;

			
		}
	}
	
	private void handleSecondClickObject(Player player, Packet packet) {
		int objectY = packet.readLEShortA();
		int objectX = packet.readLEShort();
		int objectId = packet.readShort();
		if (player.getObjectClickDelay() > System.currentTimeMillis()) {
			return;
		} else if (player.getObjectClickDelay() < System.currentTimeMillis()) {
			player.setObjectClickDelay(1000);
		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("2nd Object Click " + objectId + " " + objectX + " " + objectY);
			//System.out.println("Second object click = " + objectId + " " + objectX + " " + objectY);
		}
		if (player.getTemporaryAttribute("unmovable") != null) {
			return;
		}
		if (objectX < 1000 || objectY < 1000 || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		
		player.getActionSender().closeInterfaces();
		player.setFaceLocation(new FaceLocation(objectX, objectY));
		
		switch(objectId) {
		
			
				
			case 27663: // Duel arena bank chest.
			case 2213: // Catherby bank booth.
			case 11402: // Varrock bank booth.
			case 11758: // Falador bank booth.
			case -28750: // Lumbridge bank booth.
			case -29889: // Al-Kharid bank booth.
			case 25808: // Seers bank booth.
			case -30784: // Ardougne bank booth.
			case 26972: // Edgeville bank booth.
			case 29085: // Ooglog bank booth.
			case 24914: //canifis bank booth.
				player.getBank().openBank(false, objectX, objectY);
				break;
		}
	}
	
	private void handleThirdClickObject(Player player, Packet packet) {
		int id = packet.readLEShortA();
		int objectY = packet.readLEShortA();
		int objectX = packet.readLEShort();
		if (player.getObjectClickDelay() > System.currentTimeMillis()) {
			return;
		} else if (player.getObjectClickDelay() < System.currentTimeMillis()) {
			player.setObjectClickDelay(1000);
		}
		if (player.getTemporaryAttribute("unmovable") != null) {
			return;
		}
		if (objectX < 1000 || id < 0 || objectY < 1000 || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		player.getActionSender().closeInterfaces();
		player.setFaceLocation(new FaceLocation(objectX, objectY));
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("3rd Object Click " + id + " " + objectX + " " + objectY);
			//System.out.println("Third object click = " + id + " " + objectX + " " + objectY);
		}
		
		switch(id) {
			
				
		}
	}
	
	private void handleFourthClickObject(Player player, Packet packet) {
		int y = packet.readLEShort();
		int x = packet.readLEShortA();
		int id = packet.readShort();
		if (player.getObjectClickDelay() > System.currentTimeMillis()) {
			return;
		} else if (player.getObjectClickDelay() < System.currentTimeMillis()) {
			player.setObjectClickDelay(1000);
		}
		if (player.getTemporaryAttribute("unmovable") != null) {
			return;
		}
		if (x < 1000 || id < 0 || y < 1000 || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		
		player.getActionSender().closeInterfaces();
		player.setFaceLocation(new FaceLocation(x, y));
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("1st Object Click " + id + " " + x + " " + y);
			//System.out.println("Fourth object click = " + id + " " + x + " " + y);
		}
		switch(id) {
		
		}
	}

}
