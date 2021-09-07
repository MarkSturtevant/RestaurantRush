package com.chrisw.restaurant.events;

//import com.chrisw.restaurant.Main;
import com.chrisw.restaurant.game.Foods;
import com.chrisw.restaurant.game.Game;

import net.jitse.npclib.api.events.NPCInteractEvent;

import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EventPlayerClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
    	// test custom crafting: infinite food bug fix
    	/*if (e.getInventory().getType() == InventoryType.WORKBENCH && e.getSlot() == 0) {
    		ItemStack is = e.getInventory().getItem(0);
    		if (is != null)
    			for (Foods f : Foods.values()) {
    				if (is.equals(f.product) && f.recipe != null) {
    					for (int i = 1; i < 10; ++i) {
    						ItemStack is2 = e.getInventory().getItem(i);
    						if (is2 != null && is2.getAmount() > 1)
    						is2.setAmount(is2.getAmount() - 1);
    					}
    				}
    			}
    	}*/
        if (e.getView().getTitle() == null)
            return;
        else if (e.getView().getTitle().endsWith("order:") || e.getView().getTitle().equals("Chris's Recipe Book")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked != null)
                Foods.openValidRecipeInventory((Player) e.getWhoClicked(), clicked);
        }
        else if (e.getView().getTitle().equals("Chris's Cookbook"))
            e.setCancelled(true);
    }

    @EventHandler
    public void playerEntityClick(PlayerInteractAtEntityEvent e) {
        if (! Game.isInGame() || e.getHand() == null || e.getHand().equals(EquipmentSlot.OFF_HAND))
            return;
        if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
        	e.setCancelled(true);
        	ItemFrame itf = (ItemFrame) e.getRightClicked();
        	if (itf.getItem() != null)
        		e.getPlayer().getInventory().addItem(itf.getItem());
        } else if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().equals(Game.RECIPE_BOOK))
        	Foods.openRecipeBook(e.getPlayer());
        
    }
    
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
    	if (! Game.isInGame())
    		return;
    	if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().equals(Game.RECIPE_BOOK)) {
    		if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.HOPPER)
    			Game.onSpecHopperClick(e.getPlayer(), (Hopper) e.getClickedBlock().getState());
    		else Foods.openRecipeBook(e.getPlayer());
    	}
        	
    }
    
    @EventHandler
    public void onEntityClick(NPCInteractEvent e) {
    	Game.onNPCClick(e.getWhoClicked(), e.getNPC());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (Game.isInGame() && (e.getInventory().getType() == InventoryType.MERCHANT))
            e.setCancelled(true);
    }

}