package com.runescape.model.player;

import com.runescape.content.skills.magic.AutoCast;
import com.runescape.model.Item;
import com.runescape.model.ItemDefinition;
import com.runescape.util.ItemData;

public class Equipment {

	private Item[] slots = new Item[14];
	private transient Player p;
	//public transient int 0 = 0;
	public transient int playerCape = 1;
	public transient int playerAmulet = 2;
	//public transient int 3 = 3;
	public transient int playerChest = 4;
	//public transient int 5 = 5;
	public transient int playerLegs = 7;
	public transient int playerHands = 9;
	public transient int playerFeet = 10;
	public transient int playerRing = 12;
	public transient int playerArrows = 13;
	
	public Equipment() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new Item(-1, 0);
		}
	}

	public Item[] getEquipment() {
		return slots;
	}
	
	public boolean equipItem(int itemID, int slot) {
		
		int s = ItemData.getItemType(itemID);
		int amount = p.getInventory().getAmountInSlot(slot);
		boolean stackable = ItemDefinition.forId(itemID).isStackable();
		boolean twoHanded = ItemData.isTwoHanded(itemID);
		if (s == -1) {
			p.getActionSender().sendMessage("Unable to find an item slot for item : " + itemID + " , please report this to a staff member.");
			return false;
		}
		
		if (twoHanded) {
			if (p.getInventory().getTotalFreeSlots() < getNeeded2HSlots()) {
				p.getActionSender().sendMessage("Not enough space in your inventory.");
				return false;
			} 
		}
		if (!p.getInventory().deleteItem(itemID, slot, amount)) {
			return false;
		}
		if (twoHanded && getItemInSlot(5) != -1) {
			if (!unequipItem(5)) {
				return false;
			}
		}
		if (s == 5) {
			if (getItemInSlot(3) != -1) {
				if (ItemData.isTwoHanded(slots[3].getItemId())) {
					if (!unequipItem(3)) {
						return false;
					}	
				}
			}
		}
		if (slots[s].getItemId() != itemID && slots[s].getItemId() > 0) {
			if (!p.getInventory().addItem(slots[s].getItemId(), slots[s].getItemAmount(), slot)) {
				return false;
			}
			
		} 
		else if (stackable && slots[s].getItemId() == itemID) {
			amount = slots[s].getItemAmount() + amount;
		}
		else if (slots[s].getItemId() != -1) {
			p.getInventory().addItem(slots[s].getItemId(), slots[s].getItemAmount(), slot);
		}
		slots[s].setItemId(itemID);
		slots[s].setItemAmount(amount);
		p.getActionSender().refreshEquipment();
		p.getUpdateFlags().setAppearanceUpdateRequired(true);
		
		if (s == 3) {
			setWeapon();
			AutoCast.cancel(p, true);
		}
		p.getBonuses().refresh();
		p.setEntityFocus(65535);
		return true;
	}
	
	public void checkLevel(int itemID, int slot) {
        int checkAttack = getCheckAttack(itemID);
        int checkDefence = getCheckDefence(itemID);
        int checkStrength = getCheckStrength(itemID);
        int checkRanged = getCheckRanged(itemID);
        int checkMagic = getCheckMagic(itemID);
        
        if (checkAttack > p.getLevels().getLevel(0)) {
        	p.getActionSender().sendMessage("You need " + checkAttack + " Attack to equip this item.");
            return;
        }
        if (checkDefence > p.getLevels().getLevel(1)) {
        	p.getActionSender().sendMessage("You need " + checkDefence + " Defence to equip this item.");
            return;
        }
        if (checkStrength > p.getLevels().getLevel(2)) {
        	p.getActionSender().sendMessage("You need " + checkStrength + " Strength to equip this item.");
            return;
        }
        if (checkRanged > p.getLevels().getLevel(4)) {
        	p.getActionSender().sendMessage("You need " + checkRanged + " Ranged to equip this item.");
            return;
        }
        if (checkMagic > p.getLevels().getLevel(6)) {
        	p.getActionSender().sendMessage("You need " + checkMagic + " Magic to equip this item.");
            return;
        }
		equipItem(itemID, slot);
	}

	//Attack = 0
	//Defence = 1
	//Strength = 2
	//Ranged = 4
	//Magic = 6
	
	public int getCheckAttack(int itemID) {
		switch (itemID) {
		case 1207: //start of steel
		case 1241: //spear
		case 1281:
		case 1311:
		case 1325:
		case 1339:
		case 1295:
		case 1269:
		case 1353:
		case 1365:
		case 1424:
		case 3097:
			return 5;
		case 1217: //START OF BLACK
		case 4580: //spear
		case 1297: //longsword
		case 1327: //scimitar
		case 1361: //axe
		case 1367: //battleaxe
		case 1426: //mace
		case 3196: //halberd
			return 10;
		case 1209: //START OF MITHRIL
		case 1243: //spear
		case 1299: //longsword
		case 1329: //scimitar
		case 1355: //axe
		case 1369: //battleaxe
		case 1428: //mace
		case 3198: //halberd
		      return 20;
		case 1211: //START OF ADAMAMANT
		case 1245: //spear
		case 1301: //longsword
		case 1331: //scimitar
		case 1357: //axe
		case 1371: //battleaxe
		case 1430: //mace
		case 3200: //halberd
	  	case 1393: //Fire battlestaff
	  	case 1397: // air battlestaff
	  	case 1399: // earth battlestaff
	  	case 1395: // water battlestaff
	  	case 3053: // lava battlestaff
	  	case 6562: // mud battlestaff
	  	case 11736: //steam battlestaff
			return 30;
		case 1213: //START OF RUNE
		case 1247: //spear
		case 1303: //longsword
		case 1333: //scimitar
		case 1359: //axe
		case 1373: //battleaxe
		case 1432: //mace
		case 3202: //halberd
	  	case 1405: //mystic air staff
	  	case 1407: //mystic earth staff
	  	case 1401: //mystic fire staff
	  	case 1403: //mystic water staff
	  	case 3054: //mystic lava staff
	  	case 6563: //mystic mud staff
			return 40;
	  	case 4153: //granite maul
	  	case 4675: //ancient staff
		  	return 50;
		case 1215: //START OF DRAGON
		case 1231: //dagger(p:
	  	case 5680: //dagger(p+:
	  	case 1249: //spear
	  	case 1263: //spear(p:
	  	case 5716: //spear(p+:
	  	case 1305: //longsword
	  	case 4587: //scimitar
	  	case 6739: //axe
	  	case 1377: //battleaxe
	  	case 1434: //mace
	  	case 3204: //halberd
	  	case 6523: //toktx-xil-ak
	  	case 6539: //tzhaar-ket-em
	  	case 6525: //toktz-xil-ek
	  	case 6526: //toktz-mej-tal
	  	case 14484: //claws
	  		return 60;
	  	case 4151: //whip
	  	case 4755: //verac's flail
	  	case 4747: //torag's hammers
	  	case 4718: //dharok's greataxe
	  	case 4726: //guthan's warspear
	  	case 4710: //ahrim's staff
	  		return 70;
	  	case 13899: //vesta
	  	case 13905:
	  	case 13902: //stat
	  	case 13867: //zuriel staff
	  		return 78;
		}
        return 1;
    }
	
	public int getCheckDefence(int itemID) {
		switch(itemID) {
	    case 4123:
	    case 1069:
	    case 1083:
	    case 1157:
	    case 1141:
	    case 1119:
	        return 5;
	    case 1131:
	    case 6621: //White med helm
	    case 6615: //White chainbody
	    case 6623: //White full helm
	    case 6617: //White platebody
	    case 6627: //White plateskirt
	    case 6625: //White platelegs
	    case 6633: //White kiteshield
	    case 6631: //White sq shield
	    case 2597: //Black kiteshield(g:
	    case 2589: //Black kiteshield(t:
	    case 1151: //Black med helm
	    case 1107: //Black chainbody
	    case 1165: //Black full helm
	    case 1125: //Black platebody
	    case 2591: //Black platebody(g:
	    case 2583: //Black platebody(t:
	    case 1089: //Black plateskirt
	    case 3473: //Black plateskirt(g:
	    case 3472: //Black plateskirt(t:
	    case 1077: //Black platelegs
	    case 2593: //Black platelegs(g:
	    case 2585: //Black platelegs(t:
	    case 1195: //Black kiteshield
	    case 1179: //Black sq shield
	        return 10;
	    case 4125:
	    case 4127:
	    case 7459:
	    case 1133: //Studded body
	    case 7362: //Studded body(g:
	    case 7364: //Studded body(t:
	    case 1143: //Mithril med helm
	    case 1109: //Mithril chainbody
	    case 1159: //Mithril full helm
	    case 1121: //Mithril platebody
	    case 1085: //Mithril plateskirt
	    case 1071: //Mithril platelegs
	    case 1197: //Mithril kiteshield
	    case 1181: //Mithril sq shield
	    case 5574: //Initiate helm
	    case 5575: //Initiate platemail
	    case 5576: //Initiate platelegs
	    case 4089: //Mystic hat
	    case 4099: //Mystic hat
	    case 4109: //Mystic hat
	    case 4091: //Mystic robe top
	    case 4101: //Mystic robe top
	    case 4111: //Mystic robe top
	    case 4093: //Mystic robe bottom
	    case 4103: //Mystic robe bottom
	    case 4113: //Mystic robe bottom
	    case 4095: //Mystic gloves
	    case 4105: //Mystic gloves
	    case 4115: //Mystic gloves
	    case 4097: //Mystic boots
	    case 4107: //Mystic boots
	    case 4117: //Mystic boots
	    case 7400: //Enchanted hat
	    case 7399: //Enchanted top
	    case 7398: //Enchanted robe
	        return 20;
	    case 6922: //Infinity gloves
	    case 6918: //Infinity hat
	    case 6916: //Infinity top
	    case 6924: //Infinity bottoms
	    case 6920: //Infinity boots
	        return 25;
	    case 4129:
	    case 1145: //Adamant med helm
	    case 1111: //Adamant chainbody
	    case 1161: //Adamant full helm
	    case 1123: //Adamant platebody
	    case 1091: //Adamant plateskirt
	    case 3475: //Adamant plateskirt(g:
	    case 3474: //Adamant plateskirt(t:
	    case 1073: //Adamant platelegs
	    case 1199: //Adamant kiteshield
	    case 1183: //Adamant sq shield
	        return 30;
		case 4131:
	    case 2499:
	    case 2501:
	    case 2503:
	    case 1135:
	    case 2621: //Rune kiteshield(g:
	    case 2629: //Rune kiteshield(t:
	    case 7370: //D-hide body(g:
	    case 7372: //D-hide body(t:
	    case 1147: //Rune med helm
	    case 1113: //Rune chainbody
	    case 1163: //Rune full helm
	    case 1127: //Rune platebody
	    case 2615: //Rune platebody(g:
	    case 2623: //Rune platebody(t:
	    case 1093: //Rune plateskirt
	    case 3476: //Rune plateskirt(g:
	    case 3477: //Rune plateskirt(t:
	    case 1079: //Rune platelegs
	    case 2617: //Rune platelegs(g:
	    case 2625: //Rune platelegs(t:
	    case 1201: //Rune kiteshield
	    case 2657: //Zamorak full helm
	    case 2653: //Zamorak platebody
	    case 3478: //Zamorak plateskirt
	    case 2655: //Zamorak platelegs
	    case 2659: //Zamorak kiteshield
	    case 2673: //Guthix full helm
	    case 2669: //Guthix platebody
	    case 3480: //Guthix plateskirt
	    case 2671: //Guthix platelegs
	    case 2675: //Guthix kiteshield
	    case 2665: //Saradomin full
	    case 2661: //Saradomin plate
	    case 3479: //Saradomin plateskirt
	    case 2663: //Saradomin legs
	    case 1185: //Rune sq shield
	    case 3486: //Gilded full helm
	    case 3481: //Gilded platebody
	    case 3485: //Gilded plateskirt
	    case 3483: //Gilded platelegs
	    case 3488: //Gilded kiteshield
	    case 10551: //Fighter torso
	    case 3385: //Splitbark helm
	    case 3387: //Splitbark body
	    case 3391: //Splitbark gauntlets
	    case 3389: //Splitbark legs
	    case 3393: //Splitbark greaves
	        return 40;
	    case 7462:
	    case 7461:
	    case 7460:
	        return 42;
	    case 3749: //Archer helm
	    case 3751: //Berserker helm
	    case 3753: //Warrior helm
	    case 3755: //Farseer helm
	        return 45;
	    case 3122: //Granite shield
	        return 50;
	    case 7990:
	    case 1187: //Dragon sq shield
	    case 1149: //Dragon med helm
	    case 2513: //Dragon chainbody
	    case 4585: //Dragon plateskirt
	    case 4087: //Dragon platelegs
	    case 6524: //Toktz-ket-xil
	        return 60;
	    case 11724://bandos
	    case 11726:
	    case 11728:
	    	return 65;
	    case 4716: //Dharoks helm
	    case 4720: //Dharoks platebody
	    case 4722: //Dharoks platelegs
	    case 4724: //Guthans helm
	    case 4728: //Guthans platebody
	    case 4730: //Guthans chainskirt
	    case 4745: //Torags helm
	    case 4749: //Torags platebody
	    case 4751: //Torags platelegs
	    case 4753: //Veracs helm
	    case 4757: //Veracs brassard
	    case 4759: //Veracs plateskirt
	    case 4708: //Ahrims hood
	    case 4712: //Ahrims robetop
	    case 4714: //Ahrims robeskirt
	    case 4732: //Karils coif
	    case 4736: //Karils leathertop
	    case 4738: //Karils leatherskirt
	    case 4224: //New crystal shield
	    case 11718: //arma
	    case 11720:
	    case 11722:
	        return 70;
		}
	    return 1;
	}

	public int getCheckStrength(int itemID) {
		switch(itemID) {
        case 3196: //black halberd
        case 6599: //white halberd
            return 5;
        case 3198: //mithril halberd
            return 10;
        case 3200: //adamant halberd
            return 15;
        case 3202: //rune halberd
            return 20;
        case 3204: //dragon halberd
            return 30;
        case 3122: //Granite shield
        case 4153: //Granite maul
        case 6809: //Granite legs
        case 10565: //Granite body
        case 10589: //Granite helm
           return 50;
        case 6528: //Tzhaar-ket-om
           return 60;
        case 4747: //Torags hammers
        case 4718: //Dharoks greataxe
           return 70;
		}
		return 1;
	}

	public int getCheckRanged(int itemID) {
		switch(itemID) {
        case 808: //Steel dart
        case 814: //Steel dart(p:
        case 827: //Steel javelin
        case 833: //Steel javelin(p:
        case 802: //Steel throwing axe
        case 865: //Steel throwing knife
        case 872: //Steel throwing knife(p:
            return 5;
        case 3093: //black dart
        case 3094: //black dart(p:
        case 869: //black throwing knife
        case 874: //black throwing knife(p:
            return 10;
        case 809: //mithril dart
        case 815: //mithril dart(p:
        case 828: //mithril javelin
        case 834: //mithril javelin(p:
        case 803: //mithril throwing axe
        case 866: //mithril throwing knife
        case 873: //mithril throwing knife(p:
        case 1169: //Coif
        case 1097: //Studded chaps
        case 7366: //Studded chaps(g:
        case 7368: //Studded chaps(t:
        case 1133: //Studded body
        case 7362: //Studded body(g:
        case 7364: //Studded body(t:
            return 20;
        case 804: //Adamant thrownaxe
        case 810: //Adamant dart
        case 816: //Adamant dart(p:
        case 829: //Adamant javelin
        case 835: //Adamant javelin(p:
        case 867: //Adamant knife
        case 875: //Adamant knife(p:
            return 30;
        case 1135:
        case 1099:
        case 1065:
        case 859:
        case 861:
        case 7370:
        case 7372:
        case 7378:
        case 7380:
        case 2581: //Robin hood hat
        case 2577: //Ranger boots
        case 805: //Rune thrownaxe
        case 811: //Rune dart
        case 817: //Rune dart(p:
        case 830: //Rune javelin
        case 836: //Rune javelin(p:
        case 868: //Rune knife
        case 876: //Rune knife(p:
            return 40;
        case 2499:
        case 2487:
        case 2493:
        case 7374:
        case 7376:
        case 7382:
        case 7384:
        case 6724: //Seercull
            return 50;
        case 2489:
        case 2495:
        case 2501:
        case 2505:
        case 6522: //Toktz-xil-ul
            return 60;
        case 2503:
        case 2491:
        case 2507:
        case 4732: //Karils coif
        case 4736: //Karils leathertop
        case 4738: //Karils leatherskirt
        case 4214: //Crystal bow full
        case 4212: //New crystal bow
        case 4734: //Karils crossbow
        case 2497:
        case 4740: //Bolt rack
        case 11718: //arma
        case 11720:
        case 11722:
            return 70;
		}
		return 1;
	}
	
	public int getCheckMagic(int itemID) {
		switch(itemID) {
        case 1393: //Fire battlestaff
        case 1397: // air battlestaff
        case 1399: // earth battlestaff
        case 1395: // water battlestaff
        case 3053: // lava battlestaff
        case 6562: // mud battlestaff
        case 11736: //steam battlestaff
            return 30;
		case 4089: //Mystic hat
        case 4099: //Mystic hat
        case 4109: //Mystic hat
        case 4091: //Mystic robe top
        case 4101: //Mystic robe top
        case 4111: //Mystic robe top
        case 4093: //Mystic robe bottom
        case 4103: //Mystic robe bottom
        case 4113: //Mystic robe bottom
        case 4095: //Mystic gloves
        case 4105: //Mystic gloves
        case 4115: //Mystic gloves
        case 4097: //Mystic boots
        case 4107: //Mystic boots
        case 4117: //Mystic boots
        case 7400: //Enchanted hat
        case 7399: //Enchanted top
        case 7398: //Enchanted robe
        case 3385: //Splitbark helm
        case 3387: //Splitbark body
        case 3391: //Splitbark gauntlets
        case 3389: //Splitbark legs
        case 3393: //Splitbark greaves
        case 1405: //mystic air staff
        case 1407: //mystic earth staff
        case 1401: //mystic fire staff
        case 1403: //mystic water staff
        case 3054: //mystic lava staff
        case 6563: //mystic mud staff
            return 40;
        case 4170: //slayer's staff
        case 6922: //Infinity gloves
        case 6918: //Infinity hat
        case 6916: //Infinity top
        case 6924: //Infinity bottoms
        case 6920: //Infinity boots
        case 4675: //ancient staff
            return 50;
        case 6912:
        	return 55;
        case 2412: //Saradomin cape
        case 2415: //Saradomin staff
        case 2414: //Zamorak cape
        case 2417: //Zamorak staff
        case 2413: //Guthix cape
        case 2416: //Guthix staff
        case 6526: //toktz-mej-tal
        case 6914: //master wand
        case 6889: //mage book
            return 60;
        case 4708: //Ahrims hood
        case 4712: //Ahrims robetop
        case 4714: //Ahrims robeskirt
        case 4710: //Ahrims staff
            return 70;
		}
        return 1;
	}
	


	public void clearAll() {
		for (int i = 0; i < slots.length; i++) {
			slots[i].setItemId(-1);
			slots[i].setItemAmount(0);
		}
		setWeapon();
		p.getActionSender().refreshEquipment();
		p.getUpdateFlags().setAppearanceUpdateRequired(true);
		p.getBonuses().refresh();
	}
	
	public boolean unequipItem(int slot) {
		if (p.getInventory().addItem(slots[slot].getItemId(), slots[slot].getItemAmount())) {
			
			slots[slot].setItemId(-1);
			slots[slot].setItemAmount(0);
			p.getActionSender().refreshEquipment();
			p.getUpdateFlags().setAppearanceUpdateRequired(true);
			p.getBonuses().refresh();
			p.setEntityFocus(65535);
			if (slot == 3) {
				setWeapon();
				AutoCast.cancel(p, true);
			}
			return true;
		}
		return false;
	}
	
	private int getNeeded2HSlots() {
		int shield = slots[5].getItemId();
		int weapon = slots[3].getItemId();
		if ((shield != -1 && weapon == -1)  || (shield == -1 && weapon != -1) || (shield == -1 && weapon == -1)) {
			return 0;
		}
		return 1;
	}
	
	public void setWeapon() {
		if(slots[3].getItemId() == -1) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 92);
			p.getActionSender().modifyText("Unarmed", 92, 0);
			return;
		}
		String weapon = slots[3].getDefinition().getName();
		p.setTarget(null);
		int interfaceId = -1;
		if(weapon.equals("Abyssal whip")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 93);
			p.getActionSender().modifyText(weapon, 93, 0);
			interfaceId = 93;
		} else if (weapon.equals("Granite maul") || weapon.equals("Tzhaar-ket-om") || weapon.equals("Torags hammers")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 76);
			p.getActionSender().modifyText(weapon, 76, 0);
			interfaceId = 76;
		} else if(weapon.equals("Veracs flail") || (weapon.endsWith("mace") && !weapon.equals("Void knight mace"))) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 88);
			p.getActionSender().modifyText(weapon, 88, 0);
			interfaceId = 88;
		} else if(weapon.endsWith("crossbow") || weapon.endsWith(" c'bow")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 79);
			p.getActionSender().modifyText(weapon, 79, 0);
			interfaceId = 79;
		} else if(weapon.endsWith("bow") || weapon.endsWith("bow full") || weapon.equals("Seercull")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 77);
			p.getActionSender().modifyText(weapon, 77, 0);
			interfaceId = 77;
		} else if(weapon.startsWith("Staff") || weapon.endsWith("staff") || weapon.equals("Toktz-mej-tal") || weapon.equals("Void knight mace")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 90);
			p.getActionSender().modifyText(weapon, 90, 0);
			interfaceId = 90;
		} else if(weapon.endsWith("dart") || weapon.endsWith("knife") || weapon.endsWith("javelin") || weapon.endsWith("thrownaxe") || weapon.equals("Toktz-xil-ul")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 91);
			p.getActionSender().modifyText(weapon, 91, 0);
			interfaceId = 91;
		} else if(weapon.endsWith("dagger") || weapon.endsWith("dagger(s)") || weapon.endsWith("dagger(+)") || weapon.endsWith("dagger(p)")  || weapon.endsWith("dagger(p++)")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 89);
			p.getActionSender().modifyText(weapon, 89, 0);
			interfaceId = 89;
		} else if(weapon.endsWith("pickaxe")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 83);
			p.getActionSender().modifyText(weapon, 83, 0);
			interfaceId = 83;
		} else if(weapon.endsWith("axe") || weapon.endsWith("battleaxe") || weapon.endsWith("adze")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 75);
			p.getActionSender().modifyText(weapon, 75, 0);
			interfaceId = 75;
		} else if(weapon.endsWith("halberd")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 84);
			p.getActionSender().modifyText(weapon, 84, 0);
			interfaceId = 84;
		} else if(weapon.endsWith("spear") || weapon.equals("Guthans warspear")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 85);
			p.getActionSender().modifyText(weapon, 85, 0);
			interfaceId = 85;
		} else if(weapon.endsWith("claws")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 78);
			p.getActionSender().modifyText(weapon, 78, 0);
			interfaceId = 78;
		} else if(weapon.endsWith("2h sword") || weapon.endsWith("godsword") || weapon.equals("Saradomin sword")) {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 82);
			p.getActionSender().modifyText(weapon, 81, 0);
			interfaceId = 81;
		} else {
			p.getActionSender().sendTab(p.isHd() ? 93 : 83, 82);
			p.getActionSender().modifyText(weapon, 82, 0);
			interfaceId = 82;
		}
		setSpecials();
	}
	
	private void setSpecials() {
		int weaponId = slots[3].getItemId();
		if (weaponId == 4151) {
			p.getActionSender().showChildInterface(93, 10, true);
		} else if (weaponId == 5698 || weaponId == 1231 || weaponId == 5680
				|| weaponId == 1215 || weaponId == 8872 || weaponId == 8874
				|| weaponId == 8876 || weaponId == 8878) {
			p.getActionSender().showChildInterface(89, 12, true);
			
		} else if (weaponId == 35 || weaponId == 1305 || weaponId == 4587
				|| weaponId == 6746 || weaponId == 11037 || weaponId == 13902) {
			p.getActionSender().showChildInterface(82, 12, true);
			
		} else if (weaponId == 7158 || weaponId == 11694 || weaponId == 11696
				|| weaponId == 11698 || weaponId == 11700 || weaponId == 11730) {
			p.getActionSender().showChildInterface(81, 12, true);
			
		} else if (weaponId == 859 || weaponId == 861 || weaponId == 6724
				|| weaponId == 10284 || weaponId == 859 || weaponId == 11235) {
			p.getActionSender().showChildInterface(77, 13, true);
			
		} else if (weaponId == 8880) {
			p.getActionSender().showChildInterface(79, 10, true);
			
		} else if (weaponId == 3101 || weaponId == 14484) {
			p.getActionSender().showChildInterface(78, 12, true);
			
		} else if (weaponId == 1434 || weaponId == 11061 || weaponId == 10887) {
			p.getActionSender().showChildInterface(88, 12, true);
			
		} else if (weaponId == 1377 || weaponId == 6739) {
			p.getActionSender().showChildInterface(75, 12, true);
			
		} else if (weaponId == 4153) {
			p.getActionSender().showChildInterface(76, 10, true);
			
		} else if (weaponId == 3204) {
			p.getActionSender().showChildInterface(84, 10, true);
			
		/* SPEARS */
		} else if (weaponId == 1249 || weaponId == 13905) {
			p.getActionSender().showChildInterface(85, 10, true);
		}
	}
	
	public int getStandWalkAnimation() {
		if (p.getAppearance().getWalkAnimation() > 0) {
			return p.getAppearance().getWalkAnimation();
		}
		if(slots[3].getItemId() == -1) {
			return 1426;
		}
		return ItemDefinition.forId(slots[3].getItemId()).getAnimation();
	}
	
	public Item getSlot(int slot) {
		return slots[slot];
	}
	
	public int getAmountInSlot(int slot) {
		return slots[slot].getItemAmount();
	}

	public int getItemInSlot(int slot) {
		return slots[slot].getItemId();
	}

	public void setPlayer(Player player) {
		this.p = player;
	}

	public void displayEquipmentScreen() {
		p.getWalkingQueue().reset();
		p.getActionSender().clearMapFlag();
		Object[] opts = new Object[]{"", "", "", "", "Wear<col=ff9040>", -1, 0, 7, 4, 93, 43909120};
		p.getActionSender().displayInterface(667);
		p.getBonuses().refresh();
		p.getActionSender().displayInventoryInterface(149);
		p.getActionSender().sendClientScript2(172, 149, opts, "IviiiIsssss");
		p.getActionSender().setRightClickOptions(1278, (667 * 65536) + 14, 0, 13);
	}
}
