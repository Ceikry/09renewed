package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.content.CharacterDesign;
import com.runescape.model.player.Player;
import com.runescape.net.Packet;
import com.runescape.util.log.Logger;

/**
 * 
 * All packets representing 'buttons'.
 * @author Luke132
 */
public class ActionButton implements PacketHandler {

	private static final int CLOSE = 184; // d
	private static final int ACTIONBUTTON = 155; // d
	private static final int ACTIONBUTTON2 = 10; // d
	private static final int ACTIONBUTTON3 = 132; //d
	
	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		
		switch(packet.getId()) {
			case CLOSE:
				handleCloseButton(player, packet);
				break;
			
			case ACTIONBUTTON:
			case ACTIONBUTTON2:
				handleActionButton(player, packet);
				break;
				
			case ACTIONBUTTON3:
				handleActionButton3(player, packet);
				break;
		}
	}

	@SuppressWarnings("unused")
	private void handleActionButton3(Player player, Packet packet) {
		int id = packet.readShort();
		int interfaceId = packet.readShort();
		int junk = packet.readLEShort();
		
		
	}

	private void handleCloseButton(Player player, Packet packet) {
		if (player.getTrade() != null) {
			player.getTrade().decline();
		}
		
		if (player.isInWelcomeScreen()) {
			
			player.setInWelcomeScreen(false);
			if(player.isHd()) {
				player.getActionSender().sendWindowPane(746);
			} else {
				player.getActionSender().sendWindowPane(548);
			}
		} else if (!player.isInWelcomeScreen()) {
			return;
		}
//		if (player.firstCustomization) {
//			CharacterDesign.open(player);
//		} else {
			player.getActionSender().closeInterfaces();
//		}
	}

	private void handleActionButton(Player player, Packet packet) {
		int interfaceId = packet.readShort() & 0xFFFF;
		int buttonId = packet.readShort() & 0xFFFF;
		int buttonId2 = 0;
		if(packet.getLength() >= 6) {
			buttonId2 = packet.readShort() & 0xFFFF;
		}
		if(buttonId2 == 65535) {
			buttonId2 = 0;
		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("Button: " + interfaceId + " " +buttonId + " " +buttonId2);
			//System.out.println("button = " + interfaceId + " " +buttonId + " " +buttonId2);
		}
		switch(interfaceId) {
		
		
			case 182: // Logout tab.
				player.getActionSender().logout();
				break;
				
			
			
 			
 			case 102: // Items on death interface
 				if (buttonId == 18) {
 				 	player.getActionSender().sendMessage("You will keep this item should you die.");
 				} else {
 				 	player.getActionSender().sendMessage("You will lose this item should you die.");
 				}
 				break;
 				
			default:
				if (interfaceId != 548 && interfaceId != 751) {
					//logger.info("Unhandled ActionButton : " + interfaceId + " " + buttonId + " " + buttonId2);
				}
				break;
		}
	}
	
	private Logger logger = Logger.getInstance();

}
