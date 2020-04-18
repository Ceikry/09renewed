package com.runescape.model.player;

import com.runescape.content.skills.prayer.Prayer;

/**
 * Manages the player's skills.
 * 
 * @author Graham
 * 
 */
public class Skills {

	public static final int SKILL_COUNT = 24;

	private transient Player player;

	public static final double MAXIMUM_EXP = 200000000;

	private int level[] = new int[SKILL_COUNT];
	private double xp[] = new double[SKILL_COUNT];
	private transient int tempHealthLevel;
	
	public Object readResolve() {
		tempHealthLevel = level[3];
		return this;
	}

	public static final String[] SKILL_NAME = { "Attack", "Defence",
			"Strength", "Hitpoints", "Range", "Prayer", "Magic", "Cooking",
			"Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting",
			"Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
			"Farming", "Runecrafting", "Hunter", "Construction", "Summoning", };

	public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
			HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
			WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
			CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
			AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19,
			RUNECRAFTING = 20, HUNTER = 21, CONSTRUCTION = 22, SUMMONING = 23;
	
	public boolean hasMultiple99s() {
		int j = 0;
		for (int i = 0; i < SKILL_COUNT; i++) {
			if (getLevelForXp(i) >= 99) {
				j++;
			}
		}
		return j > 1;
	}

	public Skills() {
		for (int i = 0; i < SKILL_COUNT; i++) {
			level[i] = 1;
			xp[i] = 0;
		}
		level[3] = 10;
		xp[3] = 1184;
	}

	public int getTotalLevel() {
		int total = 0;
		for (int i = 0; i < SKILL_COUNT; i++) {
			total += getLevelForXp(i);
		}
		return total;
	}
	
	public int getTotalXp() {
		int total = 0;
		for (int i = 0; i < SKILL_COUNT; i++) {
			total += xp[i];
		}
		return total;
	}

	public void hit(int hitDiff) {
		level[3] -= hitDiff;
		if (level[3] < 0) {
			level[3] = 0;
		}
		player.getActionSender().sendSkillLevels();
		player.getUpdateFlags().setAppearanceUpdateRequired(true);
	}

	public void heal(int hitDiff) {
		level[3] += hitDiff;
		int max = getLevelForXp(3);
		if (level[3] > max) {
			level[3] = max;
		}
		player.getActionSender().sendSkillLevels();
		player.getUpdateFlags().setAppearanceUpdateRequired(true);
	}

	public void reset() {
		for (int i = 0; i < SKILL_COUNT; i++) {
			level[i] = 1;
			xp[i] = 0;
		}
		level[3] = 10;
		xp[3] = 1184;
		player.getActionSender().sendSkillLevels();
		player.getUpdateFlags().setAppearanceUpdateRequired(true);
	}

	public void forceReset() {
		for (int i = 0; i < SKILL_COUNT; i++) {
			level[i] = 1;
			xp[i] = 0;
		}
		player.getActionSender().sendSkillLevels();
		player.getUpdateFlags().setAppearanceUpdateRequired(true);
	}

	public int getCombatLevel() {
		int attack = getLevelForXp(0);
		int defence = getLevelForXp(1);
		int strength = getLevelForXp(2);
		int hp = getLevelForXp(3);
		int prayer = getLevelForXp(5);
		int ranged = getLevelForXp(4);
		int magic = getLevelForXp(6);
		int combatLevel = 3;
		combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.25) + 1;
		double melee = (attack + strength) * 0.325;
		double ranger = Math.floor(ranged * 1.5) * 0.325;
		double mage = Math.floor(magic * 1.5) * 0.325;
		if (melee >= ranger && melee >= mage) {
			combatLevel += melee;
		} else if (ranger >= melee && ranger >= mage) {
			combatLevel += ranger;
		} else if (mage >= melee && mage >= ranger) {
			combatLevel += mage;
		}
		int summoning = getLevelForXp(Skills.SUMMONING);
		summoning /= 8;
		return combatLevel + summoning;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getLevel(int skill) {
		return level[skill];
	}

	public double getXp(int skill) {
		return xp[skill];
	}
	
	public void setXp(int skill, int newXp) {
		xp[skill] = newXp;
	}

