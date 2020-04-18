package com.runescape.model;

import com.runescape.model.player.Player;
import com.runescape.util.Misc;

public class Follow {

	private Entity follower;
	private Entity beingFollowed;
	
	public Follow(Entity e) {
		this.follower = e;
	}
	
	Location lastLoc = null;
	
	public void followEntity() {
		if (beingFollowed == null || follower.isDead() || beingFollowed.isDead() || beingFollowed.isDestroyed()) {
			beingFollowed = null;
			return;
		}
		walkTo();
	}
	
   public void walkTo() {
		int bfX = beingFollowed.getLocation().getX();
		int bfY = beingFollowed.getLocation().getY();
		int fX = follower.getLocation().getX();
		int fY = follower.getLocation().getY();
        int firstX = bfX - (((Player) follower).getLocation().getRegionX() - 6) * 8;
        int firstY = bfY - (((Player) follower).getLocation().getRegionY() - 6) * 8;
        int offsetX = 0;
        int offsetY = 0;
        ((Player) follower).getWalkingQueue().reset();
        if (((Player) follower).getLocation().withinDistance(beingFollowed.getLocation(), 1)) {
        	return;
        }
		if (bfX > fX && bfX == fY) {
			offsetX++;
		} else if (bfX < fX && bfX == fY) {
			offsetX--;
		} else if (bfX == fX && bfX > fY) {
			offsetY++;
		} else if (bfX == fX && bfX < fY) {
			offsetY--;
		} else if (bfX > fX && bfX > fY) {
			offsetX++;
			offsetY++;
		} else if (bfX < fX && bfX < fY) {
			offsetX--;
			offsetY--;
		} else if (bfX > fX && bfX < fY) {
			offsetX++;
			offsetY--;
		} else if (bfX < fX && bfX > fY) {
			offsetX--;
			offsetY++;
		}
        ((Player) follower).getWalkingQueue().addToWalkingQueue(firstX + offsetX, firstY + offsetY);
        ((Player) follower).getUpdateFlags().setAppearanceUpdateRequired(true);
    }
	
	public Entity getFollowing() {
		return beingFollowed;
	}

	public void setFollowing(Entity following) {
		if (following == null) {
			if (follower instanceof Player) {
				((Player) follower).getActionSender().followPlayer(null, -1);
			}
		}
		this.beingFollowed = following;
	}
}
