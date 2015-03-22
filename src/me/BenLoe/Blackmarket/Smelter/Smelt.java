package me.BenLoe.Blackmarket.Smelter;

import java.util.ArrayList;
import java.util.HashMap;





import java.util.List;

import me.BenLoe.Blackmarket.Stats.Stats;

import org.Prison.Main.Currency.MoneyAPI;
import org.Prison.Tools.ToolStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Smelt {
	
	public static String tag = ChatColor.YELLOW + "[" + ChatColor.AQUA + "Smelter" + ChatColor.YELLOW + "]: ";
	public static HashMap<String,Integer> Shards = new HashMap<String,Integer>();
	public static HashMap<String,Integer> Enchantedshards = new HashMap<String,Integer>();
	public static HashMap<String,Integer> Money = new HashMap<String,Integer>();

	public static void OpenSmeltMenu(Player p){
		if (p.getItemInHand() != null){
			ItemStack item = p.getItemInHand();
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.getLore().size() > 1){
				if (itemm.getLore().get(1).contains("Efficiency")){
					if (itemm.getDisplayName().contains("God Pickaxe")){
						p.sendMessage(ChatColor.RED + "You cannot smelt god pickaxes, they are too powerful.");
						return;
					}
					ToolStats t = ToolStats.getToolStats(item);
					double money = 0;
					double pickshards = 0.0;
					int enchantedshards = 0;
					int efficiency = t.getEfficiency();
					int fortune = t.getFortune();
					int unbreaking = t.getUnbreaking();
					int enchants = t.getEnchants();
					int durability = item.getDurability();
					
					pickshards += efficiency * 0.408;
					money += efficiency * 120;
					pickshards += fortune * 0.408;
					money += fortune * 120;
					pickshards += unbreaking * 0.408;
					money += unbreaking * 120;
					pickshards += enchants * 1.5;
					money += enchants * 120;
					pickshards -= durability * 0.0075;
					money -= durability * 0.003;
					
					int realshards = (int) Math.round(pickshards);
					int realmoney = (int) Math.round(money);
					if (realshards > 11){
						enchantedshards = (int) Math.round(realshards * 0.2);
					}
					
					if (realshards < 0) realshards = 0;
					if (realmoney < 0) realmoney = 0;
					
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Smelter");
					ItemStack invitem = new ItemStack(Material.DIAMOND_PICKAXE);
					ItemMeta invitemm = invitem.getItemMeta();
					List<String> lore = new ArrayList<String>();
					invitemm.setDisplayName("§aSmelt down your " + item.getItemMeta().getDisplayName() + "§a?");
					lore.add(" ");
					lore.add("§e§lReward: ");
					lore.add("§9" + realshards + " Pickaxe shards.");
					lore.add("§b" + enchantedshards + " Enchanted shards.");
					lore.add("§a" + realmoney + "$.");
					lore.add("");
					lore.add("§a§lClick to smelt item.");
					lore.add("§7Close inventory to cancel.");
					invitemm.setLore(lore);
					invitem.setItemMeta(invitemm);
					inv.setItem(4, invitem);
					p.openInventory(inv);
					Shards.put(p.getName(), realshards);
					Enchantedshards.put(p.getName(), enchantedshards);
					Money.put(p.getName(), realmoney);
				}
				}
			}
		}
	}
	
	public static void completeSmelt(Player p){
		int shards = Shards.get(p.getName());
		int eshards = Enchantedshards.get(p.getName());
		int money = Money.get(p.getName());
		Shards.remove(p.getName());
		Enchantedshards.remove(p.getName());
		Money.remove(p.getName());
		p.closeInventory();
		p.updateInventory();
		p.sendMessage("§e|| §e§lPickaxe smelted:");
		if (shards > 0){
			p.sendMessage("§e|| §9+ " + shards + " Pickaxe shards.");
		}
		if (eshards > 0){
			p.sendMessage("§e|| §b+ " + eshards + " Enchanted shards.");
			}
		if (money > 0){
			p.sendMessage("§e|| §a+ " + money + "$.");
			}
		if (money <= 0 && shards <= 0 && eshards <= 0){
		p.sendMessage("§c§lNo reward.");
		}
		Stats.getStats(p.getName()).addShards(shards).addEnchantedShards(eshards);
		MoneyAPI.addMoney(p, money);
		emptySlot(p);
	}
	
	public static void emptySlot(Player p){
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
		p.updateInventory();
	}
}
