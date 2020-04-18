package com.runescape.content.events;

import com.runescape.content.CharacterDesign;
import com.runescape.model.player.Player;

public class StartingEvent {
	
	public static void showStartingChat(Player p, int status) {
		p.getActionSender().softCloseInterfaces();
		int newStatus = -1;
		switch(status) {
		case 601:
			p.getActionSender().sendNPCHead(7938, 241, 2);
			p.getActionSender().modifyText("Sir Vant", 241, 3);
			p.getActionSender().modifyText("Welcome to RuneScape!", 241, 4);
			p.getActionSender().animateInterface(9850, 241, 2);
			p.getActionSender().sendChatboxInterface2(241);
			newStatus = 602;
		break;
		case 602:
			p.getActionSender().sendNPCHead(7938, 242, 2);
			p.getActionSender().modifyText("Sir Vant", 242, 3);
			p.getActionSender().modifyText("We are currently under Beta development and are", 242, 4);
			p.getActionSender().modifyText("working hard to offer the best remake experience.", 242, 5);
			p.getActionSender().animateInterface(9850, 242, 2);
			p.getActionSender().sendChatboxInterface2(242);
			newStatus = 603;
		break;
		case 603:
			p.getActionSender().sendNPCHead(7938, 242, 2);
			p.getActionSender().modifyText("Sir Vant", 242, 3);
			p.getActionSender().modifyText("All experience rates here are x10, any other ", 242, 4);
			p.getActionSender().modifyText("rate is set to an original x1.", 242, 5);
			p.getActionSender().animateInterface(9850, 242, 2);
			p.getActionSender().sendChatboxInterface2(242);
			newStatus = 604;
		break;
		case 604:
			p.getActionSender().sendNPCHead(7938, 241, 2);
			p.getActionSender().modifyText("Sir Vant", 241, 3);
			p.getActionSender().modifyText("Farewell " + p.getUsername() + ".", 241, 4);
			p.getActionSender().animateInterface(9810, 241, 2);
			p.getActionSender().sendChatboxInterface2(241);
			newStatus = 605;
		break;
		case 605:
			CharacterDesign.open(p);
			p.getActionSender().modifyText("Creating your Character", 769, 0);
			p.getActionSender().modifyText("Before your adventure begins, you should take some time to set your mouse " +
					"settings and give your character its very own look. Take your time and choose " +
					"from the option above, then confirm your changes.", 769, 1);
			p.getActionSender().sendChatboxInterface2(769);
			p.firstLogin = false;
			//p.setFrozen(false);
			//p.setCantMove(false);
			newStatus = -1;
			break;
		}
		if (newStatus != -1) {
			p.setTemporaryAttribute("dialogue", newStatus);
		}
	}
}