	public int getLevelForXp(int skill) {
		double exp = xp[skill];
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl < 100; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= exp) {
				return lvl;
			}
		}
		return 99;
	}
	
	public int getXpForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int)Math.floor(points / 4);
		}
		return 0;
	}

	/*public void addXp(int skill, double exp) {
		exp *= player.getSettings().getXpRate();
		int oldLevel = getLevelForXp(skill);
		int oldCombat = getCombatLevel();
		xp[skill] += exp;
		if (xp[skill] > MAXIMUM_EXP) {
			xp[skill] = MAXIMUM_EXP;
		}
		int newLevel = getLevelForXp(skill);
		int levelDiff = newLevel - oldLevel;
		if (newLevel > oldLevel) {
			level[skill] += levelDiff;
			int newCombat = getCombatLevel();
			if (newCombat - oldCombat > 0) {
				player.getLevelUp().setCombatMilestone(newCombat);
			}
			player.getLevelUp().levelUp(player, skill);
		}
		player.getActionSender().sendSkillLevel(skill);
		player.getUpdateFlags().setAppearanceUpdateRequired(true);
	}*/
	
	public void addXp(int skill, double exp) {
		int oldLevel = getLevelForXp(skill);
		xp[skill] += exp * 10; //xp rate for all skills (make * 20 for doubleXP weekends)
		if(xp[skill] >= MAXIMUM_EXP) {
			xp[skill] = MAXIMUM_EXP;
		}
		int newLevel = getLevelForXp(skill);
		if(newLevel > oldLevel && newLevel <= 99) {
			if (skill != 3) {
				level[skill] = newLevel;
			} else {
				level[3]++;
				tempHealthLevel = level[3];
			}
			player.getLevelUp().levelUp(player, skill);
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
		}
		player.getActionSender().sendSkillLevel(skill);
	}
	
	public void setLevel(int skill, int lvl) {
		level[skill] = lvl;
		if (level[skill] <= 1 && skill != 3 && skill != 5) {
			level[skill] = 1;
		}
		if (skill == 5) {
			player.getSettings().setPrayerPoints((int) lvl);
			if (level[5] <= 0 || lvl == 0) {
				level[5] = 0;
				player.getActionSender().sendMessage("You have run out of Prayer points, please recharge your prayer at an altar.");
				Prayer.deactivateAllPrayers(player);
			}
		} else if (skill == 3) {
			tempHealthLevel = lvl;
		}
	}
	
	public void setTempHealth(int i) {
		this.tempHealthLevel = i;
	}
	
	public int getTempHealthLevel() {
		return tempHealthLevel;
	}

	public static int getMenuId(int buttonId) {
		switch (buttonId) {
		case 0:
			return 1;
		case 1:
			return 2;
		case 2:
			return 5;
		case 3:
			return 3;
		case 4:
			return 7;
		case 5:
			return 4;
		case 6:
			return 12;
		case 7:
			return 22;
		case 8:
			return 6;
		case 9:
			return 8;
		case 10:
			return 9;
		case 11:
			return 10;
		case 12:
			return 11;
		case 13:
			return 19;
		case 14:
			return 20;
		case 15:
			return 23;
		case 16:
			return 13;
		case 17:
			return 14;
		case 18:
			return 15;
		case 19:
			return 16;
		case 20:
			return 17;
		case 21:
			return 18;
		case 22:
			return 21;
		case 23:
			return 24;
		}
		return 0;
	}

	public static int getSkillId(int menuId) {
		switch (menuId) {
		case 1:
			return Skills.ATTACK;
		case 2:
			return Skills.STRENGTH;
		case 3:
			return Skills.RANGE;
		case 4:
			return Skills.MAGIC;
		case 5:
			return Skills.DEFENCE;
		case 6:
			return Skills.HITPOINTS;
		case 7:
			return Skills.PRAYER;
		case 8:
			return Skills.AGILITY;
		case 9:
			return Skills.HERBLORE;
		case 10:
			return Skills.THIEVING;
		case 11:
			return Skills.CRAFTING;
		case 12:
			return Skills.RUNECRAFTING;
		case 13:
			return Skills.MINING;
		case 14:
			return Skills.SMITHING;
		case 15:
			return Skills.FISHING;
		case 16:
			return Skills.COOKING;
		case 17:
			return Skills.FIREMAKING;
		case 18:
			return Skills.WOODCUTTING;
		case 19:
			return Skills.FLETCHING;
		case 20:
			return Skills.SLAYER;
		case 21:
			return Skills.FARMING;
		case 22:
			return Skills.CONSTRUCTION;
		case 23:
			return Skills.HUNTER;
		case 24:
			return Skills.SUMMONING;
		}
		return menuId;
	}
}

/*import com.able.content.LevelUp;
import com.able.content.skills.prayer.Prayer;

/**
 * Manages the player's skills.
 * @author Graham
 *
 */
