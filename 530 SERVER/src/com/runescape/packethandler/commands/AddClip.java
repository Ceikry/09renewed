package com.runescape.packethandler.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.runescape.model.player.Player;

public class AddClip implements Command {

	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
			int objectX = player.getLocation().getX();
			int objectY = player.getLocation().getY();
			int objectZ = player.getLocation().getZ();
			String filePath = "./data/clip.cfg";
			BufferedWriter bw = null;
			try 
			{				
				bw = new BufferedWriter(new FileWriter(filePath, true));
				bw.write("object = " + objectX + "\t" + objectY + "\t" + objectZ);
				bw.newLine();
				bw.flush();
			} 
			catch (IOException ioe) 
			{
				ioe.printStackTrace();
			} 
			finally 
			{
				if (bw != null)
				{
					try 
					{
						bw.close();
					} 
					catch (IOException ioe2) 
					{
					}
				}
			}
		}
	}

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
