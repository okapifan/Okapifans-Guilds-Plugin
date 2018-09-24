package me.okapia.guilds.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.okapia.guilds.main;
import me.okapia.guilds.utils.utiltools;

public class guildManager {
	main plugin;
	File a;

	public guildManager(main main) {
		plugin = main;
	}

	public void createGuild(String guildName, Player p) {
		File a = new File(plugin.getDataFolder() + "/" + "guilds.yml ");
		if (!a.exists()) {
			try {
				a.createNewFile();
			} catch (IOException e) {
				return;
			}
		}
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(a);

		for (String guild : yaml.getKeys(false)) {
			System.out.println(guild);
			if (p.getUniqueId().toString().equals(yaml.getConfigurationSection(guild).get("owner"))
					|| yaml.getConfigurationSection(guild).getList("members").contains(p.getUniqueId().toString())) {
				p.sendMessage(utiltools.chat("&6" + "You are already in a guild!"));
				return;
			}
		}

		if (!yaml.contains(guildName)) {
			yaml.createSection(guildName);
			yaml.getConfigurationSection(guildName).set("owner", p.getPlayer().getUniqueId().toString());
			yaml.getConfigurationSection(guildName).set("members", new ArrayList<String>());
			yaml.getConfigurationSection(guildName).set("money", 0);
			yaml.getConfigurationSection(guildName).set("country", null);
			yaml.getConfigurationSection(guildName).set("city", null);
			try {
				yaml.save(a);
			} catch (IOException e) {
				p.sendMessage(utiltools.chat("&6" + guildName + " could not be created!"));
				e.printStackTrace();
			}
			p.sendMessage(utiltools.chat("&6" + guildName + " created succesfully!"));
		} else {
			p.sendMessage(utiltools.chat("&6" + guildName + " already exists!"));
		}
	}

	public void getGuildsList(Player p) {
		File a = new File(plugin.getDataFolder() + "/" + "guilds.yml ");
		if (!a.exists()) {
			try {
				a.createNewFile();
			} catch (IOException e) {
				return;
			}
		}
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(a);
		p.sendMessage(utiltools.chat("&6======Guilds======"));
		for (String guild : yaml.getKeys(false)) {
			p.sendMessage(utiltools.chat("&6>  " + guild));

		}
		p.sendMessage(utiltools.chat("&6================="));
	}

	public void deleteGuild(String guildName, Player p) {
		File a = new File(plugin.getDataFolder() + "/" + "guilds.yml ");
		if (!a.exists()) {
			try {
				a.createNewFile();
			} catch (IOException e) {
				return;
			}
		}
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(a);
		if (yaml.contains(guildName)) {
			String owner = yaml.getConfigurationSection(guildName).getString("owner");
			if (!p.getUniqueId().toString().equals(owner)) {
				p.sendMessage(utiltools.chat("&6" + "You are not the owner of this guild!"));
			} else {
				yaml.set(guildName, null);
				try {
					yaml.save(a);
					p.sendMessage(utiltools.chat("&6" + guildName + " has been deleted!"));
				} catch (IOException e) {
					p.sendMessage(utiltools.chat("&6" + guildName + " could not be deleted"));
					e.printStackTrace();
				}
			}
		} else {
			p.sendMessage(utiltools.chat("&6" + guildName + " does not exist!"));
		}
	}

	public void addMember(Player p, String member) {
		File a = new File(plugin.getDataFolder() + "/" + "guilds.yml ");
		if (!a.exists()) {
			try {
				a.createNewFile();
			} catch (IOException e) {
				return;
			}
		}
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(a);
		for (String guild : yaml.getKeys(false)) {
			System.out.println(guild);
			//check isOwner
			if (p.getUniqueId().toString().equals(yaml.getConfigurationSection(guild).get("owner"))) {
				//check if the owner tries to join his own guild
				if (!p.getUniqueId().toString().equals(Bukkit.getOfflinePlayer(member).getUniqueId().toString())) {
					List<String> memlist = yaml.getConfigurationSection(guild).getStringList("members");
					//check if member is already member of this guild
					if (!memlist.contains(Bukkit.getOfflinePlayer(member).getUniqueId().toString())) {
						memlist.add(Bukkit.getOfflinePlayer(member).getUniqueId().toString());
					} else {
						p.sendMessage(utiltools.chat("&6" + "Member " + member + " is already in the guild!"));
						return;
					}
					yaml.getConfigurationSection(guild).set("members", memlist);
					try {
						yaml.save(a);
						p.sendMessage(utiltools.chat("&6" + "Member " + member + " has been added!"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				} else {
					p.sendMessage(utiltools.chat("&6" + "You are the owner of this guild!"));
					return;
				}
			}
		}

	}

	public void deleteMember(Player p, String member) {
		File a = new File(plugin.getDataFolder() + "/" + "guilds.yml ");
		if (!a.exists()) {
			try {
				a.createNewFile();
			} catch (IOException e) {
				return;
			}
		}
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(a);
		for (String guild : yaml.getKeys(false)) {
			if (p.getUniqueId().toString().equals(yaml.getConfigurationSection(guild).get("owner"))) {
				if (!p.getUniqueId().toString().equals(Bukkit.getOfflinePlayer(member).getUniqueId().toString())) {
					List<String> memlist = yaml.getConfigurationSection(guild).getStringList("members");
					if (memlist.contains(Bukkit.getOfflinePlayer(member).getUniqueId().toString())) {
						memlist.remove(Bukkit.getOfflinePlayer(member).getUniqueId().toString());
						yaml.getConfigurationSection(guild).set("members", memlist);
						try {
							yaml.save(a);
							p.sendMessage(
									utiltools.chat("&6" + "Member " + member + " has been removed from this guild!"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					} else {
						p.sendMessage(utiltools.chat("&6" + "Member " + member + " is not in the guild!"));
						return;
					}
				} else {
					p.sendMessage(utiltools.chat("&6" + "You are the owner of this guild!"));
					return;
				}
			}

		}
	}
}
