package com.chrisw.restaurant.game;

import com.chrisw.restaurant.Main;

import net.jitse.npclib.api.NPC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	
	public static final ItemStack RECIPE_BOOK = Foods.custom(Material.BOOK, ChatColor.GRAY + "Recipe Book");

    public static final int[] TIMES = new int[]{20 * 60, 20 * (60 + 20), 20 * (60 + 40), 20 * (60 + 60 + 15), 20 * (60 + 60 + 45)};
    public static String restaurant;
    private static BossBar bossbar;
    private static boolean inGame;
    private static int numOrders;
    private static int numCustomers;
    private static int timer;
    private static int taskID;
    private static int day;
    private static int maxTime;
    private static int[] vilTimes;
    private static List<Customer>[] lines;
    private static Location spawn;
    private static Hopper[] hoppers;
    private static Random rand;

    public static void startGame(Player p, String r) {
        inGame = true;
        numOrders = 0;
        timer = 0;
        day = 1;
        restaurant = r;
        lines = new List[3];
        for (int i = 0; i < 3; i++)
            lines[i] = new ArrayList<>();
        spawn = p.getLocation();
        rand = new Random();
        hoppers = new Hopper[3];
        bossbar = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
        for (Player p2 : Bukkit.getOnlinePlayers()) {
        	bossbar.addPlayer(p2);
        	p2.getInventory().setItem(8, RECIPE_BOOK);
        }
        	
        for (int i = 0; i < 3; i++)
            hoppers[i] = (Hopper) p.getWorld().getBlockAt(spawn.getBlockX() + 2, spawn.getBlockY() - 1, spawn.getBlockZ() + i * 2 + 1).getState();

        beginDay(); //instantiates numcustomers, maxtime, viltimes

        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
            onTick();
        }, 0L, 1L);
    }

    public static boolean isInGame() {
        return inGame;
    }

    public static void onNPCClick(Player p, NPC npc) {
        outerloop: for (int i = 0; i < 3; i++) {
            for (Customer c : lines[i]) {
                if (c.npc.getId().equals(npc.getId())) {
                    c.displayOrder(p);
                    break outerloop;
                }
            }
        }
    }
    
    public static void onSpecHopperClick(Player p, Hopper h) {
    	for (int i = 0; i < 3; ++i) {
    		if (hoppers[i].equals(h)) {
    			if (lines[i].size() > 0)
    				lines[i].get(0).displayOrder(p);
    			break;
    		}
    	}
    }

    private static void onTick() {
        //increment and check timer
        timer++;
        if (timer >= maxTime)
            if (numCustomers == 0)
                endDay();
        if (timer >= TIMES[day - 1]) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendTitle(ChatColor.BOLD + "" + ChatColor.RED + "" + "OOF", "u lost :(", 10, 40, 10);
            endGame();
        }
        
        // timer set
        int timeAdj = (TIMES[day - 1] - timer) / 20;
        bossbar.setTitle(ChatColor.YELLOW + String.format("%02d:%02d Remaining", timeAdj / 60, timeAdj % 60));

        //spawn customers
        for (int i = 0; i < vilTimes.length; i++) {
            if (timer == vilTimes[i]) {
                int a = rand.nextInt(3);
                lines[a].add(new Customer(day, new Location(spawn.getWorld(), spawn.getX() - lines[a].size(), spawn.getY(), spawn.getZ() + a * 2 + 1), restaurant));
            }
        }

        //test orders
        for (int i = 0; i < 3; i++) {
            if ((lines[i].size() > 0) && (lines[i].get(0).testOrder(hoppers[i]))) {
                numOrders++;
                numCustomers--;
                lines[i].remove(0).blastOff();
                hoppers[i].getInventory().clear();
                lines[i].forEach(Customer::moveUp);
                for (int j = 0; j < 3; ++j)
                	lines[j].forEach(Customer::reload);
            }
        }
    }

    public static void beginDay() {
        numCustomers = rand.nextInt(3) + 2 + day;
        maxTime = TIMES[day - 1] - 20 * (10 + 5 * day);
        vilTimes = new int[numCustomers];
        int maxAvgTime = maxTime / 2; // adj for specific day, and put into ticks
        int decrVal = 25; // increase for faster runtime.  Too high and will be finicky
        for (int i = 0; i < numCustomers; ++i)
            vilTimes[i] = rand.nextInt(maxTime);
        int sum;
        while (true) {
            sum = 0;
            for (int i = 0; i < numCustomers; ++i)
                sum += vilTimes[i];
            if (sum / numCustomers <= maxAvgTime)
                break;
            for (int i = 0; i < numCustomers; ++i)
                if (vilTimes[i] >= decrVal)
                    vilTimes[i] -= decrVal;
        }
        //state day and num villagers
        for (Player p : Bukkit.getOnlinePlayers())
            p.sendTitle(ChatColor.BOLD + "" + ChatColor.GOLD + "" + "Day " + day, "Customers to serve today: " + numCustomers, 10, 40, 10);
    }

    public static void endDay() {
        for (LivingEntity le : spawn.getWorld().getLivingEntities()) {
            if ((le instanceof Villager) && (le.getLocation().distance(spawn) < 15))
                le.remove();
        }
        numOrders = 0;
        timer = 0;
        if (++day == 6)
            endGame();
        else beginDay();
    }

    public static void endGame() {
        for (Player p : Bukkit.getOnlinePlayers()) {
        	bossbar.removePlayer(p);
        	p.sendMessage(ChatColor.ITALIC + "" + ChatColor.AQUA + "Orders completed today: " + numOrders);
        }
        //HumanEntityHandler.clearEntityPlayers();
        Bukkit.getServer().getScheduler().cancelTask(taskID);
        inGame = false;
        //for (LivingEntity le : spawn.getWorld().getLivingEntities())
         //   if ((le instanceof Villager) && (le.getLocation().distance(spawn) < 15))
         //       le.remove();
        for (int i = 0; i < 3; i++)
            for (Customer c : lines[i])
                c.npc.destroy();
    }

}
