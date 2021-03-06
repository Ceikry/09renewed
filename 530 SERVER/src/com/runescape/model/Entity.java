package com.runescape.model;

import java.util.HashMap;
import java.util.Map;

import com.runescape.model.masks.EntityFocus;
import com.runescape.model.masks.Hits;
import com.runescape.model.masks.Sprite;
import com.runescape.model.player.Player;

/**
 * An 'entity' in the game world.
 * @author Graham
 *
 */
public abstract class Entity {
	
	private transient boolean followIsDelayed;
	
	private Location location;
	private transient int index;
	private transient Sprite sprite;
	private transient Location teleportTo = null;
	private transient Hits hits;
	private transient boolean dead;
	private transient boolean hidden;
	private transient int combatTurns;
	private transient Entity target;
	private transient Entity attacker;
	private transient Entity lastOpponent;
	private transient long lastAttack;
	private transient long lastAttacked;
	private transient int poisonAmount;
	private transient long lastMagicAttack;
	private transient Map<Entity, Integer> killers;
	protected transient Follow follow;
	private transient int lastAttackType;
	private transient int miasmicEffect;
	private transient boolean frozen;
	private transient boolean attacking;
	private transient Entity interactingWith;
	private transient boolean isAggressor;
	
	public void resetTurnTo() {
	}
	
	public Entity() { //start location
		//this.location = Location.location(3221 + Misc.random(1), 3217 + Misc.random(4), 0);
		this.location = Location.location(2524, 5000, 0);
	}
	
	public Object readResolve() {
		hits = new Hits();
		hidden = false;
		dead = false;
		target = null;
		attacker = null;
		combatTurns = 0;
		poisonAmount = 0;
		killers = new HashMap<Entity, Integer>();
		sprite = new Sprite();
		
		followIsDelayed = false;
		
		return this;
	}
	
	public void setFollowDelayed(boolean b) {
		this.followIsDelayed = b;
	}
	public boolean isFollowDelayed() {
		return followIsDelayed;
	}
	
	public Entity getInteractingWith() {
		return interactingWith;
	}
	
	public boolean isInteracting() {
		return interactingWith != null;
	}
	
	public void setInteractingWith(Entity entity) {
		this.interactingWith = entity;
	}
	
	public boolean isAttacking() {
		return this.attacking;
	}
	
	public void setAttacking(boolean b) {
		if(!b) {
			interactingWith = null;
			this.resetTurnTo();
			this.setAggressor(false);
		}
		this.attacking = b;
	}
	
	public boolean isAggressor() {
		return isAggressor;
	}
	
	public void setAggressor(boolean b) {
		isAggressor = b;
	}

	public abstract void hit(int damage);
	public abstract void hit(int damage, Hits.HitType type);
	public abstract int getMaxHp();
	public abstract int getHp();
	public abstract void setHp(int val);
	public abstract void graphics(int id);
	public abstract void graphics(int id, int delay);
	public abstract void graphics(int id, int delay, int height);
	public abstract void animate(int id);
	public abstract void setEntityFocus(int id);
	public abstract void setFaceLocation(Location loc);
	public abstract EntityFocus getEntityFocus();
	public abstract void animate(int id, int delay);
	public abstract boolean isAutoRetaliating();
	public abstract int getAttackAnimation();
	public abstract int getAttackSpeed();
	public abstract int getHitDelay();
	public abstract int getMaxHit();
	public abstract int getDefenceAnimation();
	public abstract int getDeathAnimation();
	public abstract void dropLoot();
	public abstract boolean isDestroyed();
	public abstract void heal(int amt);
	
	public void addToHitCount(Entity killer, int damage) {
		if(!killers.containsKey(killer)) {
			killers.put(killer, damage);
		} else {
			killers.put(killer, killers.get(killer) + damage);
		}
	}
	
	public Entity getKiller() {
		Entity highestHitter = null;
		int highestHit = 0;
		for(Map.Entry<Entity, Integer> entry : killers.entrySet()) {
			if (entry.getKey() != null) {
				if(entry.getValue() > highestHit) {
					highestHitter = entry.getKey();
				}
			}
		}
		return highestHitter;
	}
	
	public void clearKillersHits() {
		killers.clear();
	}
		
	public Hits getHits() {
		return hits;
	}
	
	public void teleport(Location location) {
		this.teleportTo = location;
		if (this instanceof Player) {
			((Player) this).getWalkingQueue().reset();
		}
	}
	
	public void resetTeleportTo() {
		this.teleportTo = null;
	}
	
	public Location getTeleportTo() {
		return this.teleportTo;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setHidden(boolean b) {
		hidden = b;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setDead(boolean b) {
		dead = b;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public int getClientIndex() {
		if(this instanceof Player) {
			return this.index + 32768;
		} else {
			return this.index;
		}
	}
	
	public boolean inCombat() {
		if (target == null && attacker == null) {
			return false;
		}
		return true;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public Entity getTarget() {
		return target;
	}

	public void setAttacker(Entity attacker) {
		this.attacker = attacker;
	}

	public Entity getAttacker() {
		return attacker;
	}
	
	public void setCombatTurns(int i) {
		combatTurns = i;
	}
	
	public int getCombatTurns() {
		return combatTurns;
	}
	
	public void resetCombatTurns() {
		combatTurns = 0;
	}
	
	public void incrementCombatTurns() {
		combatTurns++;
	}

	public void setLastkiller(Entity lastOpponent) {
		this.lastOpponent = lastOpponent;
	}

	public Entity getLastKiller() {
		return lastOpponent;
	}

	public boolean isPoisoned() {
		return poisonAmount > 0;
	}

	public void setPoisonAmount(int amt) {
		if (amt <= 0) {
			amt = 0;
		}
		if (this instanceof Player) {
			((Player) this).getSettings().setPoisonAmount(amt);
		}
		this.poisonAmount = amt;
	}

	public int getPoisonAmount() {
		return poisonAmount;
	}

	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	public long getLastAttack() {
		return lastAttack;
	}

	public void setFollow(Follow follow) {
		this.follow = follow;
	}

	public Follow getFollow() {
		return follow;
	}

	public void setLastAttackType(int lastAttackType) {
		this.lastAttackType = lastAttackType;
	}

	public int getLastAttackType() {
		return lastAttackType;
	}

	public void setLastMagicAttack(long lastMagicAttack) {
		this.lastMagicAttack = lastMagicAttack;
	}

	public long getLastMagicAttack() {
		return lastMagicAttack;
	}

	
	public Sprite getSprites() {
		return sprite;
	}

	public void setLastAttacked(long lastAttacked) {
		this.lastAttacked = lastAttacked;
	}

	public long getLastAttacked() {
		return lastAttacked;
	}


	public void setMiasmicEffect(int miasmicEffect) {
		this.miasmicEffect = miasmicEffect;
	}

	public int getMiasmicEffect() {
		return miasmicEffect;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isFrozen() {
		return frozen;
	}
	
}
