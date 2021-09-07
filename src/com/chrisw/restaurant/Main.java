package com.chrisw.restaurant;

import com.chrisw.restaurant.commands.CommandRestaurant;
import com.chrisw.restaurant.commands.CommandRestaurantStop;
import com.chrisw.restaurant.events.EventPlayerClick;
import com.chrisw.restaurant.events.EventPlayerCraft;
import com.chrisw.restaurant.game.Foods;
import com.chrisw.restaurant.game.Game;

import net.jitse.npclib.NPCLib;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Plugin plugin;
    private static NPCLib npcLib;

    @Override
    public void onEnable() {
    	plugin = this;
    	npcLib = new NPCLib(this);
        registerCommands();
        registerEvents();
        Foods.addAllRecipes();
    }

    @Override
    public void onDisable() {
        if (Game.isInGame())
            Game.endGame();
    }

    private void registerCommands() {
        this.getCommand("restaurant").setExecutor(new CommandRestaurant());
        this.getCommand("reststop").setExecutor(new CommandRestaurantStop());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new EventPlayerClick(), this);
        getServer().getPluginManager().registerEvents(new EventPlayerCraft(), this);
    }

    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static NPCLib getNPCLib() {
    	return npcLib;
    }
    
    

}
