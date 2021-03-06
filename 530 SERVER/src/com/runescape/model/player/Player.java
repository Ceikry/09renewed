package com.runescape.model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.mina.common.IoSession;

import com.runescape.GameEngine;
import com.runescape.content.LevelUp;
import com.runescape.content.ProtectedItems;
import com.runescape.content.events.DeathEvent;
import com.runescape.content.skills.prayer.PrayerData;
import com.runescape.event.Event;
import com.runescape.model.Entity;
import com.runescape.model.Follow;
import com.runescape.model.Item;
import com.runescape.model.LocalEntityList;
import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.masks.Animation;
import com.runescape.model.masks.Appearance;
import com.runescape.model.masks.ChatMessage;
import com.runescape.model.masks.EntityFocus;
import com.runescape.model.masks.FaceLocation;
import com.runescape.model.masks.ForceMovement;
import com.runescape.model.masks.ForceText;
import com.runescape.model.masks.Graphics;
import com.runescape.model.masks.Hits;
import com.runescape.model.masks.Hits.Hit;
import com.runescape.model.npc.NPC;
import com.runescape.model.npc.NPCDefinition;
import com.runescape.net.ActionSender;
import com.runescape.net.Packet;
import com.runescape.packethandler.PacketHandlers;
import com.runescape.util.Animations;
import com.runescape.util.Misc;
import com.runescape.util.Stream;
import com.runescape.world.Clan;
import com.runescape.world.GroundItem;
import com.runescape.world.ShopManager;

/**
 * Represents a connected player.
 * 
 * @author Graham
 * 
 * 
 */
public class Player extends Entity {
	

	private PlayerDetails details;
	private transient ActionSender actionSender;
	private transient Queue<Packet> queuedPackets;
	private transient PlayerUpdateFlags updateFlags;
	private transient TradeSession trade;
	private transient WalkingQueue walkingQueue;
	private transient LocalEntityList localEntities;
	private transient ChatMessage lastChatMessage;
	private transient Animation lastAnimation;
	private transient Graphics lastGraphics;
	private transient EntityFocus entityFocus;
	private transient ForceText forceText;
	private transient FaceLocation faceLocation;
	private transient ForceMovement forceMovement;
	private transient int world;
	private transient Map<String, Object> temporaryAttributes;
	private transient ShopSession shopSession;
	private transient Queue<Hit> queuedHits;
	private transient List<Player> tradeRequests;
	private transient List<Player> duelRequests;
	private transient Clan clan;
	private transient Prayers prayers;
	private transient Bonuses bonuses;
	private transient int lastWildLevel;
	@SuppressWarnings("unused")
	private transient boolean hd;
	private transient Object distanceEvent;
	private Bank bank;
	private Settings settings;
	private LevelUp levelUp;
	private Appearance appearance;
	private Equipment equipment;
	private Skills skills;
	private Inventory inventory;
	private Friends friends;
	private int rights = 0;
	private int runEnergy = 100;
	private ShopManager shopmanager;
	public boolean firstLogin = true;
	public boolean receivedStarter = false;
	public int pSafetyStronghold = 0;
	public boolean findingPath;
	public boolean firstCustomization = true;
	private boolean welcomeScreen;
	private boolean cantMove;
	private boolean morphed;
	private long objectClickDelay;
	public long barbarianFishingDelay;

	/*
	 * Start of quest ints
	 */
	public int learningTheRopesInt = 0;
	public int cookStage = 0;
	public int doricsQuestInt = 0;
	public int runeMysteriesInt = 0;
	public int druidicRitualInt = 0;
	public int SheepShearerInt = 0;

	/**
	 * Players quest points
	 */
	public int questPoints = 0;
	public int flourAmount;
	public int grain;
	/*
	 * Holiday booleans/ints
	 */
	public int easter2014 = 0;
	
	/**
	 * Clue scroll stuffs
	 */
    public int currentClue = 0;
    /**
     * 0: Nothing
     * 1: Easy
     * 2: Medium
     * 3: Hard
     */
    public int clueDifficulty = 0; 

