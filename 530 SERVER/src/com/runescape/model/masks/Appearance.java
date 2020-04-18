package com.runescape.model.masks;

/**
 * Appearance class
 * @author Graham
 * @author Nando
 * @author Abexlry
 *
 */
public class Appearance {
	
	private transient boolean invisible = false;
	private transient Appearance temporaryAppearance; // Used when designing character.
	private transient int walkAnimation;
	private boolean asNpc   = false;
	private int     npcId   = -1;
	private int     gender = 0;
	private int[]   look    = new int[7];
	public int[]   colour  = new int[5];
	
	private int		hair = 0;
	private int		beard = 0;
	private int		legs = 0;
	private int		wrists = 0;
	private int		arms = 0;
	private int		torso = 0;
	private int		feet = 0;
	
	public Appearance() {
		look[1] = 10;
		look[2] = 18;
		look[3] = 26;
		look[4] = 33;
		look[5] = 36;
		look[6] = 42;
		walkAnimation = -1;
		for(int i = 0; i < 5; i++) {
			colour[i] = i*3+2;
		}
	}
	
	
	
	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setLook(int index, int look) {
		this.look[index] = look;
	}

	public void setColour(int index, int colour) {
		this.colour[index] = colour;
	}

	public boolean isNpc() {
		return asNpc;
	}
	
	public int getNpcId() {
		return npcId;
	}
	
	public int getGender() {
		return gender;
	}
	
	public void setNpcId(int i) {
		npcId = i;
		asNpc = i != -1;
	}
	
	public int getLook(int id) {
		return look[id];
	}
	
	public int getColour(int id) {
		return colour[id];
	}
	
	public int[] getColoursArray() {
		return colour.clone();
	}
	
	public int[] getLookArray() {
		return look.clone();
	}

	public void setTemporaryAppearance(Appearance temporaryAppearance) {
		this.temporaryAppearance = temporaryAppearance;
	}

	public Appearance getTemporaryAppearance() {
		return temporaryAppearance;
	}

	public void setColoursArray(int[] colours) {
		this.colour = colours;
	}

	public void setLookArray(int[] look) {
		this.look = look;
	}

	public void setWalkAnimation(int walkAnimation) {
		this.walkAnimation = walkAnimation;
	}

	public int getWalkAnimation() {
		return walkAnimation;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public boolean isInvisible() {
		return invisible;
	}
	
	public static final int HAIR_COLOR_INDEX = 0;
	public static final int TOP_COLOR_INDEX = 1;
	public static final int LEG_COLOR_INDEX = 2;
	public static final int BOOT_COLOR_INDEX = 3;
	public static final int SKIN_COLOR_INDEX = 4;
	
	public static final int GENDER_FEMALE = 1;
	public static final int GENDER_MALE = 0;
	
	public final static int[] SKIN_COLORS = {
		0, 1, 2, 3, 4, 5, 6, 7
	};
	public final static int[] BOOT_COLORS = {
		6, 1, 2, 3, 4, 5
	};
	
	public final static int[] HAIR_COLORS = {
		20/*burgundy*/,    19/*red*/,   10/*vermillion*/, 18/*pink*/,      4/*orange*/,
		5/*yello*/ ,       15/*peach*/, 7/*brown*/,       0/*dark brown*/, 6/*light brown*/,
		21/*mint green*/,  9/*green*/,  22/*dark green*/, 17/*dark blue*/, 8/*turquoise*/,
		16/*cyan*/,        11/*purple*/,24 /*viloet*/,    23/*indigo*/,    3/*dark grey*/,
		2/*military gray*/,1/*white*/,  14/*light gray*/, 13/*taupe*/,     12/*black*/,
	};
	
	public final static int[] TOP_COLORS = {
		24, 23, 2, 22, 12, 11, 6, 19, 4, 0, 
		9, 13, 25, 8, 15, 26, 21, 7, 20, 14, 
		10, 28, 27, 3, 5, 18, 17, 1, 16
	};
	
	public final static int[] LEG_COLORS = {
		24, 23, 3, 22, 13, 12, 7, 19, 5, 1, 
		10, 14, 25, 9, 0, 21, 8, 20, 15, 11, 
		28, 27, 4, 6, 18, 17, 2, 16
	};
	
	/*
	 * Start of males
	 */
	public final static int[] MALE_HAIR_STYLES = {
		265, 266, 267, 268, 0, 1, 2, 3, 4, 5, 
		6, 7, 8, 91, 92, 93, 94, 95, 96, 97, 
		261, 262, 263, 264, 
	};
	
	public final static int[] MALE_FACIAL_HAIR_STYLES = {
		99, 100, 101, 102, 103, 104, 305, 306, 307, 308, 
		10, 11, 12, 13, 14, 15, 16, 17, 98, 
	};
	
	public final static int[] MALE_TORSO_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, 18, 19, 
		20, 21, 22, 23, 24, 25, 111, 112, 113, 114, 
		115, 116
	};
	
