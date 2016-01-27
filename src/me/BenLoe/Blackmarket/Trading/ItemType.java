package me.BenLoe.Blackmarket.Trading;

import java.util.ArrayList;
import java.util.List;

import org.Prison.Main.Currency.CrystalAPI;
import org.Prison.Main.Currency.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ItemType {

	ADD_100_MONEY(1, 6), ADD_1000_MONEY(0, 5), REMOVE_100_MONEY(3, 8), REMOVE_1000_MONEY(2, 7), ADD_100_CRYSTALS(10, 15), ADD_1000_CRYSTALS(9, 14), REMOVE_100_CRYSTALS(12, 17), REMOVE_1000_CRYSTALS(11, 16),
	CONFIRM(30, 32), OTHERS_CONFIRM(32, 30), IRON_BAR(0, 0), GIVING_MONEY(5, 3), GIVING_CRYSTALS(14, 12);
	
	private int i1, i2;
	
	private ItemType(int i1, int i2){
		this.i1 = i1;
		this.i2 = i2;
	}
	
	public int getSlot1(){
		return i1;
	}
	
	public int getSlot2(){
		return i2;
	}
	
	public static Inventory setAllItems(Inventory inv, int position, Player p){
		for (ItemType it : values()){
			if (it.equals(IRON_BAR)){
				inv.setItem(4, it.getItem(p));
				inv.setItem(13, it.getItem(p));
				inv.setItem(22, it.getItem(p));
				inv.setItem(31, it.getItem(p));
			}else{
			if (position == 1){
				inv.setItem(it.getSlot1(), it.getItem(p));
			}else{
				inv.setItem(it.getSlot2(), it.getItem(p));
			}
			}
		}
		return inv;
	}
	
	public static boolean cantPlace(int position, int slot){
		if (position == 1){
			List<Integer> slots = new ArrayList<Integer>();
			slots.add(23);
			slots.add(24);
			slots.add(25);
			slots.add(26);
			slots.add(33);
			slots.add(34);
			slots.add(35);
			slots.add(4);
			slots.add(13);
			slots.add(22);
			slots.add(31);
			slots.add(6);
			slots.add(7);
			slots.add(8);
			slots.add(15);
			slots.add(16);
			slots.add(17);
			if (slots.contains(slot)) return true;
			for (ItemType it : values()){
				if (it.getSlot1() == slot) return true;
			}
		}else{
			List<Integer> slots = new ArrayList<Integer>();
			slots.add(4);
			slots.add(13);
			slots.add(22);
			slots.add(31);
			slots.add(0);
			slots.add(1);
			slots.add(2);
			slots.add(9);
			slots.add(10);
			slots.add(11);
			slots.add(18);
			slots.add(19);
			slots.add(20);
			slots.add(21);
			slots.add(27);
			slots.add(28);
			slots.add(29);
			if (slots.contains(slot)) return true;
			for (ItemType it : values()){
				if (it.getSlot2() == slot) return true;
			}
		}
		return false;
	}
	
	public static boolean hasClickableAction(int position, int slot){
		if (position == 1){
			for (ItemType it : values()){
				if (it.getSlot1() == slot) return true;
			}
		}else{
			for (ItemType it : values()){
				if (it.getSlot2() == slot) return true;
			}
		}
		return false;
	}
	
	public ItemStack getItem(Player p){
		int money = TradingMenu.money.get(p.getName());
		int crystals = TradingMenu.crystals.get(p.getName()); 
		List<String> lore = new ArrayList<String>();
		String displayname = "";
		switch (this){
		case ADD_1000_CRYSTALS:
			if ((CrystalAPI.getCrystals(p) - crystals) >= 1000){
				displayname = "§b§lOffer +1000 crystals";
			}else{
				displayname = "§7Offer +1000 crystals";
				lore.add("§cNot enough crystals");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§b" + crystals + " crystals");
			return ItemUtil.getItem(Material.NETHER_STAR, displayname, lore);
		case ADD_1000_MONEY:
			if ((MoneyAPI.getMoney(p) - money) >= 1000){
				displayname = "§a§lOffer +1000 money";
			}else{
				displayname = "§7Offer +1000 money";
				lore.add("§cNot enough money");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§a" + money + " dollars");
			return ItemUtil.getItem(Material.EMERALD, displayname, lore);
		case ADD_100_CRYSTALS:
			if ((CrystalAPI.getCrystals(p) - crystals) >= 100){
				displayname = "§b§lOffer +100 crystals";
			}else{
				displayname = "§7Offer +100 crystals";
				lore.add("§cNot enough crystals");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§b" + crystals + " crystals");
			return ItemUtil.getItem(Material.NETHER_STAR, displayname, lore);
		case ADD_100_MONEY:
			if ((MoneyAPI.getMoney(p) - money) >= 100){
				displayname = "§a§lOffer +100 money";
			}else{
				displayname = "§7Offer +100 money";
				lore.add("§cNot enough money");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§a" + money + " dollars");
			return ItemUtil.getItem(Material.EMERALD, displayname, lore);
		case CONFIRM:
			ItemStack item = TradingMenu.confirm.get(p.getName()).getMaterial();
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName(TradingMenu.confirm.get(p.getName()).getName());
			item.setItemMeta(itemm);
			return item;
		case GIVING_CRYSTALS:
			lore.add("§b" + TradingMenu.crystals.get(TradingMenu.tradingWith.get(p.getName())) + " crystals");
			return ItemUtil.getItem(Material.QUARTZ_BLOCK, "§7They are currently offering:", lore);
		case GIVING_MONEY:
			lore.add("§a" + TradingMenu.money.get(TradingMenu.tradingWith.get(p.getName())) + " money");
			return ItemUtil.getItem(Material.EMERALD_BLOCK, "§7They are currently offering:", lore);
		case IRON_BAR:
			return ItemUtil.getItem(Material.IRON_FENCE, "§7Barrier", new ArrayList<String>());
		case REMOVE_1000_CRYSTALS:
			if (crystals >= 1000){
				displayname = "§b§lOffer -1000 crystals";
			}else{
				displayname = "§7Offer -1000 crystals";
				lore.add("§cYou are not offering that");
				lore.add("§cmany crystals");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§b" + crystals + " crystals");
			return ItemUtil.getItem(Material.NETHER_STAR, displayname, lore);
		case REMOVE_1000_MONEY:
			if (money >= 1000){
				displayname = "§a§lOffer -1000 money";
			}else{
				displayname = "§7Offer -1000 money";
				lore.add("§cYou are not offering that");
				lore.add("§cmany crystals");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§a" + money + " dollars");
			return ItemUtil.getItem(Material.EMERALD, displayname, lore);
		case REMOVE_100_CRYSTALS:
			if (crystals >= 100){
				displayname = "§b§lOffer -100 crystals";
			}else{
				displayname = "§7Offer -100 crystals";
				lore.add("§cYou are not offering that");
				lore.add("§cmany crystals");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§b" + crystals + " crystals");
			return ItemUtil.getItem(Material.NETHER_STAR, displayname, lore);
		case REMOVE_100_MONEY:
			if (money >= 100){
				displayname = "§a§lOffer -100 money";
			}else{
				displayname = "§7Offer -100 money";
				lore.add("§cYou are not offering that");
				lore.add("§cmany crystals");
			}
			lore.add("");
			lore.add("§7Currently Offering:");
			lore.add("§a" + money + " dollars");
			return ItemUtil.getItem(Material.EMERALD, displayname, lore);
		case OTHERS_CONFIRM:
			ItemStack item2 = TradingMenu.confirm.get(TradingMenu.tradingWith.get(p.getName())).getMaterial();
			ItemMeta itemm2 = item2.getItemMeta();
			itemm2.setDisplayName(TradingMenu.confirm.get(TradingMenu.tradingWith.get(p.getName())).getName());
			item2.setItemMeta(itemm2);
			return item2;
		}
		return new ItemStack(Material.AIR);
	}
	
	public void wasClicked(Player p){
		int position = TradingMenu.tradingPos.get(p.getName());
		int money = TradingMenu.money.get(p.getName());
		int crystals = TradingMenu.crystals.get(p.getName());
		Player p2 = Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName()));
		int position2 = 1;
		if (position == 1){
			position2 = 2;
		}
		switch(this){
		case ADD_1000_CRYSTALS:
			if ((CrystalAPI.getCrystals(p) - crystals) >= 1000){
				crystals += 1000;
				TradingMenu.crystals.remove(p.getName());
				TradingMenu.crystals.put(p.getName(), crystals);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		case ADD_1000_MONEY:
			if ((MoneyAPI.getMoney(p) - money) >= 1000){
				money += 1000;
				TradingMenu.money.remove(p.getName());
				TradingMenu.money.put(p.getName(), money);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		case ADD_100_CRYSTALS:
			if ((CrystalAPI.getCrystals(p) - crystals) >= 100){
				crystals += 100;
				TradingMenu.crystals.remove(p.getName());
				TradingMenu.crystals.put(p.getName(), crystals);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		case ADD_100_MONEY:
			if ((MoneyAPI.getMoney(p) - money) >= 1000){
				money += 100;
				TradingMenu.money.remove(p.getName());
				TradingMenu.money.put(p.getName(), money);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		case CONFIRM:
			boolean bool = false;
			ConfirmState conf = TradingMenu.confirm.get(p.getName());
			if (conf == ConfirmState.NOT_CONFIRMED){
				TradingMenu.confirm.put(p.getName(), ConfirmState.PENDING);
			}
			if (conf == ConfirmState.PENDING){
				TradingMenu.confirm.put(p.getName(), ConfirmState.CONFIRMED);
				if (TradingMenu.confirm.get(TradingMenu.tradingWith.get(p.getName())) == ConfirmState.CONFIRMED){
					bool = true;
					if (TradingMenu.tradingPos.get(p.getName()) == 1){
					TradingMenu.completeTrade(p, Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName())));
					}else{
						TradingMenu.completeTrade(Bukkit.getPlayer(TradingMenu.tradingWith.get(p.getName())), p);
					}
				}
			}
			if (!bool){
				setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
				setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			}
			break;
		case GIVING_CRYSTALS:
			break;
		case GIVING_MONEY:
			break;
		case IRON_BAR:
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			break;
		case OTHERS_CONFIRM:
			break;
		case REMOVE_1000_CRYSTALS:
			if (crystals >= 1000){
				crystals -= 1000;
				TradingMenu.crystals.remove(p.getName());
				TradingMenu.crystals.put(p.getName(), crystals);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		case REMOVE_1000_MONEY:
			if (money >= 1000){
				TradingMenu.money.remove(p.getName());
				TradingMenu.money.put(p.getName(), money - 1000);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		case REMOVE_100_CRYSTALS:
			if (crystals >= 100){
				crystals = crystals - 100;
				TradingMenu.crystals.remove(p.getName());
				TradingMenu.crystals.put(p.getName(), crystals);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		case REMOVE_100_MONEY:
			if (money >= 100){
				TradingMenu.money.remove(p.getName());
				TradingMenu.money.put(p.getName(), money - 100);
			}else{
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.5f, 1f);
			}
			TradingMenu.confirm.put(p.getName(), ConfirmState.NOT_CONFIRMED);
			TradingMenu.confirm.put(TradingMenu.tradingWith.get(p.getName()), ConfirmState.NOT_CONFIRMED);
			setAllItems(p.getOpenInventory().getTopInventory(), TradingMenu.tradingPos.get(p.getName()), p);
			setAllItems(p2.getOpenInventory().getTopInventory(), position2, p2);
			break;
		}
	}
	
	public static ItemType getFromSlot(int slot, int position){
		if (position == 1){
			for (ItemType it : values()){
				if (it.getSlot1() == slot) return it;
			}
		}else{
			for (ItemType it : values()){
				if (it.getSlot2() == slot) return it;
			}
		}
		return null;
	}
	
}
