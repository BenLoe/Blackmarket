package me.BenLoe.Blackmarket.Trading;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ConfirmState {

	NOT_CONFIRMED, PENDING, CONFIRMED;
	
	public String getName(){
		switch(this){
		case CONFIRMED:{
			return "§aConfirmed";
		}
		case NOT_CONFIRMED:{
			return "§7Click to confirm";
		}
		case PENDING:{
			return "§eAre you sure?";
		}
		}
		return "";
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getMaterial(){
		switch(this){
		case CONFIRMED:{
			return new ItemStack(35, 1, (byte)5);
		}
		case NOT_CONFIRMED:{
			return new ItemStack(35, 1, (byte)8);
		}
		case PENDING:{
			return new ItemStack(35, 1, (byte)4);
		}
		}
		return new ItemStack(Material.AIR);
	}
}
