package me.BenLoe.Blackmarket;

import java.util.UUID;

import me.BenLoe.Blackmarket.Repair.Repair;
import me.BenLoe.Blackmarket.Smelter.Smelt;
import me.BenLoe.Blackmarket.Trading.ConfirmState;
import me.BenLoe.Blackmarket.Trading.ItemType;
import me.BenLoe.Blackmarket.Trading.TradingMenu;
import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

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
		if (TradingMenu.inMenu.contains(p.getName())){
			if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
				event.setCancelled(true);
				p.updateInventory();
			}
			if (event.getClickedInventory().getName() != null){
				if (event.getClickedInventory().getName().contains("|")){
					int position = TradingMenu.tradingPos.get(p.getName());
					if (event.getAction() == InventoryAction.HOTBAR_SWAP || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
						event.setCancelled(true);
						p.updateInventory();
					}else{
					if (ItemType.cantPlace(position, event.getRawSlot())){
						event.setCancelled(true);
						p.updateInventory();
					}
					if (ItemType.hasClickableAction(position, event.getRawSlot())){
						event.setCancelled(true);
						p.updateInventory();
						ItemType.getFromSlot(event.getRawSlot(), position).wasClicked(p);
					}
					if (!ItemType.cantPlace(position, event.getRawSlot()) && !ItemType.hasClickableAction(position, event.getRawSlot())){
						event.setCancelled(false);
						Player trading = Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName()));
						TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
						TradingMenu.confirm.put(trading.getName(), ConfirmState.NOT_CONFIRMED);
						Inventory inv2 = trading.getOpenInventory().getTopInventory();
						if (event.getCursor() != null){
							inv2.setItem(event.getRawSlot(), event.getCursor());
						}else{
							inv2.setItem(event.getRawSlot(), null);
						}
						if (position == 1){
							event.getClickedInventory().setItem(30, ItemType.CONFIRM.getItem(p));
							event.getClickedInventory().setItem(32, ItemType.OTHERS_CONFIRM.getItem(trading));
							inv2.setItem(32, ItemType.CONFIRM.getItem(trading));
							inv2.setItem(30, ItemType.OTHERS_CONFIRM.getItem(p));
							}else{
							event.getClickedInventory().setItem(32, ItemType.CONFIRM.getItem(p));
							event.getClickedInventory().setItem(30, ItemType.OTHERS_CONFIRM.getItem(trading));
							inv2.setItem(30, ItemType.CONFIRM.getItem(trading));
							inv2.setItem(32, ItemType.OTHERS_CONFIRM.getItem(p));
						}
					}
					}
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
			Smelt.Money.remove(p.getName());
		}
		if (Repair.Shards.containsKey(p.getName())){
			p.sendMessage(Repair.tag + "§aCome back if you want anything repaired.");
			Repair.Shards.remove(p.getName());
		}
		if (TradingMenu.inMenu.contains(p.getName())){
			int position = TradingMenu.tradingPos.get(p.getName());
			Player p1 = null;
			Player p2 = null;
			if (position == 1){
				p1 = p;
				p2 = Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName()));
			}else{
				p2 = p;
				p1 = Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName()));
			}
			TradingMenu.cancelTrade(p1, p2, position);
		}
	}
	
	@EventHandler
	public void NPCRightClick(NPCRightClickEvent event){
		Files.reloadConfig();
		int id = event.getNPC().getId();
		if (id == 181){
			Repair.openRepairMenu(event.getClicker());
		}else
		if (id == 180){
			Smelt.OpenSmeltPickMenu(event.getClicker());
			Smelt.OpenSmeltSwordMenu(event.getClicker());	
			Smelt.OpenSmeltArmorMenu(event.getClicker());
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
			Smelt.Money.remove(p.getName());
		}
		if (Repair.Shards.containsKey(p.getName())){
			Repair.Shards.remove(p.getName());
		}
		if (TradingMenu.inMenu.contains(p.getName())){
			int position = TradingMenu.tradingPos.get(p.getName());
			if (position == 1){
				TradingMenu.cancelTrade(p, Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName())), position);
			}else{
				TradingMenu.cancelTrade(Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName())), p, position);
			}
		}
	}
	
	@EventHandler
	public void interact(PlayerInteractEntityEvent event){
		Player p = event.getPlayer();
		if (event.getRightClicked() instanceof Player){
			Player clicked = (Player) event.getRightClicked();
			if (p.isSneaking()){
				if (Main.tradeRequest.containsKey(clicked.getName()) && Main.tradeRequest.get(clicked.getName()).equals(p.getName())){
					Main.tradeRequest.remove(clicked.getName());
					Main.tradeRequest.remove(p.getName());
					Main.tradeTime.remove(clicked.getName());
					Main.tradeTime.remove(p.getName());
					TradingMenu.openTradingMenu(clicked, p);
				}else{
					if (Main.tradeRequest.containsKey(p.getName()) && Main.tradeRequest.get(p.getName()).equals(clicked.getName())){
						p.sendMessage("§cAlready sent this player a request.");
					}else{
						Main.tradeRequest.put(p.getName(), clicked.getName());
						Main.tradeTime.put(p.getName(), 30);
						p.sendMessage("§9Trade request sent to §b" + clicked.getName() + "§9. They have §b30 seconds §9to accept.");
						clicked.sendMessage("§b" + p.getName() + "§9 has requested to trade with you. You have §b30 seconds §9 to accept by shift clicking him.");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void joinEvent(PlayerJoinEvent event){
		Player p = event.getPlayer();
		if (Files.getDataFile().contains("Players." + p.getName())){
			UUID uuid = p.getUniqueId();
			Files.getDataFile().set("Players." + uuid + ".shards", Files.getDataFile().get("Players." + p.getName() + ".shards"));
			Files.getDataFile().set("Players." + uuid + ".eshards", Files.getDataFile().get("Players." + p.getName() + ".eshards"));
			Files.saveDataFile();
			Files.getDataFile().set("Players." + p.getName(), null);
			Files.saveDataFile();
		}
	}
}
