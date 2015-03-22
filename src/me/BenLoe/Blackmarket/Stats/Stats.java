package me.BenLoe.Blackmarket.Stats;

import me.BenLoe.Blackmarket.Files;

public class Stats {

	private int shards, eshards;
	private String p;
	
	public Stats(int shards, int eshards, String p){
		this.shards = shards;
		this.eshards = eshards;
		this.p = p;
	}
	
	public static Stats getStats(String p){
		int shards = 0;
		int eshards = 0;
		if (Files.getDataFile().contains("Players." + p + ".shards")){
			shards = Files.getDataFile().getInt("Players." + p + ".shards");
		}
		if (Files.getDataFile().contains("Players." + p + ".eshards")){
			eshards = Files.getDataFile().getInt("Players." + p + ".eshards");
		}
		return new Stats(shards, eshards, p);
	}
	
	public int getShards(){
		return this.shards;
	}
	
	public int getEnchantedShards(){
		return this.eshards;
	}
	
	public Stats setShards(int i){
		Files.getDataFile().set("Players." + p + ".shards", i);
		Files.saveDataFile();
		return new Stats(i, eshards, p);
	}
	
	public Stats setEnchantedShards(int i){
		Files.getDataFile().set("Players." + p + ".eshards", i);
		Files.saveDataFile();
		return new Stats(shards, i, p);
	}
	
	public Stats addShards(int i){
		Files.getDataFile().set("Players." + p + ".shards", shards + i);
		Files.saveDataFile();
		return new Stats(shards + i, eshards, p);
	}
	
	public Stats addEnchantedShards(int i){
		Files.getDataFile().set("Players." + p + ".eshards", eshards + i);
		Files.saveDataFile();
		return new Stats(shards, eshards + i, p);
	}
	
	public Stats removeShards(int i){
		Files.getDataFile().set("Players." + p + ".shards", shards - i);
		Files.saveDataFile();
		return new Stats(shards - i, eshards, p);
	}
	
	public Stats removeEnchantedShards(int i){
		Files.getDataFile().set("Players." + p + ".eshards", eshards - i);
		Files.saveDataFile();
		return new Stats(shards, eshards - i, p);
	}
}