	public final static int[] MALE_ARMS_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 
		30, 31, 105, 106, 107, 108, 109, 110
	};
	
	public final static int[] MALE_WRISTS_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, 33, 34, 84, 117, 118, 119, 120, 
		121, 122, 123, 124, 125, 126
	};
	
	public final static int[] MALE_LEGS_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, 36, 37, 38, 39, 
		40, 85, 86, 87, 88, 89, 90

	};
	
	public final static int[] MALE_FEET_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, 42, 43
	};
	
	/*
	 * Start of females
	 */
	public final static int[] FEMALE_HAIR_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, 45, 46, 
		47, 48, 49, 50, 51, 52, 53, 54, 135, 136, 
		137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 
		269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 
		279, 280
	};
	
	public final static int[] FEMALE_TORSO_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, 56, 57, 58, 
		59, 60, 153, 154, 155, 156, 157, 158
	};
	
	public final static int[] FEMALE_ARMS_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, 61, 62, 63, 64, 65, 147, 
		148, 149, 150, 151, 152
	};
	
	public final static int[] FEMALE_WRISTS_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, 67, 68, 
		127, 159, 160, 161, 162, 163, 164, 165, 166, 167, 
		168
	};
	
	public final static int[] FEMALE_LEGS_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, 70, 71, 72, 
		73, 74, 75, 76, 77, 128, 129, 130, 131, 132, 
		133, 134
	};
	
	public final static int[] FEMALE_FEET_STYLES = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		79, 80
	};

	public void setHairIndex(int hairIndex) {
		this.hair = hairIndex;
	}

	public int getHairIndex() {
		return hair;
	}
	
	public void setBeardIndex(int beardIndex) {
		this.beard = beardIndex;
	}

	public int getBeardIndex() {
		return beard;
	}
	
	public void setLegsIndex(int legsIndex) {
		this.legs = legsIndex;
	}

	public int getLegsIndex() {
		return legs;
	}
	
	public void setWristsIndex(int wristsIndex) {
		this.wrists = wristsIndex;
	}

	public int getWristsIndex() {
		return wrists;
	}
	
	public void setArmsIndex(int armsIndex) {
		this.arms = armsIndex;
	}

	public int getArmsIndex() {
		return arms;
	}
	
	public void setTorsoIndex(int torsoIndex) {
		this.torso = torsoIndex;
	}

	public int getTorsoIndex() {
		return torso;
	}
	
	public void setFeetIndex(int feetIndex) {
		this.feet = feetIndex;
	}

	public int getFeetIndex() {
		return feet;
	}
	
	public void toDefault() {
		if (gender == 1) {//female
			setHairIndex(48);
			setBeardIndex(1000);
			setLegsIndex(77);
			setWristsIndex(68);
			setArmsIndex(64);
			setTorsoIndex(57);
			setFeetIndex(80);
			this.look[0] = 48;
			this.look[1] = 1000;
			this.look[2] = 57;
			this.look[3] = 64;
			this.look[4] = 68;
			this.look[5] = 77;
			this.look[6] = 80;
		}
		if (gender == 0) {//male
			setHairIndex(3);
			setBeardIndex(10);
			setLegsIndex(36);
			setWristsIndex(33);
			setArmsIndex(26);
			setTorsoIndex(18);
			setFeetIndex(42);
			this.look[0] = 3;
			this.look[1] = 10;
			this.look[2] = 18;
			this.look[3] = 26;
			this.look[4] = 33;
			this.look[5] = 36;
			this.look[6] = 42;
		}
	}
}
