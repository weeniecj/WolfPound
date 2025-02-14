package com.fernferret.wolfpound.commands;

import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;

import com.fernferret.wolfpound.WolfPound;

public abstract class WolfPoundCommand implements CommandExecutor {
	protected WolfPound plugin;
	protected static final Logger log = Logger.getLogger("Minecraft");
	
	public WolfPoundCommand(WolfPound plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Currently unused, used for parsing more advanecd worldstrings
	 * 
	 * @param s
	 * @return
	 */
	protected HashSet<String> parseWorlds(String s) {
		String[] values = s.split(":");
		HashSet<String> worldList = new HashSet<String>();
		if (values.length == 2 && values[0].equalsIgnoreCase("w")) {
			String[] worlds = values[1].split(",");
			
			if (worlds.length == 1 && worlds[0] == "all") {
				worldList.add("all");
				return worldList;
			}
			Server server = plugin.getServer();
			
			for (String world : worlds) {
				if (server.getWorld(world) != null) {
					worldList.add("w:" + world);
				}
			}
		}
		return worldList;
	}
	/**
	 * Checks to see if the given string was:
	 * 1. In the format w:WORLD
	 * 2. WORLD is a valid worldname
	 * @param s The passed in string
	 * @return true if valid, false if not
	 */
	protected boolean isValidWorld(String s) {
		String[] values = s.split(":");
		return(values.length == 2 && values[0].equalsIgnoreCase("w") && 
				plugin.getServer().getWorld(values[1]) != null);
	}
	/**
	 * Returns just the worldname
	 * @param worldString String in format: w:WORLD
	 * @return
	 */
	protected String getWorldName(String worldString) {
		String[] values = worldString.split(":");
		if(values.length != 2 || !values[0].equalsIgnoreCase("w") || plugin.getServer().getWorld(values[1]) == null) {
			log.severe(WolfPound.logPrefix + " Could not find world name(" + worldString + ") in getWorldName(String worldString)");
			log.severe("Worlds: " + plugin.getServer().getWorlds());
			return "";
		} else {
			return values[1];
		}
	}
	
	/**
	 * Checks to see if the given string was:
	 * 1. In the format w:WORLD
	 * 2. WORLD is a valid worldname
	 * @param s The passed in string
	 * @return true if valid, false if not
	 */
	protected String checkKeyword(String s, String[] choices) {
		for(String word : choices) {
			if(s.matches("(?i)(.*" + word + ".*)")) {
				return word;
			}
		}
		return null;
	}
	
	/**
	 * Checks to see if the given list contains the given string
	 * @param s The passed in string
	 * @param choices The list to check
	 * @return true if the string is in the list, false if not
	 */
	protected boolean isAKeyword(String s, String[] choices) {
		for(String word : choices) {
			if(s.matches("(?i)(.*" + word + ".*)")) {
				return true;
			}
		}
		return false;
	}
	
	
}
