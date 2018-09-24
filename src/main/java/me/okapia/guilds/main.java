package me.okapia.guilds;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.okapia.guilds.commands.guildCommand;
import me.okapia.guilds.commands.guildManager;

public class main extends JavaPlugin{

	public guildManager gM;
	
	@Override
	public void onEnable(){
		saveConfig();
		getCommand("guilds").setExecutor(new guildCommand(this));
		gM = new guildManager(this);
	}
}
