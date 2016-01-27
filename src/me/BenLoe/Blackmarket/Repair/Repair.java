package me.BenLoe.Blackmarket.Repair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.BenLoe.Blackmarket.Stats.Stats;
import me.BenLoe.quest.ActiveQuest;
import me.BenLoe.quest.NeededType;
import me.BenLoe.quest.QuestAPI;

import org.Prison.Tools.ToolStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Repair {
	
	public static HashMap<String,Integer> Shards = new HashMap<String,Integer>();
	public static String tag = ChatColor.YELLOW + "[" + ChatColor.AQUA + "Repair" + ChatColor.YELLOW + "]: ";

	public static void openRepairMenu1(Player p){
		if (p.getItemInHand() != null){
			ItemStack item = p.getItemInHand();
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.getLore().size() > 1){
				if (itemm.getLore().get(1).contains("Efficiency")){
					if (itemm.getDisplayName().contains("God Pickaxe") || itemm.getDisplayName().contains("§2§lH§4§lO")){
						p.sendMessage(ChatColor.RED + "Cannot repair this type of pickaxe, it is too powerful.");
						return;
					}
					ToolStats t = ToolStats.getToolStats(item);
					double shards = 0.0;
					int efficiency = t.getEfficiency();
					int fortune = t.getFortune();
					int unbreaking = t.getUnbreaking();
					int enchants = t.getEnchants();
					int durability = item.getDurability();
					
					shards += efficiency * 1.3;
					shards += fortune * 1.2;
					shards += unbreaking * 1.2;
					shards += enchants * 1.2;
					shards += durability * 0.038;
					
					int eshards = 0;
					int realshards = (int) Math.round(shards);
					if (realshards > 68){
						eshards = (int) Math.round(realshards * 0.05);
						realshards -= realshards * 0.19;
					}
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Repair pickaxe?");
					ItemStack item1 = new ItemStack(Material.ANVIL);
					ItemMeta itemm1 = item1.getItemMeta();
					itemm1.setDisplayName("§aRepair your " + itemm.getDisplayName() + "§a.");
					List<String> lore = new ArrayList<String>();
					lore.add("");
					lore.add("§7Cost: ");
					lore.add("§9" + realshards + " Pickaxe shards");
					if (eshards > 0){
						lore.add("§b" + eshards + " Enchanted shards");
					}
					if (Stats.getStats(p.getName()).getShards() < realshards || Stats.getStats(p.getName()).getEnchantedShards() < eshards){
						lore.add("§cNot enough shards.");
					}else{
						lore.add("§aClick to repair.");
					}
					lore.add("§7Close inventory to cancel.");
					itemm1.setLore(lore);
					item1.setItemMeta(itemm1);
					inv.setItem(4, item1);
					p.openInventory(inv);
					Shards.put(p.getName(), realshards);
				}
				}
			}
			}
	}
	
	public static void openRepairMenu(Player p){
		if (p.getItemInHand() != null){
			ItemStack item = p.getItemInHand();
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.getLore().size() > 1){
				if (itemm.getLore().get(1).contains("Efficiency")){
					if (itemm.getDisplayName().contains("God Pickaxe") || itemm.getDisplayName().contains("§2§lH§4§lO")){
						p.sendMessage(ChatColor.RED + "Cannot repair this type of pickaxe, it is too powerful.");
						return;
					}
					String rarity = "Common";
					if (itemm.getDisplayName().contains("§e")) rarity = "Rare";
					if (itemm.getDisplayName().contains("§5")) rarity = "Epic";
					if (itemm.getDisplayName().contains("§4")) rarity = "Ultra";
					if (itemm.getDisplayName().contains("§d")) rarity = "Custom";
					ToolStats t = ToolStats.getToolStats(item);
					double shards = 1.0;
					int efficiency = t.getEfficiency();
					int fortune = t.getFortune();
					int unbreaking = t.getUnbreaking();
					int enchants = t.getEnchants();
					int durability = item.getDurability();
					
					if (rarity.equals("Rare")){
						shards = 4.0;
					}
					if (rarity.equals("Epic") || rarity.equals("Custom")){
						shards = 7.0;
					}
					if (rarity.equals("Ultra")){
						shards = 13.0;
					}
					
					shards += efficiency * 0.6;
					shards += fortune * 0.6;
					shards += unbreaking * 0.6;
					shards += enchants * 0.6;
					shards += durability * 0.028;
					
					int realshards = (int) Math.round(shards);
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Repair pickaxe?");
					ItemStack item1 = new ItemStack(Material.ANVIL);
					ItemMeta itemm1 = item1.getItemMeta();
					itemm1.setDisplayName("§aRepair your " + itemm.getDisplayName() + "§a.");
					List<String> lore = new ArrayList<String>();
					lore.add("");
					lore.add("§7Cost: ");
					lore.add("§9" + realshards + " Pickaxe shards");
					if (Stats.getStats(p.getName()).getShards() < realshards){
						lore.add("§cNot enough shards.");
					}else{
						lore.add("§aClick to repair.");
					}
					lore.add("§7Close inventory to cancel.");
					itemm1.setLore(lore);
					item1.setItemMeta(itemm1);
					inv.setItem(4, item1);
					p.openInventory(inv);
					Shards.put(p.getName(), realshards);
				}
				}
			}
			}
	}
	
	public static void completeRepair(Player p){
		int shards = Shards.get(p.getName());
		if (Stats.getStats(p.getName()).getShards() < shards){
			p.sendMessage(ChatColor.RED + "Not enough shards.");
			Shards.remove(p.getName());
			p.closeInventory();
		}else{
			Shards.remove(p.getName());
			p.closeInventory();
			p.sendMessage(tag + "§aPickaxe repaired.");
			Stats.getStats(p.getName()).removeShards(shards);
			doRepairItem(p);
			if (QuestAPI.hasAActive(p)){
				ActiveQuest aq = ActiveQuest.getActive(p);
				if (aq.getNeededType() == NeededType.REPAIRER){
					QuestAPI.addProgress(p, 1);
				}
			}
		}
	}
	
	public static void doRepairItem(Player p){
		ItemStack item = p.getItemInHand();
		item.setDurability((short)0);
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
		p.updateInventory();
	}
}
