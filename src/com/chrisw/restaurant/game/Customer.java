package com.chrisw.restaurant.game;

import com.chrisw.restaurant.Main;

import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.MineSkinFetcher;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class Customer {

    /*private final String[] NAMES = new String[]{"Jimmy", "Aaron", "Collin", "Jack's stunt double", "Rob", "Tom",
            "Chad", "Kyle", "Abraham Lincoln", "Obama", "Throckmorton", "Rwanda", "Mr. Van Derven", "Mr. Schmerker",
            "Haven", "Ryan", "Cringy Fortnite player", "Moe Lester", "Alexis Texas", "Chong", "James", "Spencer",
            "Genghis Khan", "Domino's Pizza", "Food Critic", "Lisa Wu", "Lisa uwu", "Notch", "Dream", "GeorgeNotFound",
            "GeorgeFound", "Sapnap", "Oliver Closeoff"};*/
	private final static String[] NAMES = new String[] {"Dream", "Perry", "Scary Person", "Spoderman", "Guy with Covid-19", "Thing", "Drum", "Moe Lester", "Mailbox",
			"GeorgeNotFound", "Skin from 2012", "Future Chris", "Aaron", "Dimmy", "Collin", "Evil Mark", "Old Mark", "GeorgeFound"};
	private final static int[] IDS = new int[] {1948673550, 259460336, 1254015225, 466914005, 1735182059, 1280717724, 980905873, 1141083022, 942240983,
			1715292872, 211403705, 92719632, 1750664149, 1544976754, 12108743, 920849105, 941359387, 1715292872};
    private List<ItemStack> order;
    public NPC npc;
    private int nameID;
    private Location loc;

    public Customer(int difficulty, Location loc, String restaurant) {
    	Random rand = new Random();
        this.loc = loc;
        this.loc.add(0, 0, 0);
        this.loc.setYaw(-90);
        /*v = (Villager) loc.getWorld().spawn(loc, Villager.class);
        v.setAdult();
        v.setAI(false);
        v.setInvulnerable(true);*/
        //npc = HumanEntityHandler.addEntity(loc, "Dream", "Dream");
        this.nameID = rand.nextInt(NAMES.length);
        createNPC(nameID);
        createOrder(difficulty, restaurant);
    }
    
    private void createNPC(int nameID) {
    	// create npc async
    	/*npc = Main.getNPCLib().createNPC(Arrays.asList(NAMES[nameID]));
        npc.setLocation(loc);
        npc.create();
        for (Player p : Bukkit.getOnlinePlayers())
        	npc.show(p);*/
    	MineSkinFetcher.fetchSkinFromIdAsync(IDS[nameID], skin -> {
    		npc = Main.getNPCLib().createNPC(Arrays.asList(NAMES[nameID]));
            npc.setLocation(loc);
            npc.setSkin(skin);
            
            npc.create();
            // add while in sync
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
            	for (Player p : Bukkit.getOnlinePlayers())
            		npc.show(p);
            });
    	});
    }

    private void createOrder(int difficulty, String restaurant) {
        Random rand = new Random();
        order = new ArrayList<>();
        List<Foods> mainOptions = new ArrayList<>();
        List<Foods> sideOptions = new ArrayList<>();

        for (Foods food : Foods.createMenu(restaurant)) {
            if (food.difficulty == difficulty)
                mainOptions.add(food);
            if ((food.difficulty == difficulty - 1) || (food.difficulty == difficulty - 2))
                sideOptions.add(food);
        }

        //guarantees one item of int difficulty in the order
        order.add(mainOptions.get(rand.nextInt(mainOptions.size())).product);

        //random chance to get additional items in order depending on difficulty
        if (difficulty == 2) {
            int a = rand.nextInt(4);
            if (a > 0) {
                if (rand.nextBoolean())
                    order.add(mainOptions.get(rand.nextInt(mainOptions.size())).product);
                else order.add(sideOptions.get(rand.nextInt(sideOptions.size())).product);
            }
        }
        if (difficulty == 3) {
            int a = rand.nextInt(4);
            if (a > 0)
                order.add(mainOptions.get(rand.nextInt(mainOptions.size())).product);
            else order.add(sideOptions.get(rand.nextInt(sideOptions.size())).product);
        }
        if (difficulty == 4) {
            int a = rand.nextInt(5);
            if (a > 0)
                order.add(mainOptions.get(rand.nextInt(mainOptions.size())).product);
            else order.add(sideOptions.get(rand.nextInt(sideOptions.size())).product);
            a = rand.nextInt(4);
            if (a == 0)
                order.add(sideOptions.get(rand.nextInt(sideOptions.size())).product);
        }
        if (difficulty == 5) {
            order.add(mainOptions.get(rand.nextInt(mainOptions.size())).product);
            int a = rand.nextInt(4);
            if (a == 0)
                order.add(sideOptions.get(rand.nextInt(sideOptions.size())).product);
        }
    }

    public void displayOrder(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, NAMES[nameID] + "'s order:");
        for (int i = 0; i < order.size(); i++)
            inv.setItem(i, order.get(i));
        p.closeInventory();
        p.openInventory(inv);
    }

    public boolean testOrder(Hopper hopper) {
        for (int i = 0; i < order.size(); i++)
            if (!hopper.getInventory().containsAtLeast(order.get(i), Collections.frequency(order, order.get(i))))
                return false;
        return true;
    }

    public void moveUp() {
        loc.add(1, 0, 0);
        //npc.destroy();
        //createNPC(nameID);
    }
    
    public void reload() {
    	npc.destroy();
    	createNPC(nameID);
    }

    public void blastOff() {
        npc.getWorld().spawnParticle(Particle.HEART, npc.getLocation(), 85, 1.5, 1.5, 1.5);
        npc.getWorld().playSound(npc.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        //final int taskId2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
        //	npc.getWorld().spawnParticle(Particle.FLAME, npc.getLocation().add(new Vector(0, -1, 0)), 15, 0.1, 0.1, 0.1);
        //	npc.setLocation(npc.getLocation().add(0, 0.25, 0));
        //}, 0, 1);
        //Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            npc.destroy();
         //   Bukkit.getScheduler().cancelTask(taskId2);
        //}, 40L);
    }

}
