package me.BenLoe.Blackmarket;

import java.util.HashMap;

import me.BenLoe.Blackmarket.Repair.Repair;
import me.BenLoe.Blackmarket.Smelter.Smelt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	Events events = new Events(this);
	Files files = new Files(this);
	public static HashMap<String,String> tradeRequest = new HashMap<String,String>();
	public static HashMap<String,Integer> tradeTime = new HashMap<String,Integer>();
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(events, this);
		saveDefaultConfig();
		Smelt.Shards.clear();
		Repair.Shards.clear();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				for (Player p : Bukkit.getOnlinePlayers()){
					if (tradeTime.containsKey(p.getName())){
						int newtime = tradeTime.get(p.getName()) - 1;
						tradeTime.remove(p.getName());
						if (newtime < 0){
							tradeRequest.remove(p.getName());
						}else{
							tradeTime.put(p.getName(), newtime);
						}
					}
				}
			}
		}, 0l, 20l);
	}
}
