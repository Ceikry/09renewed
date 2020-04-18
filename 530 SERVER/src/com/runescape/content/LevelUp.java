package com.runescape.content;

import com.runescape.model.player.Player;
import com.runescape.model.player.Skills;
import com.runescape.util.Misc;

	/**
	 * Level up class.
	 * 
	 * @author Graham
	 * 
	 */
	public class LevelUp {

		public static int[] FLASH_ORDER = new int[] { Skills.ATTACK,
				Skills.STRENGTH, Skills.DEFENCE, Skills.RANGE, Skills.PRAYER,
				Skills.MAGIC, Skills.HITPOINTS, Skills.AGILITY, Skills.HERBLORE,
				Skills.THIEVING, Skills.CRAFTING, Skills.FLETCHING, Skills.MINING,
				Skills.SMITHING, Skills.FISHING, Skills.COOKING, Skills.FIREMAKING,
				Skills.WOODCUTTING, Skills.RUNECRAFTING, Skills.SLAYER,
				Skills.FARMING, Skills.CONSTRUCTION, Skills.HUNTER,
				Skills.SUMMONING };

		public static int[] ICON_ORDER = { Skills.ATTACK, Skills.STRENGTH,
				Skills.RANGE, Skills.MAGIC, Skills.DEFENCE, Skills.HITPOINTS,
				Skills.PRAYER, Skills.AGILITY, Skills.HERBLORE, Skills.THIEVING,
				Skills.CRAFTING, Skills.RUNECRAFTING, Skills.MINING,
				Skills.SMITHING, Skills.FISHING, Skills.COOKING, Skills.FIREMAKING,
				Skills.WOODCUTTING, Skills.FLETCHING, Skills.SLAYER,
				Skills.FARMING, Skills.CONSTRUCTION, Skills.HUNTER,
				Skills.SUMMONING };

		public static final int[] SKILL_ICON = new int[Skills.SKILL_COUNT];
		public static final int[] SKILL_FLASH = new int[Skills.SKILL_COUNT];
		public static final int[] SKILL_ADVANCE = new int[Skills.SKILL_COUNT];

		private static final int[] COMBAT_MILESTONES = { 5, 10, 15, 25, 50, 75, 90,
				100, 110, 120, 126, 130, 138 };

		private static final int[] TOTAL_MILESTONES = { 50, 75, 100, 200, 300, 400,
				500, 600, 700, 800, 900, 1000, 1100, 1200, 1300 };

		private transient final boolean leveled[] = new boolean[Skills.SKILL_COUNT];

		private transient int combatMilestone;

		private transient int totalMilestone;

		static {
			for (int i = 0; i < Skills.SKILL_COUNT; i++) {
				int slot = FLASH_ORDER[i];
				SKILL_FLASH[slot] = 1 << i;
				slot = ICON_ORDER[i];
				SKILL_ICON[slot] = (i + 1) << 26;
				SKILL_ADVANCE[slot] = (i + 1) << 3;
			}
		}

		public int getConfigHash(int latestLevelUp) {
			int configHash = 0;
			if (latestLevelUp != -1) {
				configHash = SKILL_ICON[latestLevelUp];
			}
			for (int i = 0; i < Skills.SKILL_COUNT; i++) {
				configHash |= leveled[i] ? SKILL_FLASH[i] : 0;
			}
			return configHash;
		}

		/**
		 * Called when a player levels up.
		 * 
		 * @param player
		 *            The player.
		 * @param skill
		 *            The skill id.
		 */
		public void levelUp(Player player, int skill) {
			leveled[skill] = true;
			String s = "<br><br><br>";
			String s1 = "<br><br><br><br>";
			player.graphics(199, 100);player.getActionSender().sendMessage("You've just advanced a " + Misc.SKILL_NAME[skill] + " level! You have reached level " + player.getLevels().getLevelForXp(skill) + ".");
			player.getActionSender().modifyText(s + "Congratulations, you have just advanced a " + Misc.SKILL_NAME[skill] + " level!", 740, 0);
			player.getActionSender().modifyText(s1 + "You have now reached level " + player.getLevels().getLevelForXp(skill) + ".", 740, 1);
			player.getActionSender().modifyText("", 740, 2);
			checkTotal(player);
			player.getActionSender().sendConfig(1179, getConfigHash(skill));
			player.getActionSender().sendChatboxInterface(740);
			if (skill == 5) {
				player.getSettings().increasePrayerPoints(+1);
				player.getActionSender().sendSkillLevel(5);
		}
		}

		private void checkTotal(Player player) {
			int totalLevel = player.getLevels().getTotalLevel();
			for (int i = 0; i < TOTAL_MILESTONES.length; i++) {
				if (TOTAL_MILESTONES[i] == totalLevel) {
					totalMilestone = i + 1;
					break;
				} else {
					totalMilestone = -1;
				}
			}
		}

		public boolean didLevel(int skillId) {
			return leveled[skillId];
		}

		public int getLevelUpInterfaceConfig(int skillId) {
			int config = SKILL_ADVANCE[skillId];
			if (combatMilestone != -1 && combatSkill(skillId)) {
				config |= 2;
				config |= (combatMilestone << 23);
				combatMilestone = -1;
			}
			if (totalMilestone != -1) {
				config |= 4;
				config |= (totalMilestone << 27);
				totalMilestone = -1;
			}
			return config;
		}

		private boolean combatSkill(int skillId) {
			switch (skillId) {
			case Skills.ATTACK:
			case Skills.DEFENCE:
			case Skills.STRENGTH:
			case Skills.HITPOINTS:
			case Skills.MAGIC:
			case Skills.RANGE:
			case Skills.SUMMONING:
			case Skills.PRAYER:
				return true;
			}
			return false;
		}

		public void setDidLevel(int skillId, boolean didLevel) {
			leveled[skillId] = didLevel;
		}

		public void setCombatMilestone(int combatLevel) {
			for (int i = 0; i < COMBAT_MILESTONES.length; i++) {
				if (combatLevel == COMBAT_MILESTONES[i]) {
					combatMilestone = i + 1;
					break;
				} else {
					combatMilestone = -1;
				}
			}
		}
	}
	
	/*public static final int[] SKILL_ICON = {
		100000000, 400000000, 200000000, 450000000, 250000000, 500000000,
		300000000, 1100000000, 1250000000, 1300000000, 1050000000, 1200000000,
		800000000, 1000000000, 900000000, 650000000, 600000000, 700000000,
		1400000000, 1450000000, 850000000, 1500000000, 1600000000, 1650000000, 0,
	};
	
	public static final int[] SKILL_FLASH = {
		1, 4, 2, 64, 8, 16, 32, 32768, 131072, 2048, 16384, 65536, 1024, 8192, 4096, 256, 128,
		512, 524288, 1048576, 262144, 2097152, 4194304, 8388608, 0,
	};
	
	public static void levelUp(Player player, int skill) {
		String s = "<br><br><br>";
		String s1 = "<br><br><br><br>";
        player.setTemporaryAttribute("leveledUp", skill);
        player.setTemporaryAttribute("leveledUp["+skill+"]", Boolean.TRUE);
        if (player.getTemporaryAttribute("LEVEL_SKILL") != null) {
        player.setTemporaryAttribute("LEVEL_SKILL", SKILL_FLASH[skill]+
                (Integer)player.getTemporaryAttribute("LEVEL_SKILL"));
        } else {
            player.setTemporaryAttribute("LEVEL_SKILL", SKILL_FLASH[skill]);
        }
        player.graphics(199, 100);
		player.getActionSender().sendMessage("You've just advanced a " + Misc.SKILL_NAME[skill] + " level! You have reached level " + player.getLevels().getLevelForXp(skill) + ".");
		player.getActionSender().modifyText(s + "Congratulations, you have just advanced a " + Misc.SKILL_NAME[skill] + " level!", 740, 0);
		player.getActionSender().modifyText(s1 + "You have now reached level " + player.getLevels().getLevelForXp(skill) + ".", 740, 1);
        player.getActionSender().sendConfig(1179,
                (Integer) player.getTemporaryAttribute("LEVEL_SKILL") | SKILL_ICON[skill]);
        player.getActionSender().sendChatboxInterface4(740);     
		if (skill == 5) {
			player.getSettings().increasePrayerPoints(+1);
			player.getActionSender().sendSkillLevel(5);
		}
    }*/

	/*public static void levelUp(Player player, int skill) {
		String s = "<br><br><br>";
		String s1 = "<br><br><br><br>";
		if (player.getTemporaryAttribute("teleporting") == null) {
			player.graphics(199, 0, 100);
		}
		player.getActionSender().sendMessage("You've just advanced a " + Misc.SKILL_NAME[skill] + " level! You have reached level " + player.getLevels().getLevelForXp(skill) + ".");
		player.getActionSender().modifyText(s + "Congratulations, you have just advanced a " + Misc.SKILL_NAME[skill] + " level!", 740, 0);
		player.getActionSender().modifyText(s1 + "You have now reached level " + player.getLevels().getLevelForXp(skill) + ".", 740, 1);
		player.getActionSender().modifyText("", 740, 2);
		player.getActionSender().sendConfig2(1179, SKILL_ICON[skill]);
		player.getActionSender().sendChatboxInterface2(740);
			if (skill == 5) {
			player.getSettings().increasePrayerPoints(+1);
			player.getActionSender().sendSkillLevel(5);
		}

	}*/

