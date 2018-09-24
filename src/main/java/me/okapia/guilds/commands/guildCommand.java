package me.okapia.guilds.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.okapia.guilds.main;

public class guildCommand implements CommandExecutor {
	main plugin;
	
	public guildCommand(main main){
		plugin = main;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(args.length == 2 && args[0].equals("create")){
				plugin.gM.createGuild(args[1], p);
			}else if(args.length == 2 && args[0].equals("delete")){
				plugin.gM.deleteGuild(args[1], p);
			}else if(args.length == 1 && args[0].equals("list")){
				plugin.gM.getGuildsList(p);
			}else if(args.length == 2 && args[0].equals("addmember")){
				plugin.gM.addMember(p,args[1]);
			}else if(args.length == 2 && args[0].equals("deletemember")){
				plugin.gM.deleteMember(p, args[1]);
			}
		}
		return false;
	}
	


}
