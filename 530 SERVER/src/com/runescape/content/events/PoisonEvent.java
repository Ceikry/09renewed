package com.runescape.content.events;

import com.runescape.event.Event;
import com.runescape.model.Entity;
import com.runescape.model.masks.Hits;
import com.runescape.model.player.Player;
import com.runescape.util.Misc;

public class PoisonEvent extends Event {

	private Entity target;
	
	public PoisonEvent(Entity target, int poisonAmount) {
		super(30000 + Misc.random(30000));
		this.target = target;
		initialize(poisonAmount);
	}

	private void initialize(int poisonAmount) { 
		if (target instanceof Player) {
			if (((Player)target).getSettings().getSuperAntipoisonCycles() > 0) {
				stop();
				return;
			}
			((Player)target).getActionSender().sendMessage("You have been poisoned!");
		}
		target.setPoisonAmount(poisonAmount);
	}

	@Override
	public void execute() {
		if (!target.isPoisoned() || target.isDead()) {
			stop();
			return;
		}
		if (target instanceof Player) {
			((Player) target).getActionSender().closeInterfaces();
		}
		target.hit(target.getPoisonAmount(), Hits.HitType.POISON_DAMAGE);
		if (Misc.random(200) >= 100) {
			target.setPoisonAmount(target.getPoisonAmount() - 1);
		}
		if (Misc.random(10) == 0) {
			target.setPoisonAmount(0);
			stop();
			return;
		}
		setTick(30000 + Misc.random(30000));
	}

}
