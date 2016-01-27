package me.BenLoe.Blackmarket.Smelter;

import java.util.ArrayList;
import java.util.HashMap;





import java.util.List;

import me.BenLoe.Blackmarket.Stats.Stats;
import me.BenLoe.quest.ActiveQuest;
import me.BenLoe.quest.NeededType;
import me.BenLoe.quest.QuestAPI;

import org.Prison.Main.Currency.MoneyAPI;
import org.Prison.Main.Letter.LetterType;
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
	public static HashMap<String,Integer> Money = new HashMap<String,Integer>();

	public static void OpenSmeltSwordMenu(Player p){
		if (p.getItemInHand() != null){
			ItemStack item = p.getItemInHand();
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.getLore().size() > 1){
				if (itemm.getLore().get(1).contains("Sharpness")){
					String rarity = "Common";
					if (itemm.getDisplayName().contains("§e")) rarity = "Rare";
					if (itemm.getDisplayName().contains("§5")) rarity = "Epic";
					if (itemm.getDisplayName().contains("§4")) rarity = "Ultra";
					int durability = item.getDurability();
					double money = 1.0;
					if (rarity.equals("Rare")){
						money = 1.2;
					}
					if (rarity.equals("Epic")){
						money = 1.4;
					}
					if (rarity.equals("Ultra")){
						money = 2.0;
					}
					
					money -= durability * 0.003;
					
					int needed = 0;
					if (LetterType.getPlayerLetter(p).equals(LetterType.A)){
						needed = 100;
					}else{
						needed = Math.round(LetterType.getPlayerLetter(p).getNeeded().getMoney() / 45);
					}
					
					money *= needed;
					
					int realmoney = (int) Math.round(money);	
					if (realmoney < 0) realmoney = 0;
					
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Smelter");
					ItemStack invitem = new ItemStack(Material.DIAMOND_SWORD);
					ItemMeta invitemm = invitem.getItemMeta();
					List<String> lore = new ArrayList<String>();
					invitemm.setDisplayName("§aSmelt down your " + item.getItemMeta().getDisplayName() + "§a?");
					lore.add(" ");
					lore.add("§e§lReward: ");
					lore.add("§a" + realmoney + "$.");
					lore.add("");
					lore.add("§a§lClick to smelt item.");
					lore.add("§c§lClose inventory to cancel.");
					invitemm.setLore(lore);
					invitem.setItemMeta(invitemm);
					inv.setItem(4, invitem);
					p.openInventory(inv);
					Shards.put(p.getName(), 0);
					Money.put(p.getName(), realmoney);
				}
				}
			}
		}
	}
	
	public static void OpenSmeltArmorMenu(Player p){
		if (p.getItemInHand() != null){
			ItemStack item = p.getItemInHand();
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.getLore().size() > 1){
				if (itemm.getLore().get(1).contains("Protection")){
					String rarity = "Common";
					if (itemm.getDisplayName().contains("§e")) rarity = "Rare";
					if (itemm.getDisplayName().contains("§5")) rarity = "Epic";
					if (itemm.getDisplayName().contains("§4")) rarity = "Ultra";
					int durability = item.getDurability();
					double money = 1.0;
					if (rarity.equals("Rare")){
						money = 1.1;
					}
					if (rarity.equals("Epic")){
						money = 1.3;
					}
					if (rarity.equals("Ultra")){
						money = 1.6;
					}
					
					money -= durability * 0.002;
					
					int needed = 0;
					if (LetterType.getPlayerLetter(p).equals(LetterType.A)){
						needed = 100;
					}else{
						needed = Math.round(LetterType.getPlayerLetter(p).getNeeded().getMoney() / 50);
					}
					
					money *= needed;
					
					int realmoney = (int) Math.round(money);	
					if (realmoney < 0) realmoney = 0;
					
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Smelter");
					ItemStack invitem = new ItemStack(p.getItemInHand().getType());
					ItemMeta invitemm = invitem.getItemMeta();
					List<String> lore = new ArrayList<String>();
					invitemm.setDisplayName("§aSmelt down your " + item.getItemMeta().getDisplayName() + "§a?");
					lore.add(" ");
					lore.add("§e§lReward: ");
					lore.add("§a" + realmoney + "$.");
					lore.add("");
					lore.add("§a§lClick to smelt item.");
					lore.add("§c§lClose inventory to cancel.");
					invitemm.setLore(lore);
					invitem.setItemMeta(invitemm);
					inv.setItem(4, invitem);
					p.openInventory(inv);
					Shards.put(p.getName(), 0);
					Money.put(p.getName(), realmoney);
				}
				}
			}
		}
	}
	
	public static void OpenSmeltPickMenu(Player p){
		if (p.getItemInHand() != null){
			ItemStack item = p.getItemInHand();
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.getLore().size() > 1){
				if (itemm.getLore().get(1).contains("Efficiency")){
					if (itemm.getDisplayName().contains("God Pickaxe") || itemm.getDisplayName().contains("§2§lH§4§lO")){
						p.sendMessage(ChatColor.RED + "Cannot smelt this type of pickaxe, it is too powerful.");
						return;
					}
					String rarity = "Common";
					if (itemm.getDisplayName().contains("§e")) rarity = "Rare";
					if (itemm.getDisplayName().contains("§5")) rarity = "Epic";
					if (itemm.getDisplayName().contains("§4")) rarity = "Ultra";
					if (itemm.getDisplayName().contains("§d")){ p.sendMessage(ChatColor.RED + "Cannot smelt custom picks"); return;} 
					ToolStats t = ToolStats.getToolStats(item);
					double money = 0.4;
					double pickshards = 0.1;
					int efficiency = t.getEfficiency();
					int fortune = t.getFortune();
					int unbreaking = t.getUnbreaking();
					int enchants = t.getEnchants();
					int durability = item.getDurability();
					
					if (rarity.equals("Rare")){
						pickshards = 2.0;
					}
					if (rarity.equals("Epic")){
						pickshards = 4.0;
					}
					if (rarity.equals("Ultra")){
						pickshards = 9.0;
					}
					
					pickshards += efficiency * 0.25;
					money += efficiency * 0.05;
					pickshards += fortune * 0.25;
					money += fortune * 0.05;
					pickshards += unbreaking * 0.25;
					money += unbreaking * 0.05;
					pickshards += enchants * 1.5;
					money += enchants * 0.05;
					pickshards -= durability * 0.0075;
					money -= durability * 0.003;
					
					int needed = 0;
					if (LetterType.getPlayerLetter(p).equals(LetterType.A)){
						needed = 100;
					}else{
						needed = Math.round(LetterType.getPlayerLetter(p).getNeeded().getMoney() / 80);
					}
					
					money *= needed;
					
					int realshards = (int) Math.round(pickshards);
					int realmoney = (int) Math.round(money);	
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
					lore.add("§a" + realmoney + "$.");
					lore.add("");
					lore.add("§a§lClick to smelt item.");
					lore.add("§c§lClose inventory to cancel.");
					invitemm.setLore(lore);
					invitem.setItemMeta(invitemm);
					inv.setItem(4, invitem);
					p.openInventory(inv);
					Shards.put(p.getName(), realshards);
					Money.put(p.getName(), realmoney);
				}
				}
			}
		}
	}
	
	public static void completeSmelt(Player p){
		int shards = Shards.get(p.getName());
		int money = Money.get(p.getName());
		Shards.remove(p.getName());
		Money.remove(p.getName());
		p.closeInventory();
		p.updateInventory();
		p.sendMessage("§e|| §e§lTool smelted:");
		if (shards > 0){
			p.sendMessage("§e|| §9+ " + shards + " Pickaxe shards.");
		}
		if (money > 0){
			p.sendMessage("§e|| §a+ " + money + "$.");
			}
		if (money <= 0 && shards <= 0){
		p.sendMessage("§c§lNo reward.");
		}
		Stats.getStats(p.getName()).addShards(shards);
		MoneyAPI.addMoney(p, money);
		emptySlot(p);
		if (QuestAPI.hasAActive(p)){
			ActiveQuest aq = ActiveQuest.getActive(p);
			if (aq.getNeededType() == NeededType.SMELTER){
				QuestAPI.addProgress(p, 1);
			}
		}
	}
	
	public static void emptySlot(Player p){
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
		p.updateInventory();
	}
}
