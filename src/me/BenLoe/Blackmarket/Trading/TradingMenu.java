package me.BenLoe.Blackmarket.Trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.Prison.Main.Currency.CrystalAPI;
import org.Prison.Main.Currency.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;

public class TradingMenu {

	public static List<String> inMenu = new ArrayList<String>();
	public static HashMap<String,String> tradingWith = new HashMap<String,String>();
	public static HashMap<String,Integer> tradingPos = new HashMap<String,Integer>();
	public static HashMap<String,ConfirmState> confirm = new HashMap<String,ConfirmState>();
	public static HashMap<String,Integer> crystals = new HashMap<String,Integer>();
	public static HashMap<String,Integer> money = new HashMap<String,Integer>();
	
	
	public static void openTradingMenu(Player p1, Player p2){
		Inventory inv1 = Bukkit.createInventory(null, 4*9, ChatColor.BLUE + "" + p1.getName() + ChatColor.GREEN + " | " + ChatColor.BLUE + p2.getName());
		Inventory inv2 = Bukkit.createInventory(null, 4*9, ChatColor.BLUE + "" + p1.getName() + ChatColor.GREEN + " | " + ChatColor.BLUE + p2.getName());
		inMenu.add(p1.getName());
		inMenu.add(p2.getName());
		tradingWith.put(p1.getName(), p2.getName());
		tradingWith.put(p2.getName(), p1.getName());
		tradingPos.put(p1.getName(), 1);
		tradingPos.put(p2.getName(), 2);
		confirm.put(p1.getName(), ConfirmState.NOT_CONFIRMED);
		confirm.put(p2.getName(), ConfirmState.NOT_CONFIRMED);
		crystals.put(p1.getName(), 0);
		crystals.put(p2.getName(), 0);
		money.put(p1.getName(), 0);
		money.put(p2.getName(), 0);
		Inventory inv11 = ItemType.setAllItems(inv1, 1, p1);
		Inventory inv22 = ItemType.setAllItems(inv2, 2, p2);
		p1.openInventory(inv11);
		p2.openInventory(inv22);
	}
	
	public static void completeTrade(Player p1, Player p2){
		Inventory inv1 = p1.getOpenInventory().getTopInventory();
		Inventory inv2 = p2.getOpenInventory().getTopInventory();
		List<Integer> slots = new ArrayList<Integer>();
		slots.add(23);
		slots.add(24);
		slots.add(25);
		slots.add(26);
		slots.add(33);
		slots.add(34);
		slots.add(35);
		for (Integer i : slots){
			if (inv2.getItem(i) != null){
				p1.getInventory().addItem(inv2.getItem(i));
				p1.updateInventory();
			}
		}
		slots.clear();
		slots.add(18);
		slots.add(19);
		slots.add(20);
		slots.add(21);
		slots.add(27);
		slots.add(28);
		slots.add(29);
		for (Integer i : slots){
			if (inv1.getItem(i) != null){
				p2.getInventory().addItem(inv1.getItem(i));
				p2.updateInventory();
			}
		}
		int money1 = money.get(p2.getName());
		int money2 = money.get(p1.getName());
		int crystals1 = crystals.get(p2.getName());
		int crystals2 = crystals.get(p1.getName());
		p1.sendMessage("§e|| §a§lTrade Completed:");	
		p1.sendMessage("§e|| §a+" + money1 + "$");
		p1.sendMessage("§e|| §b+" + crystals1 + " crystals");
		p2.sendMessage("§e|| §a§lTrade Completed:");	
		p2.sendMessage("§e|| §a+" + money2 + "$");
		p2.sendMessage("§e|| §b+" + crystals2 + " crystals");
		inMenu.remove(p1.getName());
		inMenu.remove(p2.getName());
		if (money1 > 0){
			MoneyAPI.addMoney(p1, money1);
			MoneyAPI.removeMoney(p2, money1);
		}
		if (money2 > 0){
			MoneyAPI.addMoney(p2, money2);
			MoneyAPI.removeMoney(p1, money2);
		}
		if (crystals1 > 0){
			CrystalAPI.addCrystals(p1, crystals1);
			CrystalAPI.removeCrystals(p2, crystals1);
		}
		if (crystals2 > 0){
			CrystalAPI.addCrystals(p2, crystals2);
			CrystalAPI.removeCrystals(p1, crystals2);
		}
		p1.closeInventory();
		p2.closeInventory();
		tradingWith.remove(p1.getName());
		tradingWith.remove(p2.getName());
		tradingPos.remove(p1.getName());
		tradingPos.remove(p2.getName());
		confirm.remove(p1.getName());
		confirm.remove(p2.getName());
		crystals.remove(p1.getName());
		crystals.remove(p2.getName());
		money.remove(p1.getName());
		money.remove(p2.getName());
	}
	
	public static void cancelTrade(Player p1, Player p2, int positionThatLeft){
		Inventory inv1 = p1.getOpenInventory().getTopInventory();
		Inventory inv2 = p2.getOpenInventory().getTopInventory();
		List<Integer> slots = new ArrayList<Integer>();
		slots.add(23);
		slots.add(24);
		slots.add(25);
		slots.add(26);
		slots.add(33);
		slots.add(34);
		slots.add(35);
		for (Integer i : slots){
			if (inv2.getItem(i) != null){
				p2.getInventory().addItem(inv2.getItem(i));
			}
		}
		slots.clear();
		slots.add(18);
		slots.add(19);
		slots.add(20);
		slots.add(21);
		slots.add(27);
		slots.add(28);
		slots.add(29);
		for (Integer i : slots){
			if (inv1.getItem(i) != null){
				p1.getInventory().addItem(inv1.getItem(i));
			}
		}
		inMenu.remove(p1.getName());
		inMenu.remove(p2.getName());
		p1.updateInventory();
		p2.updateInventory();
		if (positionThatLeft == 1){
			p2.closeInventory();
			p2.sendMessage("§c§lOther player left the trade.");
			p1.sendMessage("§c§lYou left the trade.");
		}else{
			p1.closeInventory();
			p1.sendMessage("§c§lOther player left the trade.");
			p2.sendMessage("§c§lYou left the trade.");
		}
		tradingWith.remove(p1.getName());
		tradingWith.remove(p2.getName());
		tradingPos.remove(p1.getName());
		tradingPos.remove(p2.getName());
		confirm.remove(p1.getName());
		confirm.remove(p2.getName());
		crystals.remove(p1.getName());
		crystals.remove(p2.getName());
		money.remove(p1.getName());
		money.remove(p2.getName());
	}
}
