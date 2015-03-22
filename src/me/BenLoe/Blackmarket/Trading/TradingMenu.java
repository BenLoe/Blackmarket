package me.BenLoe.Blackmarket.Trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.Inventory;

public class TradingMenu {

	public static List<String> inMenu = new ArrayList<String>();
	public static HashMap<String,String> tradingWith = new HashMap<String,String>();
	public static HashMap<String,Integer> tradingPos = new HashMap<String,Integer>();
	public static HashMap<String,ConfirmState> confirm = new HashMap<String,ConfirmState>();
	public static HashMap<String,Integer> crystals = new HashMap<String,Integer>();
	public static HashMap<String,Integer> money = new HashMap<String,Integer>();
	public static HashMap<String,Inventory> inventory = new HashMap<String,Inventory>();
}
