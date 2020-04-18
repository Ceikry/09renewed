package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.model.player.Player;
import com.runescape.net.Packet;
import com.runescape.util.EnterVariable;

public class InterfaceOptions implements PacketHandler {

	private static final int ENTER_AMOUNT = 23; // d
	private static final int CLICK_1 = 81; // d
	private static final int CLICK_2 = 196; // d
	private static final int CLICK_3 = 124; // d
	private static final int CLICK_4 = 199; // d
	private static final int CLICK_5 = 234; // d
	private static final int CLICK_6 = 168; // d
	private static final int CLICK_7 = 166; // d
	private static final int CLICK_8 = 64; // d
	private static final int CLICK_9 = 53; // d
	private static final int CLICK_10 = 223;
	private static final int GE_SEARCH = 111; // d

	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		switch(packet.getId()) {
			case ENTER_AMOUNT:
				handleEnterAmount(player, packet);
				break;
				
			
			case CLICK_1:
				handleClickOne(player, packet);
				break;
				
			case CLICK_2:
				handleClickTwo(player, packet);
				break;
				
			case CLICK_3:
				handleClickThree(player, packet);
				break;
				
			case CLICK_4:
				handleClickFour(player, packet);
				break;
				
			case CLICK_5:
				handleClickFive(player, packet);
				break;
				
			case CLICK_6:
				handleClickSix(player, packet);
				break;
				
			case CLICK_7:
				handleClickSeven(player, packet);
				break;
				
			case CLICK_8:
				handleClickEight(player, packet);
				break;
				
			case CLICK_9:
				handleClickNine(player, packet);
				break;
				
			case CLICK_10:
				handleClickTen(player, packet);
				break;
				
			case GE_SEARCH:
				handleGeSearch(player, packet);
				break;
		}
	}

	private void handleEnterAmount(Player player, Packet packet) {
		if (player.getTemporaryAttribute("interfaceVariable") == null) {
			player.getActionSender().sendMessage("An error occured, please try again.");
			return;
		}
		EnterVariable var = (EnterVariable) player.getTemporaryAttribute("interfaceVariable");
		int amount = packet.readInt();
		
			
			player.removeTemporaryAttribute("interfaceVariable");
	}
	
	
	private void handleClickOne(Player player, Packet packet) {
		int slot = packet.readShortA();
		int item = packet.readShort();
		int childId = packet.readShort();
		int interfaceId = packet.readShort();
		if (slot < 0 || slot > 28 || player.isDead()) {
			return;
		}
		player.getActionSender().closeInterfaces();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 1: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 1: interfaceId: " + interfaceId);
		}
	
	}
	
	private void handleClickTwo(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 2: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 2: interfaceId: " + interfaceId);
		}
		
	}

	private void handleClickThree(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 3: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 3: interfaceId: " + interfaceId);
		}
		
	}
	
	private void handleClickFour(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 4: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 4: interfaceId: " + interfaceId);
		}
		
	}
	
	private void handleClickFive(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 5: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 5: interfaceId: " + interfaceId);
		}
		
	}
	
	private void handleClickSix(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 6: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 6: interfaceId: " + interfaceId);
		}
		
	}
	
	private void handleClickSeven(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 7: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 7: interfaceId: " + interfaceId);
		}
		
	}
	
	private void handleClickEight(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 8: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 8: interfaceId: " + interfaceId);
		}
		
	}

	private void handleClickNine(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 9: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 9: interfaceId: " + interfaceId);
		}
		
	}

	private void handleClickTen(Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int child = packet.readShort();
		int slot = packet.readShort();
		if (player.getRights() == 1 || player.getRights() == 2) {
			//Logger.getInstance().info("InterfaceOption 10: interfaceId: " + interfaceId);
			player.getActionSender().sendMessage("InterfaceOption 10: interfaceId: " + interfaceId);
		}
	}
	
	private void handleGeSearch(Player player, Packet packet) {
		int item = packet.readShort();
		if (item < 0 || item > 16000) {
			return;
		}
		
	}
}
