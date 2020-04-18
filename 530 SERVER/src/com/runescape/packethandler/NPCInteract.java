package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.Constants;
import com.runescape.content.CharacterDesign;
import com.runescape.model.Entity;
import com.runescape.model.World;
import com.runescape.model.npc.NPC;
import com.runescape.model.npc.NPCDefinition;
import com.runescape.model.player.Player;
import com.runescape.net.Packet;

/**
 * 
 * NPC clicking packets.
 * @author Luke132
 */
public class NPCInteract implements PacketHandler {

	private static final int EXAMINE_NPC = 72;
	private static final int FIRST_CLICK = 3; // d
	private static final int SECOND_CLICK = 78; // d
	private static final int THIRD_CLICK = 148; // d
	private static final int FOURTH_CLICK = 30;
	private static final int FIFTH_CLICK = 218;
	private static final int MAGIC_ON_NPC = 239; // d
	private static final int ITEM_ON_NPC = 115; // d
	
	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		switch(packet.getId()) {
			case FIRST_CLICK:
				handleFirstClickNPC(player, packet);
				break;
				
			case SECOND_CLICK:
				handleSecondClickNPC(player, packet);
				break;
				
			case THIRD_CLICK:
				handleThirdClickNPC(player, packet);
				break;
				
			case FOURTH_CLICK:
				handleFourthClickNPC(player, packet);
				return;
				
			case FIFTH_CLICK:
				handleFifthClickNPC(player, packet);
				break;
				
			case MAGIC_ON_NPC:
				handleMagicOnNPC(player, packet);
				break;
				
			case ITEM_ON_NPC:
				handleItemOnNPC(player, packet);
				break;
				
			case EXAMINE_NPC:
				handleExamine(player, packet);
				break;
		}
	}

	private void handleFirstClickNPC(Player player, Packet packet) {
		int npcIndex = packet.readLEShortA();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("1st Click NPC: " + npc.getId());
			//System.out.println("NPC ID = " + npc.getId());
		}
	}
	
	private void handleSecondClickNPC(Player player, Packet packet) {
		int npcIndex = packet.readLEShort();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("2nd Click NPC: " + npc.getId());
			//System.out.println("Second click NPC " + npc.getId());
		}
		
		
		switch(npc.getId()) {
		
			
			
		}
	}

	@SuppressWarnings("unused")
	private void handleThirdClickNPC(Player player, Packet packet) {
		int npcIndex = packet.readShortA();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		int npcX = npc.getLocation().getX();
		int npcY = npc.getLocation().getY();
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("3rd Click NPC: " + npc.getId());
			//System.out.println("Third click NPC " + npc.getId());
		}
		
		switch(npc.getId()) {
			case 44:
			case 45:
			case 166:
			case 494:
			case 495:
			case 496:
			case 497:
			case 498:
			case 499:
			case 953:
			case 1036:
			case 1360:
			case 1702:
			case 2163:
			case 2164:
			case 2354:
			case 2355:
			case 2568:
			case 2569:
			case 2570:
			case 3046:
			case 3198:
			case 3199:
			case 4519:
			case 5258:
			case 5260:
			case 5776:
			case 5777:
			case 5912:
			case 5913:
			case 6200:
			case 6532:
			case 6533:
			case 6534:
			case 6535:
			case 6538:
			case 7049:
			case 7050:
			case 7445:
			case 7446:
			case 7605:
				player.getBank().openNpcBank(player, npc);
				break;
				
		}
	}
	
	private void handleFourthClickNPC(Player player, Packet packet) {
		int npcIndex = packet.readShort();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("4th Click NPC: " + npc.getId());
			//System.out.println("Fourth click NPC " + npc.getId());
		}
		
		switch(npc.getId()) {
			
			case 548:
				CharacterDesign.open(player);
				break;
			
		}
	}
	
	private void handleFifthClickNPC(Player player, Packet packet) {
		int npcIndex = packet.readLEShort();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("5th Click NPC: " + npc.getId());
			//System.out.println("Fifth click NPC " + npc.getId());
		}
		
	}
	
	@SuppressWarnings("unused")
	private void handleMagicOnNPC(Player player, Packet packet) {
		int childId = packet.readLEShort();
		int interfaceId = packet.readLEShort();
		int junk = packet.readShortA();
	    int npcIndex = packet.readLEShortA();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		
		player.setTarget(npc);
		Entity target = player.getTarget();
//		if (Location.projectilePathBlocked(player, target) && npc.getMaxHit() != 0) {
//			MagicCombat.newMagicAttack(player, npc, childId, interfaceId == 193);
//		} else {
			if (npc.getMaxHit() == 0) {
				player.getActionSender().sendMessage("I can't attack this npc!");
			} else if (npc.getMaxHit() != 0) {
				player.getActionSender().sendMessage("I can't reach that!");
				player.getWalkingQueue().reset();
			}
//		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("Spell " + childId + " on NPC: " + npc.getId());
			//System.out.println("Spell ID = " + childId);
		}
	}
	
	@SuppressWarnings("unused")
	private void handleItemOnNPC(Player player, Packet packet) {
		int interfaceId = packet.readInt();
		int slot = packet.readLEShort();
		int npcIndex = packet.readLEShort();
		int item = packet.readLEShortA();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		
		player.getActionSender().closeInterfaces();
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("Item " + item + " on NPC: " + npc.getId());
			//System.out.println("Item on NPC " + npc.getId());
		}
		if (player.getInventory().getItemInSlot(slot) == item) {
			switch(npc.getId()) {
				
			}
		}
	}
	
	private void handleExamine(Player player, Packet packet) {
		NPCDefinition def;
		int npcIndex = packet.readShort();
		if (npcIndex < 0 || npcIndex > Constants.NPC_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		def = NPCDefinition.forId(npcIndex);
		final NPC npc = World.getInstance().getNpcList().get(npcIndex);
		if (npc == null || npc.isDestroyed()) {
			return;
		}
		if (player.getRights() == 1 || player.getRights() == 2) {
			player.getActionSender().sendMessage("Examine NPC: " + npc.getId());
			//System.out.println("Examine NPC " + npc.getId());
		}
		
		player.getActionSender().sendMessage(def.getExamine());
	}

}