	/**
	 * Music System
	 */
	public int musicTimer = 0;
	public boolean randomMusic;
	/**
	 * Class for storing and converting bytes.
	 */
	public Stream stream = new Stream(500, 5000);
	public long lastTeleport;

	public Player(PlayerDetails details) {
		this.details = details;
		this.appearance = new Appearance();
		this.equipment = new Equipment();
		this.skills = new Skills();
		this.inventory = new Inventory();
		this.friends = new Friends();
		this.settings = new Settings();
		this.bank = new Bank();
		this.settings.setDefaultSettings();
	}

	/**
	 * Called when XStream loads us.
	 * 
	 * NOTE: other loaders should call this also.
	 */
	public Object readResolve() {
		super.readResolve();
		actionSender = new ActionSender(this);
		follow = new Follow(this);
		queuedPackets = new LinkedList<Packet>();
		updateFlags = new PlayerUpdateFlags();
		walkingQueue = new WalkingQueue(this);
		skills.setPlayer(this);
		inventory.setPlayer(this);
		bank.setPlayer(this);
		equipment.setPlayer(this);
		friends.setPlayer(this);
		localEntities = new LocalEntityList();
		settings.setPlayer(this);
		prayers = new Prayers(this);
		temporaryAttributes = new HashMap<String, Object>();
		lastChatMessage = null;
		lastAnimation = null;
		lastGraphics = null;
		entityFocus = null;
		forceText = null;
		faceLocation = null;
		forceMovement = null;
		tradeRequests = new ArrayList<Player>();
		duelRequests = new ArrayList<Player>();
		clan = null;
		bonuses = new Bonuses(this);
		world = 1;
		lastWildLevel = -1;
		details.refreshLongName();
		queuedHits = new LinkedList<Hit>();
		hd = false;
		levelUp = new LevelUp();
		
		welcomeScreen = true;
		return this;
	}

	public transient int oldX;
	public transient int oldY;
	public transient boolean IsFollowing = false;
	public int FollowingId;

	public void Following(final Player player, final Player p2) {
		if (!player.IsFollowing && !player.isMorphed()) {
			player.IsFollowing = true;
			World.getInstance().registerEvent(new Event(600) { // equal to 2
																// tickable's

						public void execute() {
							// FOLLOWING BY MYSTIC FLOW :P
							if (player.isDestroyed() || p2.isDestroyed()) {
								player.IsFollowing = false;
								this.stop();
								return;
							}
							if (!player.getLocation().withinDistance(p2.getLocation(), 15)) {
								player.IsFollowing = false;
								this.stop();
								return;
							}
							if (!player.IsFollowing) {
								this.stop();
								return;
							}
							player.setEntityFocus(p2.getClientIndex());
							player.getWalkingQueue().reset();
							int toX = p2.oldX - (player.getLocation().getRegionX() - 6) * 8;
							int toY = p2.oldY - (player.getLocation().getRegionY() - 6) * 8;
							int tX = 0;
							int tY = 0;
							if (p2.getLocation().getX() > player.getLocation().getX()) {
								tX = 1;
							}
							if (p2.getLocation().getX()< player.getLocation().getX()) {
								tX = -1;
							}
							if (p2.getLocation().getY() > player.getLocation().getY()) {
								tY = 1;
							}
							if (p2.getLocation().getY() < player.getLocation().getY()) {
								tY = -1;
							}
							if (!GameEngine.noClipHandler.checkPos(player.getLocation().getX() + tX, player.getLocation().getY() + tY, player.getLocation().getZ())) {
								return;
							}
							player.getWalkingQueue().getNextPlayerMovement();
							player.getWalkingQueue().addToWalkingQueue(toX, toY);
						}
					});
		}
	}

	/**
	 * Called roughly every 500ms.
	 */

	public void tick() {
		World.getInstance().getGroundItems().refreshGlobalItems(this);
	}

	public void setTemporaryAttribute(String attribute, Object value) {
		temporaryAttributes.put(attribute, value);
	}