/*public class Skills {
	
	public static final int SKILL_COUNT = 24;
	
    public static final String[] SKILL_NAME = {
        "Attack", "Defence", "Strength", "Hitpoints", "Range", "Prayer",
        "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking",
        "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
        "Farming", "Runecrafting", "Construction", "Hunter", "Summoning",
    };

    public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2, HITPOINTS = 3, RANGE = 4, PRAYER = 5,
        MAGIC = 6, COOKING = 7, WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
        CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15, AGILITY = 16, THIEVING = 17, SLAYER = 18,
        FARMING = 19, RUNECRAFTING = 20, CONSTRUCTION = 21, HUNTER = 22, SUMMONING = 23;
	
	private transient Player player;
	
	public static final double MAXIMUM_EXP = 200000000;
	
	private int level[] = new int[SKILL_COUNT];
	private double xp[] = new double[SKILL_COUNT];
	private transient int tempHealthLevel;
	
	public Skills() {
		for(int i = 0; i < SKILL_COUNT; i++) {
			level[i] = 1;
			xp[i] = 0;
		}
		level[3] = 10;
		xp[3] = 1184;
	}
	
	public Object readResolve() {
		tempHealthLevel = level[3];
		return this;
	}
	
	public int getCombatLevel() {
		int attack = getLevelForXp(0);
		int defence = getLevelForXp(1);
		int strength = getLevelForXp(2);
		int hp = getLevelForXp(3);
		int prayer = getLevelForXp(5);
		int ranged = getLevelForXp(4);
		int magic = getLevelForXp(6);
		int summoning = getLevelForXp(23);
		
		double combatLevel = (defence + hp + Math.floor(prayer / 2) + Math.floor(summoning / 2)) * 0.25;
		double warrior = (attack + strength) * 0.325;
        double ranger = ranged * 0.4875;
        double mage = magic * 0.4875;

		return (int) (combatLevel + Math.max(warrior, Math.max(ranger, mage)));
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getLevel(int skill) {
		return level[skill];
	}
	
	public double getXp(int skill) {
		return xp[skill];
	}
	
	public void setLevel(int skill, int lvl) {
		level[skill] = lvl;
		if (level[skill] <= 1 && skill != 3 && skill != 5) {
			level[skill] = 1;
		}
		if (skill == 5) {
			player.getSettings().setPrayerPoints((int) lvl);
			if (level[5] <= 0 || lvl == 0) {
				level[5] = 0;
				player.getActionSender().sendMessage("You have run out of Prayer points, please recharge your prayer at an altar.");
				Prayer.deactivateAllPrayers(player);
			}
		} else if (skill == 3) {
			tempHealthLevel = lvl;
		}
	}
	
	public void setXp(int skill, int newXp) {
		xp[skill] = newXp;
	}
	
	public int getLevelForXp(int skill) {
		double exp = xp[skill];
		int points = 0;
		int output = 0;
		for(int lvl = 1; lvl < 100; lvl++) {
			points += Math.floor((double)lvl + 300.0 * Math.pow(2.0, (double)lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if((output - 1) >= exp) {
				return lvl;
			}
		}
		return 99;
	}
	
	public int getXpForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int)Math.floor(points / 4);
		}
		return 0;
	}
	
	public void addXp(int skill, double exp) {
		int oldLevel = getLevelForXp(skill);
		xp[skill] += exp * 10; //xp rate for all skills (make * 20 for doubleXP weekends)
		if(xp[skill] >= MAXIMUM_EXP) {
			xp[skill] = MAXIMUM_EXP;
		}
		int newLevel = getLevelForXp(skill);
		if(newLevel > oldLevel && newLevel <= 99) {
			if (skill != 3) {
				level[skill] = newLevel;
			} else {
				level[3]++;
				tempHealthLevel = level[3];
			}
			player.getLevelUp().levelUp(player, skill);
			player.getUpdateFlags().setAppearanceUpdateRequired(true);
		}
		player.getActionSender().sendSkillLevel(skill);
	}

	public boolean hasMultiple99s() {
		int j = 0;
		for (int i = 0; i < SKILL_COUNT; i++) {
			if (getLevelForXp(i) >= 99) {
				j++;
			}
		}
		return j > 1;
	}
	
	public int getTotalXp() {
		int total = 0;
		for (int i = 0; i < SKILL_COUNT; i++) {
			total += xp[i];
		}
		return total;
	}

	public int getTotalLevel() {
		int total = 0;
		for (int i = 0; i < SKILL_COUNT; i++) {
			total += getLevelForXp(i);
		}
		return total;
	}

	public int getTempHealthLevel() {
		return tempHealthLevel;
	}

	public void setTempHealth(int i) {
		this.tempHealthLevel = i;
	}
}*/
