package com.runescape.content;

import com.runescape.model.masks.Appearance;
import com.runescape.model.player.Player;
import com.runescape.util.Misc;

/**
 * @author Nando
 * @author Abexlry
 * 
 */
public class CharacterDesign {

	public static final int START_UP_INTERFACE = 771;
	
	public static void open(Player player) {
		player.getActionSender().displayInterface(START_UP_INTERFACE);
		player.getActionSender().sendPlayerHead(START_UP_INTERFACE, 79);
		player.getActionSender().animateInterface(9805, START_UP_INTERFACE, 79);
		player.getActionSender().sendConfig(START_UP_INTERFACE, 92, false);//back - hair (problem with them..)
		player.getActionSender().sendConfig(START_UP_INTERFACE, 97, false);//back - beards (problem with them..)
		//player.getAppearance().setGender(Appearance.GENDER_MALE);
		player.getAppearance().getGender();
		player.getAppearance().toDefault();
		player.getActionSender().sendConfig(1262, 1);
		player.getUpdateFlags().setAppearanceUpdateRequired(true);
	}
	
	public static void handleButtons(Player player, int buttonId) {
		int index = -1;
		int color = -1;
		if(buttonId >= 307 && buttonId <= 312) {
			index = Appearance.BOOT_COLOR_INDEX;
			color = Appearance.BOOT_COLORS[buttonId - 307];
		}
		if(buttonId >= 151 && buttonId <= 158) {
			index = Appearance.SKIN_COLOR_INDEX;
			color = Appearance.SKIN_COLORS[buttonId - 151];
		}
		if(buttonId >= 100 && buttonId <= 124) {
			index = Appearance.HAIR_COLOR_INDEX;
			color = Appearance.HAIR_COLORS[buttonId - 100];
		}
		if(buttonId >= 189 && buttonId <= 217) {
			index = Appearance.TOP_COLOR_INDEX;
			color = Appearance.TOP_COLORS[buttonId - 189];
		}
		if(buttonId >= 249 && buttonId <= 276) {
			index = Appearance.LEG_COLOR_INDEX;
			color = Appearance.LEG_COLORS[buttonId - 249];
		}
		if(index != -1 && color != -1) {
			player.getAppearance().colour[index] = color;
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
		}
		switch(buttonId) {
		case 357://Previous feet
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextFeet = -1;
				int feetIndex = player.getAppearance().getFeetIndex();
				if (feetIndex == 42) {
					feetIndex = 43;
				} else {
					feetIndex -= 1;
				}
				nextFeet = Appearance.MALE_FEET_STYLES[feetIndex];
				player.getAppearance().setFeetIndex(feetIndex);
				player.getAppearance().setLook(6, nextFeet);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextFeet = -1;
				feetIndex = player.getAppearance().getFeetIndex();
				if (feetIndex == 80) {
					feetIndex = 81;
				} else {
					feetIndex -= 1;
				}
				nextFeet = Appearance.FEMALE_FEET_STYLES[feetIndex];
				player.getAppearance().setFeetIndex(feetIndex);
				player.getAppearance().setLook(6, nextFeet);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 358://Next feet
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextFeet = -1;
				int feetIndex = player.getAppearance().getFeetIndex();
				if (feetIndex == 43) {
					feetIndex = 42;
				} else {
					feetIndex += 1;
				}
				nextFeet = Appearance.MALE_FEET_STYLES[feetIndex];
				player.getAppearance().setFeetIndex(feetIndex);
				player.getAppearance().setLook(6, nextFeet);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextFeet = -1;
				feetIndex = player.getAppearance().getFeetIndex();
				if (feetIndex == 81) {
					feetIndex = 80;
				} else {
					feetIndex += 1;
				}
				nextFeet = Appearance.FEMALE_FEET_STYLES[feetIndex];
				player.getAppearance().setFeetIndex(feetIndex);
				player.getAppearance().setLook(6, nextFeet);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 353://Previous legs
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextLegs = -1;
				int legsIndex = player.getAppearance().getLegsIndex();
				if (legsIndex == 36) {
					legsIndex = 46;
				} else {
					legsIndex -= 1;
				}
				nextLegs = Appearance.MALE_LEGS_STYLES[legsIndex];
				player.getAppearance().setLegsIndex(legsIndex);
				player.getAppearance().setLook(5, nextLegs);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextLegs = -1;
				legsIndex = player.getAppearance().getLegsIndex();
				if (legsIndex == 77) {
					legsIndex = 91;
				} else {
					legsIndex -= 1;
				}
				nextLegs = Appearance.FEMALE_LEGS_STYLES[legsIndex];
				player.getAppearance().setLegsIndex(legsIndex);
				player.getAppearance().setLook(5, nextLegs);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 354://Next legs
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextLegs = -1;
				int legsIndex = player.getAppearance().getLegsIndex();
				if (legsIndex == 45) {
					legsIndex = 36;
				} else {
					legsIndex += 1;
				}
				nextLegs = Appearance.MALE_LEGS_STYLES[legsIndex];
				player.getAppearance().setLegsIndex(legsIndex);
				player.getAppearance().setLook(5, nextLegs);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextLegs = -1;
				legsIndex = player.getAppearance().getLegsIndex();
				if (legsIndex == 91) {
					legsIndex =77;
				} else {
					legsIndex += 1;
				}
				nextLegs = Appearance.FEMALE_LEGS_STYLES[legsIndex];
				player.getAppearance().setLegsIndex(legsIndex);
				player.getAppearance().setLook(5, nextLegs);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 349://Previous wrists
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextWrists = -1;
				int wristsIndex = player.getAppearance().getWristsIndex();
				if (wristsIndex == 33) {
					wristsIndex = 44;
				} else {
					wristsIndex -= 1;
				}
				nextWrists = Appearance.MALE_WRISTS_STYLES[wristsIndex];
				player.getAppearance().setWristsIndex(wristsIndex);
				player.getAppearance().setLook(4, nextWrists);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextWrists = -1;
				wristsIndex = player.getAppearance().getWristsIndex();
				if (wristsIndex == 68) {
					wristsIndex = 79;
				} else {
					wristsIndex -= 1;
				}
				nextWrists = Appearance.FEMALE_WRISTS_STYLES[wristsIndex];
				player.getAppearance().setWristsIndex(wristsIndex);
				player.getAppearance().setLook(4, nextWrists);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 350://Next wrists
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextWrists = -1;
				int wristsIndex = player.getAppearance().getWristsIndex();
				if (wristsIndex == 45) {
					wristsIndex = 33;
				} else {
					wristsIndex += 1;
				}
				nextWrists = Appearance.MALE_WRISTS_STYLES[wristsIndex];
				player.getAppearance().setWristsIndex(wristsIndex);
				player.getAppearance().setLook(4, nextWrists);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextWrists = -1;
				wristsIndex = player.getAppearance().getWristsIndex();
				if (wristsIndex == 80) {
					wristsIndex = 68;
				} else {
					wristsIndex += 1;
				}
				nextWrists = Appearance.FEMALE_WRISTS_STYLES[wristsIndex];
				player.getAppearance().setWristsIndex(wristsIndex);
				player.getAppearance().setLook(4, nextWrists);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 345://Before arms
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextArms = -1;
				int armsIndex = player.getAppearance().getArmsIndex();
				if (armsIndex == 26) {
					armsIndex = 37;
				} else {
					armsIndex -= 1;
				}
				nextArms = Appearance.MALE_ARMS_STYLES[armsIndex];
				player.getAppearance().setArmsIndex(armsIndex);
				player.getAppearance().setLook(3, nextArms);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				armsIndex = player.getAppearance().getArmsIndex();
				nextArms = -1;
				if (armsIndex == 64) {
					armsIndex = 74;
				} else {
					armsIndex -= 1;
				}
				nextArms = Appearance.FEMALE_ARMS_STYLES[armsIndex];
				player.getAppearance().setArmsIndex(armsIndex);
				player.getAppearance().setLook(3, nextArms);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 346://Next arms
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextArms = -1;
				int armsIndex = player.getAppearance().getArmsIndex();
				if (armsIndex == 37) {
					armsIndex = 26;
				} else {
					armsIndex += 1;
				}
				nextArms = Appearance.MALE_ARMS_STYLES[armsIndex];
				player.getAppearance().setArmsIndex(armsIndex);
				player.getAppearance().setLook(3, nextArms);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				armsIndex = player.getAppearance().getArmsIndex();
				nextArms = -1;
				if (armsIndex == 74) {
					armsIndex = 64;
				} else {
					armsIndex += 1;
				}
				nextArms = Appearance.FEMALE_ARMS_STYLES[armsIndex];
				player.getAppearance().setArmsIndex(armsIndex);
				player.getAppearance().setLook(3, nextArms);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 342://Next torso
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextChest = -1;
				int chestIndex = player.getAppearance().getTorsoIndex();
				if (chestIndex == 31) {
					chestIndex = 18;
				} else {
					chestIndex += 1;
				}
				nextChest = Appearance.MALE_TORSO_STYLES[chestIndex];
				player.getAppearance().setTorsoIndex(chestIndex);
				player.getAppearance().setLook(2, nextChest);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				chestIndex = player.getAppearance().getTorsoIndex();
				if (chestIndex == 67) {
					chestIndex = 57;
				} else {
					chestIndex += 1;
				}
				nextChest = Appearance.FEMALE_TORSO_STYLES[chestIndex];
				player.getAppearance().setTorsoIndex(chestIndex);
				player.getAppearance().setLook(2, nextChest);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 341://Previous torso
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextChest = -1;
				int chestIndex = player.getAppearance().getTorsoIndex();
				if (chestIndex == 18) {
					chestIndex = 31;
				} else {
					chestIndex -= 1;
				}
				nextChest = Appearance.MALE_TORSO_STYLES[chestIndex];
				player.getAppearance().setTorsoIndex(chestIndex);
				player.getAppearance().setLook(2, nextChest);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				chestIndex = player.getAppearance().getTorsoIndex();
				if (chestIndex == 57) {
					chestIndex = 67;
				} else {
					chestIndex -= 1;
				}
				nextChest = Appearance.FEMALE_TORSO_STYLES[chestIndex];
				player.getAppearance().setTorsoIndex(chestIndex);
				player.getAppearance().setLook(2, nextChest);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 93://Next hair
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextHair = -1;
				int hairIndex = player.getAppearance().getHairIndex();
				if (hairIndex == 23) {
					hairIndex = 0;
				} else {
					hairIndex += 1;
				}
				nextHair = Appearance.MALE_HAIR_STYLES[hairIndex];
				player.getAppearance().setHairIndex(hairIndex);
				player.getAppearance().setLook(0, nextHair);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextHair = -1;
				hairIndex = player.getAppearance().getHairIndex();
				if (hairIndex == 81) {
					hairIndex = 48;
				} else {
					hairIndex += 1;
				}
				nextHair = Appearance.FEMALE_HAIR_STYLES[hairIndex];
				player.getAppearance().setHairIndex(hairIndex);
				player.getAppearance().setLook(0, nextHair);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 98://Next beard
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextBeard = -1;
				int beardIndex = player.getAppearance().getBeardIndex();
				if (beardIndex == 18) {
					beardIndex = 0;
				} else {
					beardIndex += 1;
				}
				nextBeard = Appearance.MALE_FACIAL_HAIR_STYLES[beardIndex];
				player.getAppearance().setBeardIndex(beardIndex);
				player.getAppearance().setLook(1, nextBeard);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 97://Previous beard
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextBeard = -1;
				int beardIndex = player.getAppearance().getBeardIndex();
				if (beardIndex == 0) {
					beardIndex = Appearance.MALE_FACIAL_HAIR_STYLES.length - 1;
				} else {
					beardIndex -= 1;
				}
				nextBeard = Appearance.MALE_FACIAL_HAIR_STYLES[beardIndex];
				player.getAppearance().setBeardIndex(beardIndex);
				player.getAppearance().setLook(1, nextBeard);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 92://Previous hair
			switch(player.getAppearance().getGender()) {
			case Appearance.GENDER_MALE:
				int nextHair = -1;
				int hairIndex = player.getAppearance().getHairIndex();
				if (hairIndex == 0) {
					hairIndex = Appearance.MALE_HAIR_STYLES.length - 1;
				} else {
					hairIndex -= 1;
				}
				nextHair = Appearance.MALE_HAIR_STYLES[hairIndex];
				player.getAppearance().setHairIndex(hairIndex);
				player.getAppearance().setLook(0, nextHair);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			case Appearance.GENDER_FEMALE:
				nextHair = -1;
				hairIndex = player.getAppearance().getHairIndex();
				if (hairIndex == 48) {
					hairIndex = 81;
				} else {
					hairIndex -= 1;
				}
				nextHair = Appearance.FEMALE_HAIR_STYLES[hairIndex];
				player.getAppearance().setHairIndex(hairIndex);
				player.getAppearance().setLook(0, nextHair);
				player.getUpdateFlags().setAppearanceUpdateRequired(true);
				break;
			}
			break;
		case 49://male
			player.getAppearance().setGender(Appearance.GENDER_MALE);
			player.getAppearance().toDefault();
			player.getActionSender().sendConfig(1262, 1);
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
			break;
		case 52://female
			player.getAppearance().setGender(Appearance.GENDER_FEMALE);
			player.getAppearance().toDefault();
			player.getActionSender().sendConfig(1262, -1);
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
			System.out.println(player.getAppearance().getGender());
			System.out.println(player.getAppearance().getArmsIndex());
			System.out.println(player.getAppearance().getTorsoIndex());
			break;
		case 40: // mouse
			player.getSettings().setMouseTwoButtons(false);
			break;
		case 37: // mouse
			player.getSettings().setMouseTwoButtons(true);
			break;
		case 16:
			//for(int i = 100; i < 300; i++)
				//player.getPackets().sendInterfaceConfig(START_UP_INTERFACE, i, false);
			break;
		case 321://Random
			player.getAppearance().colour[Appearance.TOP_COLOR_INDEX] = Appearance.TOP_COLORS[Misc.random(Appearance.TOP_COLORS.length - 1)];
			player.getAppearance().colour[Appearance.LEG_COLOR_INDEX] = Appearance.LEG_COLORS[Misc.random(Appearance.LEG_COLORS.length - 1)];
			player.getAppearance().colour[Appearance.BOOT_COLOR_INDEX] = Appearance.BOOT_COLORS[Misc.random(Appearance.BOOT_COLORS.length - 1)];
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
			break;
		case 319:
			for(int i = 360; i < 365; i++)
				player.getActionSender().sendConfig(START_UP_INTERFACE, i, true);
			break;
		case 362:
			player.getActionSender().closeInterfaces();
			if (player.firstCustomization) {
				player.setCantMove(false);
				player.firstCustomization = false;
			}
			break;
		}
	}
}
