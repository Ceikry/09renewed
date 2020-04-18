package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;
import com.runescape.util.Misc;

public class ChangePassword implements Command {

	@Override
	public void execute(Player player, String command) { //TODO finish this
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		String oldPassword = Misc.md5Hash(cmd[1]);
		String newPassword = Misc.md5Hash(cmd[2]);
			if (cmd[0] == null) {
				player.getActionSender().sendMessage("Usage - ::password [old pass] [new pass]");
				return;
			}
			if (cmd[0] != null && cmd[1] != null) {
				if (oldPassword == player.getPlayerDetails().getPassword()) {
					player.getPlayerDetails().setPassword(Misc.md5Hash(cmd[1]));
					player.getActionSender().sendMessage("Your new password is: " + cmd[1]);
				}
			}
		}
		
	}

	@Override
	public int minimumRightsNeeded() {
		return 0;
	}

	

}
