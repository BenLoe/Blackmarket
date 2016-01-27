package me.BenLoe.Blackmarket.Stats;

import java.util.UUID;

import me.BenLoe.Blackmarket.Files;
import me.BenLoe.Blackmarket.UUIDFetcher;

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
		UUID uuid = new UUIDFetcher(p).callForOne();
		if (Files.getDataFile().contains("Players." + uuid + ".shards")){
			shards = Files.getDataFile().getInt("Players." + uuid + ".shards");
		}
		if (Files.getDataFile().contains("Players." + uuid + ".eshards")){
			eshards = Files.getDataFile().getInt("Players." + uuid + ".eshards");
		}
		return new Stats(shards, eshards, p);
	}
	
	public static Stats getStats(UUID uuid){
		int shards = 0;
		int eshards = 0;
		if (Files.getDataFile().contains("Players." + uuid + ".shards")){
			shards = Files.getDataFile().getInt("Players." + uuid + ".shards");
		}
		if (Files.getDataFile().contains("Players." + uuid + ".eshards")){
			eshards = Files.getDataFile().getInt("Players." + uuid + ".eshards");
		}
		return new Stats(shards, eshards, org.Prison.Main.Files.getDataFile().getString("Players." + uuid + ".Name"));
	}
	
	public int getShards(){
		return this.shards;
	}
	
	public int getEnchantedShards(){
		return this.eshards;
	}
	
	public Stats setShards(int i){
		UUID uuid = new UUIDFetcher(p).callForOne();
		Files.getDataFile().set("Players." + uuid + ".shards", i);
		Files.saveDataFile();
		return new Stats(i, eshards, p);
	}
	
	public Stats setEnchantedShards(int i){
		UUID uuid = new UUIDFetcher(p).callForOne();
		Files.getDataFile().set("Players." + uuid + ".eshards", i);
		Files.saveDataFile();
		return new Stats(shards, i, p);
	}
	
	public Stats addShards(int i){
		UUID uuid = new UUIDFetcher(p).callForOne();
		Files.getDataFile().set("Players." + uuid + ".shards", shards + i);
		Files.saveDataFile();
		return new Stats(shards + i, eshards, p);
	}
	
	public Stats addEnchantedShards(int i){
		UUID uuid = new UUIDFetcher(p).callForOne();
		Files.getDataFile().set("Players." + uuid + ".eshards", eshards + i);
		Files.saveDataFile();
		return new Stats(shards, eshards + i, p);
	}
	
	public Stats removeShards(int i){
		UUID uuid = new UUIDFetcher(p).callForOne();
		Files.getDataFile().set("Players." + uuid + ".shards", shards - i);
		Files.saveDataFile();
		return new Stats(shards - i, eshards, p);
	}
	
	public Stats removeEnchantedShards(int i){
		UUID uuid = new UUIDFetcher(p).callForOne();
		Files.getDataFile().set("Players." + uuid + ".eshards", eshards - i);
		Files.saveDataFile();
		return new Stats(shards, eshards - i, p);
	}
}
