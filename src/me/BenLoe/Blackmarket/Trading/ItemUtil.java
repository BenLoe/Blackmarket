package me.BenLoe.Blackmarket.Trading;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	
	public static ItemStack getItem(Material m, String displayname, List<String> lore){
		ItemStack item = new ItemStack(m);
		ItemMeta itemm = item.getItemMeta();
		itemm.setDisplayName(displayname);
		itemm.setLore(lore);
		item.setItemMeta(itemm);
		return item;
	}
}
