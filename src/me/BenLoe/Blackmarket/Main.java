package me.BenLoe.Blackmarket;

import me.BenLoe.Blackmarket.Repair.Repair;
import me.BenLoe.Blackmarket.Smelter.Smelt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	Events events = new Events(this);
	Files files = new Files(this);
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(events, this);
		saveDefaultConfig();
		Smelt.Shards.clear();
		Repair.Shards.clear();
	}
}