	public Object getTemporaryAttribute(String attribute) {
		return temporaryAttributes.get(attribute);
	}

	public void removeTemporaryAttribute(String attribute) {
		temporaryAttributes.remove(attribute);
	}

	public Settings getSettings() {
		return this.settings;
	}

	public LevelUp getLevelUp() {
		return this.levelUp;
	}

	public PlayerDetails getPlayerDetails() {
		return this.details;
	}

	public ActionSender getActionSender() {
		return this.actionSender;
	}

	public IoSession getSession() {
		return this.details.getSession();
	}

	public String getUsername() {
		return this.details.getUsername();
	}

	public int getRights() {
		return this.rights;
	}

	public void setRights(int r) {
		rights = r;
	}

	public void processQueuedPackets() {
		synchronized (queuedPackets) {
			Packet p = null;
			while ((p = queuedPackets.poll()) != null) {
				PacketHandlers.handlePacket(getSession(), p);
			}
		}
	}

	public void addPacketToQueue(Packet p) {
		synchronized (queuedPackets) {
			queuedPackets.add(p);
		}
	}

	public PlayerUpdateFlags getUpdateFlags() {
		return updateFlags;
	}

	public Appearance getAppearance() {
		return appearance;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public WalkingQueue getWalkingQueue() {
		return walkingQueue;
	}

	public Skills getLevels() {
		return skills;
	}

	public void graphics(int id) {
		graphics(id, 0);
	}

	public void graphics(int id, int delay) {
		lastGraphics = new Graphics(id, delay);
		updateFlags.setGraphicsUpdateRequired(true);
	}

	public void animate(int id) {
		animate(id, 0);
	}

	public void animate(int id, int delay) {
		lastAnimation = new Animation(id, delay);
		updateFlags.setAnimationUpdateRequired(true);
	}

	public boolean isDisconnected() {
		return !getSession().isConnected();
	}

	public void setPlayerListSize(int playerListSize) {
		localEntities.playerListSize = playerListSize;
	}

	public int getPlayerListSize() {
		return localEntities.playerListSize;
	}

	public void setPlayerList(Player[] playerList) {
		localEntities.playerList = playerList;
	}

	public Player[] getPlayerList() {
		return localEntities.playerList;
	}

	public void setPlayersInList(byte[] playersInList) {
		localEntities.playersInList = playersInList;
	}

	public byte[] getPlayersInList() {
		return localEntities.playersInList;
	}

	public void setNpcListSize(int npcListSize) {
		localEntities.npcListSize = npcListSize;
	}

	public int getNpcListSize() {
		return localEntities.npcListSize;
	}

	public void setNpcList(NPC[] npcList) {
		localEntities.npcList = npcList;
	}

	public NPC[] getNpcList() {
		return localEntities.npcList;
	}

	public void setNpcsInList(byte[] npcsInList) {
		localEntities.npcsInList = npcsInList;
	}

	public byte[] getNpcsInList() {
		return localEntities.npcsInList;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public ChatMessage getLastChatMessage() {
		return lastChatMessage;
	}

	public void setLastChatMessage(ChatMessage msg) {
		lastChatMessage = msg;
	}

	public int getWorld() {
		return world;
	}

	public Friends getFriends() {
		return friends;
	}

	public boolean isRebuildNpcList() {
		return localEntities.rebuildNpcList;
	}

	public Animation getLastAnimation() {
		return lastAnimation;
	}

	public void setLastAnimation(Animation lastAnimation) {
		this.lastAnimation = lastAnimation;
	}

	public void setEntityFocus(EntityFocus entityFocus) {
		this.entityFocus = entityFocus;
		updateFlags.setEntityFocusUpdateRequired(true);
	}

	@Override
	public EntityFocus getEntityFocus() {
		return entityFocus;
	}

	public void setForceText(ForceText forceText) {
		this.forceText = forceText;
		updateFlags.setForceTextUpdateRequired(true);
	}

	public ForceText getForceText() {
		return forceText;
	}

	public void setForceMovement(ForceMovement movement) {
		this.forceMovement = movement;
		updateFlags.setForceMovementRequired(true);
	}

	public ForceMovement getForceMovement() {
		return forceMovement;
	}

	public Graphics getLastGraphics() {
		return lastGraphics;
	}

	public void setFaceLocation(FaceLocation faceLocation) {
		this.faceLocation = faceLocation;
		updateFlags.setFaceLocationUpdateRequired(true);
	}

	public FaceLocation getFaceLocation() {
		return faceLocation;
	}

	public void setLastGraphics(Graphics lastGraphics) {
		this.lastGraphics = lastGraphics;
	}

	public void setRebuildNpcList(boolean b) {
		this.localEntities.rebuildNpcList = true;
	}

	public void processQueuedHits() {
		if (!updateFlags.isHitUpdateRequired()) {
			if (queuedHits.size() > 0) {
				Hit h = queuedHits.poll();
				hit(h.getDamage(), h.getType());
			}
		}
		if (!updateFlags.isHit2UpdateRequired()) {
			if (queuedHits.size() > 0) {
				Hit h = queuedHits.poll();
				hit(h.getDamage(), h.getType());
			}
		}
	}

	public void forceChat(String message) {
		setForceText(new ForceText(message));
		updateFlags.setForceTextUpdateRequired(true);
	}

	public void hit(int damage) {
		
		hit(damage, damage == 0 ? Hits.HitType.NO_DAMAGE : Hits.HitType.NORMAL_DAMAGE);
	}

	public void hit(int damage, Hits.HitType type) {
		if (isDead()) {
			damage = 0;
			type = Hits.HitType.NO_DAMAGE;
		}
		boolean redemption = prayers.getHeadIcon() == PrayerData.REDEMPTION;
		byte maxHp = (byte) getMaxHp();
		byte newHp = (byte) (getHp() - damage);
		if (redemption) {
			if (newHp >= 1 && newHp <= maxHp * 0.10) {
				graphics(436, 0, 0);
				actionSender.sendMessage("Using your prayer skill, you heal yourself.");
				skills.setLevel(5, 0);
				actionSender.sendSkillLevel(5);
				heal((int) (skills.getLevelForXp(5) * 0.25));
			}
		}
		
			if (newHp >= 1 && newHp <= maxHp * 0.10 && !redemption) {
				if (equipment.getItemInSlot(12) == 2570) {
					teleport(Location.location(3221 + Misc.random(1), 3217 + Misc.random(3), 0));
					actionSender.sendMessage("Your ring of life shatters whilst teleporting you to safety.");
					equipment.getSlot(12).setItemId(-1);
					equipment.getSlot(12).setItemAmount(0);
					actionSender.refreshEquipment();
					queuedHits.clear();
					return;
				}
			}
		
		boolean damageOverZero = damage > 0;
		if (damage > skills.getLevel(3)) {
			damage = skills.getLevel(3);
		}
		if (damageOverZero && damage == 0) {
			type = Hits.HitType.NO_DAMAGE;
		}
		if (!updateFlags.isHitUpdateRequired()) {
			getHits().setHit1(new Hit(damage, type));
			updateFlags.setHitUpdateRequired(true);
		} else {
			if (!updateFlags.isHit2UpdateRequired()) {
				getHits().setHit2(new Hit(damage, type));
				updateFlags.setHit2UpdateRequired(true);
			} else {
				queuedHits.add(new Hit(damage, type));
				return;
			}
		}
		skills.setLevel(3, skills.getLevel(3) - damage);
		if (skills.getLevel(3) <= 0) {
			skills.setLevel(3, 0);
			if (!isDead()) {
				World.getInstance().registerEvent(new DeathEvent(this));
				setDead(true);
			}
		}
		actionSender.sendSkillLevel(3);
	}

	public Bank getBank() {
		return bank;
	}

	public void setRunEnergy(int runEnergy) {
		this.runEnergy = runEnergy;
		actionSender.sendEnergy();
	}

	public int getRunEnergy() {
		return this.runEnergy;
	}

	public void setHd(boolean b) {
		this.hd = b;
		details.setHd(b);
	}

	public boolean isHd() {
		return details.isHd();
	}

	public TradeSession getTrade() {
		return trade;
	}

	public void setTrade(TradeSession ts) {
		this.trade = ts;
	}

	public List<Player> getTradeRequests() {
		return tradeRequests;
	}

	public ShopSession getShopSession() {
		return shopSession;
	}

	public void setShopSession(ShopSession shopSession) {
		this.shopSession = shopSession;
	}

	public void setAppearance(Appearance newAppearance) {
		this.appearance = newAppearance;
		updateFlags.setAppearanceUpdateRequired(true);
	}

	public boolean wantsToTrade(Player p2) {
		for (Player p : tradeRequests) {
			if (p != null) {
				if (p.equals(p2)) {
					return true;
				}
			}
		}
		return false;
	}

	public void newTradeRequest(Player p2) {
		if (wantsToTrade(p2)) {
			return;
		}
		tradeRequests.add(p2);
	}

	public List<Player> getDuelRequests() {
		return duelRequests;
	}

	public boolean wantsToDuel(Player p2) {
		for (Player p : duelRequests) {
			if (p != null) {
				if (p.equals(p2)) {
					return true;
				}
			}
		}
		return false;
	}

	public void newDuelRequest(Player p2) {
		if (wantsToDuel(p2)) {
			return;
		}
		duelRequests.add(p2);
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}

	public Clan getClan() {
		return clan;
	}

	public Prayers getPrayers() {
		return prayers;
	}

	@Override
	public void dropLoot() {
		Entity killer = this.getKiller();
		Player klr = killer instanceof NPC ? null : (Player) killer;
		if (klr == null) {
			klr = this;
		}
		int amountToKeep = settings.isSkulled() ? 0 : 3;
		if (prayers.isProtectItem()) {
			amountToKeep = settings.isSkulled() ? 1 : 4;
		}
		int[] protectedItems = new int[amountToKeep];
		boolean[] saved = new boolean[amountToKeep];
		if (protectedItems.length > 0) {
			protectedItems[0] = ProtectedItems.getProtectedItem1(this)[0];
		}
		if (protectedItems.length > 1) {
			protectedItems[1] = ProtectedItems.getProtectedItem2(this)[0];
		}
		if (protectedItems.length > 2) {
			protectedItems[2] = ProtectedItems.getProtectedItem3(this)[0];
		}
		if (protectedItems.length > 3) {
			protectedItems[3] = ProtectedItems.getProtectedItem4(this)[0];
		}
		boolean save = false;
		for (int i = 0; i < 28; i++) {
			save = false;
			Item item = inventory.getSlot(i);
			if (item.getItemId() > 0) {
				for (int j = 0; j < protectedItems.length; j++) {
					if (amountToKeep > 0 && protectedItems[j] > 0) {
						if (!saved[j] && !save) {
							if (item.getItemId() == protectedItems[j] && item.getItemAmount() == 1) {
								saved[j] = true;
								save = true;
							}
							if (item.getItemId() == protectedItems[j] && item.getItemAmount() > 1) {
								item.setItemAmount(item.getItemAmount() - 1);
								saved[j] = true;
								save = true;
							}
						}
					}
				}
				if (!save) {
					int itemId = item.getItemId();
					
					GroundItem gi = new GroundItem(itemId, item.getItemAmount(), this.getLocation(), item.getDefinition().isPlayerBound() ? this : klr);
					World.getInstance().getGroundItems().newEntityDrop(gi);
				}
			}
		}
		inventory.clearAll();
		saved = new boolean[amountToKeep];
		for (int i = 0; i < 14; i++) {
			save = false;
			Item item = this.getEquipment().getSlot(i);
			if (item.getItemId() > 0) {
				for (int j = 0; j < protectedItems.length; j++) {
					if (amountToKeep > 0 && protectedItems[j] > -1) {
						if (!saved[j] && !save) {
							if (item.getItemId() == protectedItems[j] && item.getItemAmount() == 1) {
								saved[j] = true;
								save = true;
							}
							if (item.getItemId() == protectedItems[j] && item.getItemAmount() > 1) {
								item.setItemAmount(item.getItemAmount() - 1);
								saved[j] = true;
								save = true;
							}
						}
					}
				}
				if (!save) {
					int itemId = item.getItemId();
					
					GroundItem gi = new GroundItem(itemId, item.getItemAmount(), this.getLocation(), item.getDefinition().isPlayerBound() ? this : klr);
					World.getInstance().getGroundItems().newEntityDrop(gi);
				}
			}
		}
		equipment.clearAll();
		GroundItem gi = new GroundItem(526, 1, this.getLocation(), klr);
		World.getInstance().getGroundItems().newEntityDrop(gi);
		inventory.setProtectedItems(protectedItems);
	}

	@Override
	public int getAttackAnimation() {
		return !this.appearance.isNpc() ? Animations.getAttackAnimation(this) : NPCDefinition.forId(this.appearance.getNpcId()).getAttackAnimation();
	}

	@Override
	public int getAttackSpeed() {
		if (getMiasmicEffect() > 0) {
			return Animations.getAttackSpeed(this) * 2;
		}
		return Animations.getAttackSpeed(this);
	}

	@Override
	public int getDeathAnimation() {
		return !this.appearance.isNpc() ? 7185 : NPCDefinition.forId(this.appearance.getNpcId()).getDeathAnimation();
	}

	@Override
	public int getDefenceAnimation() {
		return !this.appearance.isNpc() ? Animations.getDefenceAnimation(this) : NPCDefinition.forId(this.appearance.getNpcId()).getDefenceAnimation();
	}

	@Override
	public int getHitDelay() {
		return Animations.getPlayerHitDelay(this);
	}

	@Override
	public int getHp() {
		return this.getLevels().getLevel(3);
	}

	@Override
	public int getMaxHit() {
//		int a = skills.getLevel(2);
//		int b = bonuses.getBonus(11);
//		CombatSkill fightType = this.getSettings().getAttackVars().getSkill();
//		double c = (double) a;
//		double d = (double) b;
//		double e = 0;
//		double f = 0;
//		double g = 0;
//		double gg = 0;
//		double h = 0;
//		int strPrayer = prayers.getStrengthPrayer();
//		if (strPrayer == 1) {
//			gg = 0.05;
//		} else if (strPrayer == 2) {
//			gg = 0.1;
//		} else if (strPrayer == 3) {
//			gg = 0.15;
//		} else if (prayers.getSuperPrayer() == 1) {
//			gg = 0.18;
//		} else if (prayers.getSuperPrayer() == 2) {
//			gg = 0.23;
//		}
//		e = c * (1 + g + gg);
//		if (fightType.equals(CombatSkill.AGGRESSIVE)) {
//			e += 3;
//		}
//		if (fightType.equals(CombatSkill.CONTROLLED)) {
//			e += 1;
//		}
//		f = (d * 0.00175) + 0.1;
//		h = Math.floor(e * f);// 2.05);
//		if (a >= 80) {
//			h -= 2.0;
//		} else {
//			h += 2.0;
//		}
//		if (CombatFormula.wearingMeleeVoid(this)) {
//			h *= 1.10;
//		} 
//		return (int) h;
//
//		/*
//		 * double maxHit = 0; if(this.getCombatType().equals(CombatType.MELEE))
//		 * { int strBonus = bonuses.getBonus(11); int strength =
//		 * skills.getLevel(2); int fightType =
//		 * this.getSettings().getAttackStyle(); if(fightType == 1 || fightType
//		 * == 4) { maxHit += (double) (1.05 + (double) ((double) (strBonus *
//		 * strength) * 0.00175)); } else if(fightType == 2) { maxHit += (double)
//		 * (1.35 + (double) ((double) (strBonus) * 0.00525)); } else
//		 * if(fightType == 3){ maxHit += (double) (1.15 + (double) ((double)
//		 * (strBonus) * 0.00175)); } int strPrayer =
//		 * prayers.getStrengthPrayer(); if (strPrayer == 1) { maxHit += (double)
//		 * 0.05; } else if (strPrayer == 2) { maxHit += (double) 0.1; } else if
//		 * (strPrayer == 3) { maxHit += (double) 0.15; } else if
//		 * (prayers.getSuperPrayer() == 1) { maxHit += (double) 0.18; } else if
//		 * (prayers.getSuperPrayer() == 2) { maxHit += (double) 0.23; } maxHit
//		 * += (double)(strength * 0.1); System.out.println("maxHit " + maxHit);
//		 * } else if(this.getCombatType().equals(CombatType.RANGE)) { int range
//		 * = skills.getLevel(4); int rangeBonus = bonuses.getBonus(4); double d
//		 * = ((double) (rangeBonus * 0.00175D) + 0.1D); maxHit += d * (double)
//		 * range + 2.05D; } else { // Magic spells have a set max hit. } return
//		 * ((int) (Math.ceil(maxHit)) + 1);
//		 */
		return 1;
	}

	@Override
	public int getMaxHp() {
		return this.getLevels().getLevelForXp(3);
	}

	@Override
	public void graphics(int id, int delay, int height) {
		this.lastGraphics = new Graphics(id, delay, height);
		updateFlags.setGraphicsUpdateRequired(true);
	}

	@Override
	public void heal(int amount) {
		if (isDead()) {
			return;
		}
		if ((skills.getLevel(3) + amount) > (skills.getLevelForXp(3))) {
			skills.setLevel(3, skills.getLevelForXp(3));
			actionSender.sendSkillLevel(3);
			return;
		}
		skills.setLevel(3, skills.getLevel(3) + amount);
		actionSender.sendSkillLevel(3);
	}

	@Override
	public boolean isAutoRetaliating() {
		return settings.isAutoRetaliate();
	}

	@Override
	public boolean isDestroyed() {
		return !World.getInstance().getPlayerList().contains(this);
	}

	@Override
	public void setEntityFocus(int id) {
		this.entityFocus = new EntityFocus(id);
		updateFlags.setEntityFocusUpdateRequired(true);
	}

	@Override
	public void setFaceLocation(Location location) {
		this.faceLocation = new FaceLocation(location);
		updateFlags.setFaceLocationUpdateRequired(true);
	}

	@Override
	public void setHp(int val) {
		this.getLevels().setLevel(3, val);
		actionSender.sendSkillLevel(3);
	}

	public Bonuses getBonuses() {
		return this.bonuses;
	}

	public int getLastWildLevel() {
		return lastWildLevel;
	}

	public void setLastwildLevel(int currentLevel) {
		this.lastWildLevel = currentLevel;
	}

	public void setWalkingQueue(WalkingQueue wQ) {
		this.walkingQueue = wQ;
	}

	public boolean isInWelcomeScreen() {
		return welcomeScreen;
	}

	public void setInWelcomeScreen(boolean b) {
		welcomeScreen = b;
	}

	public Object getDistanceEvent() {
		return distanceEvent;
	}

	public void setDistanceEvent(Object event) {
		this.distanceEvent = event;
	}

	/**
	 * Used while designing character
	 */
	public void setCantMove(boolean cantMove) {
		this.cantMove = cantMove;
	}

	/**
	 * Used while designing character
	 */
	public boolean getCantMove() {
		return this.cantMove;
	}
	
	public ShopManager getShopManager() {
		return this.shopmanager;
	}
	
	public void setObjectClickDelay(long time) {
		long delay = System.currentTimeMillis() + time;
		this.objectClickDelay = delay;
	}
	
	public long getObjectClickDelay() {
		return this.objectClickDelay;
	}
	
	public void setMorphed(boolean b) {
		morphed = b;
	}
	
	public boolean isMorphed() {
		return morphed;
	}
}
