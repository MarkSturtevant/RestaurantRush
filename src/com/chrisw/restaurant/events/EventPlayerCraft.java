package com.chrisw.restaurant.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import com.chrisw.restaurant.Main;
import com.chrisw.restaurant.game.Foods;

public class EventPlayerCraft implements Listener {

	/*@EventHandler
	public void onCraftPrepare(PrepareItemCraftEvent e) {
		if (e.getInventory().getType() != InventoryType.WORKBENCH)
			return;
		for (Foods f : Foods.values()) {
			switch(f.isValidCraft(e.getInventory())) {
			case 0: continue;
			case 1: //if (f.product.equals(e.getInventory().getResult()))
					//	e.getInventory().setResult(null);
					continue;
			case 2: e.getInventory().setResult(f.product);
					break;
			}
		}
		
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		Main.getPlugin().getLogger().info(e.getRecipe().getResult().toString() + " | " + e.getInventory().getItem(0).toString());
		
	}*/
	
}
