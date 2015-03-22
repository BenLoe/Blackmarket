package me.BenLoe.Blackmarket;

import me.BenLoe.Blackmarket.Repair.Repair;
import me.BenLoe.Blackmarket.Smelter.Smelt;
import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener{
	
	public static Main plugin;
	public Events(Main instance){
		plugin = instance;
	}
	
	@EventHandler
	public void InventoryChange(InventoryClickEvent event){
		Player p = (Player) event.getWhoClicked();
		if (Smelt.Shards.containsKey(p.getName())){
			if (event.getClickedInventory().getName() != null){
		if (event.getClickedInventory().getName().contains("Smelter")){
			if (event.getRawSlot() == 4){
				Smelt.completeSmelt(p);
				event.setCancelled(true);
			}else{
				event.setCancelled(true);
				p.updateInventory();
			}
		}else{
			event.setCancelled(true);
			p.updateInventory();
		}
			}
		}
		if (Repair.Shards.containsKey(p.getName())){
			if (event.getClickedInventory().getName() != null){
			if (event.getClickedInventory().getName().contains("Repair")){
				if (event.getRawSlot() == 4){
					Repair.completeRepair(p);
					event.setCancelled(true);
				}else{
					event.setCancelled(true);
					p.updateInventory();
				}
			}else{
				event.setCancelled(true);
				p.updateInventory();
			}
			}			
		}
	}
	
	@EventHandler
	public void InventoryClose(InventoryCloseEvent event){
		Player p = (Player) event.getPlayer();
		if (Smelt.Shards.containsKey(p.getName())){
			p.sendMessage(Smelt.tag + "§aCome back if you want anything smelted.");
			Smelt.Shards.remove(p.getName());
			Smelt.Enchantedshards.remove(p.getName());
			Smelt.Money.remove(p.getName());
		}
		if (Repair.Shards.containsKey(p.getName())){
			p.sendMessage(Repair.tag + "§aCome back if you want anything repaired.");
			Repair.Shards.remove(p.getName());
			Repair.EShards.remove(p.getName());
		}
	}
	
	@EventHandler
	public void NPCRightClick(NPCRightClickEvent event){
		Files.reloadConfig();
		int id = event.getNPC().getId();
		if (Files.config().getInt("Repair") == id){
			Repair.openRepairMenu(event.getClicker());
		}else{
		if (Files.config().getInt("Smelt") == id){
			Smelt.OpenSmeltMenu(event.getClicker());
		}
		}
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent event){
		Player p = event.getPlayer();
		if (Smelt.Shards.containsKey(p.getName()) || Repair.Shards.containsKey(p.getName())){
			event.setCancelled(true);
			p.updateInventory();
		}
	}
	
	@EventHandler
	public void pickupItem(PlayerPickupItemEvent event){
		Player p = event.getPlayer();
		if (Smelt.Shards.containsKey(p.getName()) || Repair.Shards.containsKey(p.getName())){
			event.setCancelled(true);
			p.updateInventory();
		}
	}
	
	@EventHandler
	public void leave(PlayerQuitEvent event){
		Player p = event.getPlayer();
		if (Smelt.Shards.containsKey(p.getName())){
			Smelt.Shards.remove(p.getName());
			Smelt.Enchantedshards.remove(p.getName());
			Smelt.Money.remove(p.getName());
		}
		if (Repair.Shards.containsKey(p.getName())){
			Repair.Shards.remove(p.getName());
			Repair.EShards.remove(p.getName());
		}
	}
}
