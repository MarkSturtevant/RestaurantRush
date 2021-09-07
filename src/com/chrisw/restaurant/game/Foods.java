package com.chrisw.restaurant.game;

import com.chrisw.restaurant.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Foods {

    /* Difficulties:
    1 - Short crafting recipe, same type items
    2 - Any easy smelting or more diverse crafting recipes
    3 - Complex crafting recipes or 1 smelted item implemented in crafting
    4 - 2 smelted items implemented in crafting or complex custom recipe
    5 - 3+ smelted items, HARDDDD */

	BREAD(1, new ItemStack(Material.BREAD), null),
    APPLE(1, new ItemStack(Material.APPLE), null),
    COOKIE(1, new ItemStack(Material.COOKIE), null),
    MELON(1, new ItemStack(Material.MELON_SLICE), null),
    CARROT(1, new ItemStack(Material.CARROT), null),
    MUSHROOM_STEW(1, new ItemStack(Material.MUSHROOM_STEW), null),
    BEETROOT_STEW(1, new ItemStack(Material.BEETROOT_SOUP), null),
    STEAK(2, new ItemStack(Material.COOKED_BEEF), null),
    LAMB(2, new ItemStack(Material.COOKED_MUTTON), null),
    CHICKEN(2, new ItemStack(Material.COOKED_CHICKEN), null),
    PORK(2, new ItemStack(Material.COOKED_PORKCHOP), null),
    RABBIT(2, new ItemStack(Material.COOKED_RABBIT), null),
    CHEESE(2, Foods.custom(Material.SPONGE, ChatColor.YELLOW + "Cheese"), "F", Material.MILK_BUCKET),
    PUMPKIN_PIE(2, new ItemStack(Material.PUMPKIN_PIE), null),
    CARAMEL(2, Foods.custom(Material.GUNPOWDER, ChatColor.GOLD + "Caramel"), "F", Material.SUGAR),
    BAKED_POTATO(2, new ItemStack(Material.BAKED_POTATO), null),
    CAKE(3, new ItemStack(Material.CAKE), null),
    APPLE_TART(3, Foods.custHead("muffin", ChatColor.RED + "Apple Tart"), "L001223", Material.WHEAT, Material.EGG, Material.SUGAR, Material.APPLE),
    HAMBURGER(3, Foods.custHead("hamburger", ChatColor.DARK_RED + "Hamburger"), "S 0  1  0 ", Material.BREAD, Material.COOKED_BEEF),
    CHEESEBURGER(4, Foods.custHead("cheeseburger", ChatColor.GOLD + "Cheeseburger"), "S 0  12 0 ", Material.BREAD, Material.COOKED_BEEF, CHEESE),
    LOADED_POTATO(4, Foods.custHead("potatoe", ChatColor.GREEN + "Loaded Baked Potato"), "L0112234", Material.BAKED_POTATO, Material.SPONGE, Material.TALL_GRASS, Material.COOKED_PORKCHOP, Material.BROWN_MUSHROOM),
    RABBIT_STEW(4, new ItemStack(Material.RABBIT_STEW), null),
    SUPREME_PIZZA(4, Foods.custom(Material.COOKIE, ChatColor.RED + "Supreme Pizza"), "L012344555", Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.OAK_LEAVES, Material.COOKED_BEEF, Material.SPONGE, Material.BREAD),
    PIG_BLANKET(4, Foods.custHead("pigblanket", ChatColor.RED + "Pig in a Blanket"), "S 0  12 0 ", Material.BREAD, Material.COOKED_PORKCHOP, Material.SPONGE),
    KFC_BUCKET(5, Foods.custHead("kfc", ChatColor.DARK_GRAY + "KFC Bucket"), "L010101020", Material.COOKED_CHICKEN, Material.BREAD, Material.BUCKET),
    GORDON_BURGER(5, Foods.custHead("gordonburger", ChatColor.DARK_GRAY + "Spicy Gordon Ramsey Burger"), "L304217506", Material.BREAD, Material.COOKED_BEEF, Material.SPONGE, Material.OAK_LEAVES, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.LAVA_BUCKET, Material.BEETROOT),
    MEAT_PIZZA(5, Foods.custom(Material.COOKIE, ChatColor.RED + "Meat Lovers Pizza"), "L012344555", Material.COOKED_PORKCHOP, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_MUTTON, Material.SPONGE, Material.BREAD),
    
    SOY_MILK(1, Foods.custom(Material.MILK_BUCKET, "Soy Milk"), "L01", Material.WHEAT_SEEDS, Material.MILK_BUCKET),
    STEAMED_RICE(1, Foods.custom(Material.BONE_MEAL, "Steamed Rice", 2), "F", Material.PUMPKIN_SEEDS),
    NOODLES(1, Foods.custom(Material.STRING, "Noodles"), "F", Material.HAY_BLOCK),
    TOFU(2, Foods.custom(Material.WHITE_WOOL, "Tofu"), "F", SOY_MILK),
    SALMONROLL(2, Foods.custHead("sushi3", ChatColor.GOLD + "Salmon Roll"), "L0012", STEAMED_RICE, Material.COOKED_SALMON, Material.SEAGRASS),
    ONIGIRI(2, Foods.custHead("onigiri", ChatColor.DARK_GREEN + "Onigiri"), "L0122", STEAMED_RICE, Material.SALMON, Material.SEAGRASS),
    MISO_SOUP(3, Foods.custHead("misoSoup", ChatColor.GOLD + "Miso Soup"), "L0123", Material.BOWL, Material.WHEAT_SEEDS, TOFU, Material.SEAGRASS),
    EGG_F_RICE(3, Foods.custHead("eggRice", ChatColor.YELLOW + "Egg Fried Rice"), "S010222 3 ", Material.CARROT, Material.EGG, STEAMED_RICE, Material.BOWL),
    VEG_RICEBOWL(3, Foods.custHead("vegRice", ChatColor.GREEN + "Vegetable Rice Bowl"), "S012333 4 ", Material.CARROT, Material.SEAGRASS, Material.OAK_LEAVES, STEAMED_RICE, Material.BOWL),
    SPICYROLL(4, Foods.custHead("sushi1", ChatColor.RED + "Spicy Roll"), "L010232020", STEAMED_RICE, Material.LAVA_BUCKET, Material.SEAGRASS, Material.COOKED_COD),
    CODSWALLOP(4, Foods.custHead("codswallop", ChatColor.GRAY + "Codswallop's Head"), "S   01234 ", Material.SEAGRASS, TOFU, Material.COOKED_COD, STEAMED_RICE, Material.WATER_BUCKET),
    MUSHROOMROLL(4, Foods.custHead("sushi2", ChatColor.GOLD + "Mushroom Roll"), "L012345000", STEAMED_RICE, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM, Material.SEAGRASS, Material.COOKED_COD, TOFU),
    MISO_RAMEN(4, Foods.custHead("ramen", ChatColor.GOLD + "Miso Ramen"), "L012334", Material.WHEAT_SEEDS, Material.SEAGRASS, TOFU, NOODLES, Material.BOWL),
    CHONGQING_NOODLES(5, Foods.custHead("chongqing", ChatColor.RED + "Chongqing Noodles"), "S001233 4 ", Material.COOKED_BEEF, Material.OAK_LEAVES, Material.LAVA_BUCKET, NOODLES, Material.BOWL),
    FISH_RICEBOWL(5, Foods.custHead("fishBowl", ChatColor.AQUA + "Assorted Fish Rice Bowl"), "S012344 5 ", Material.COOKED_COD, Material.COOKED_SALMON, Material.SEAGRASS, TOFU, STEAMED_RICE, Material.BOWL),
    TAKEOUT(5, Foods.custHead("takeout", ChatColor.BLUE + "Chinese Takeout"), "L00123456", STEAMED_RICE, Material.SEAGRASS, TOFU, Material.COOKED_CHICKEN, Material.COOKED_PORKCHOP, Material.BUCKET, Material.COOKED_COD),
    
	ROTTENFLESH(1, new ItemStack(Material.ROTTEN_FLESH), null),
    BONEMEAL(1, new ItemStack(Material.BONE_MEAL), null), 
    STOCK_STEW(1, Foods.custom(Material.RABBIT_STEW, ChatColor.RED + "Zombie Stock"), "L01  2    ", Material.ROTTEN_FLESH, Material.BONE, Material.BOWL), // BREAD, MUSHROOM_STEW, 
    PLAGUED_BREAD(2, Foods.custom(Material.BREAD, ChatColor.DARK_GREEN + "Plagued Bread"), "S 0 010 0 ", Material.BREAD, Material.ROTTEN_FLESH),
    SPIDER_BLOOD(2, Foods.custom(Material.FIRE_CORAL_FAN, ChatColor.DARK_RED + "Spider Blood", 2), "F", Material.SPIDER_EYE),
    STRINGWEASEL(2, Foods.custom(Material.LEATHER_HORSE_ARMOR, ChatColor.GRAY + "Stringweasel"), "S000111   ", Material.STRING, Material.ROTTEN_FLESH),
    TENDERED_MEAT(2, Foods.custom(Material.COOKED_MUTTON, ChatColor.GOLD + "Tenderized Meat"), "F", Material.ROTTEN_FLESH),
    BLOOD_STEW(3, Foods.custom(Material.BEETROOT_SOUP, ChatColor.DARK_RED + "Blood Stew"), "S 0 000 1 ", SPIDER_BLOOD, Material.BOWL),
    SKELE_SKULL(3, Foods.custom(Material.SKELETON_SKULL, ChatColor.WHITE + "Skull of Man"), "S011121111", Material.ARROW, Material.BONE, SPIDER_BLOOD),
    HEART(3, Foods.custom(Material.BEETROOT, ChatColor.RED + "Human Heart"), "S012101210", SPIDER_BLOOD, Material.ROTTEN_FLESH, Material.STRING),
    GARNISHED_BREAD(4, Foods.custom(Material.BREAD, ChatColor.GREEN + "Garnished Bread"), "L01222", PLAGUED_BREAD, SPIDER_BLOOD, Material.BONE_MEAL),
    LIVER(4, Foods.custom(Material.MUTTON, ChatColor.RED + "Liver"), "S 010 12 1", Material.WHEAT, SPIDER_BLOOD, TENDERED_MEAT),
    PERFECT_MEAL(4, Foods.custom(Material.RABBIT_STEW, ChatColor.YELLOW + "Perfect Meal"), "L0123", PLAGUED_BREAD, BLOOD_STEW, Material.BONE_MEAL, TENDERED_MEAT),
    PLAGUE_CAKE(5, Foods.custom(Material.CAKE, ChatColor.LIGHT_PURPLE + "Plague Cake"), "S000121333", SPIDER_BLOOD, Material.BONE_MEAL, TENDERED_MEAT, PLAGUED_BREAD),
    THE_HEAD(5, Foods.custom(Material.ZOMBIE_HEAD, ChatColor.BLUE + "" + ChatColor.MAGIC + "The Head."), "S010121010", SPIDER_BLOOD, Material.ROTTEN_FLESH, SKELE_SKULL),
    WILLING_FLESH(5, Foods.custHead("flesh", ChatColor.RED + "Willing Flesh"), "L  0 12", Material.BONE, SPIDER_BLOOD, HEART);
    
	
    public int difficulty;
    public ItemStack product;
    public String recipe;
    private Object[] ingredients;

    Foods(int difficulty, ItemStack product, String recipe, Object... ingredients) {
        this.product = product;
        this.difficulty = difficulty;
        this.recipe = recipe;
        this.ingredients = ingredients;
    }
    
    public static Foods[] createMenu(String restaurant) {
        switch (restaurant) {
            case "diner":
                return new Foods[] {BREAD, APPLE, COOKIE, MELON, CARROT, MUSHROOM_STEW, BEETROOT_STEW, STEAK, LAMB, CHICKEN,
                                    PORK, RABBIT, CHEESE, PUMPKIN_PIE, CARAMEL, BAKED_POTATO, CAKE, APPLE_TART, HAMBURGER, CHEESEBURGER, LOADED_POTATO,
                                    RABBIT_STEW, SUPREME_PIZZA, PIG_BLANKET, KFC_BUCKET, GORDON_BURGER, MEAT_PIZZA};
            case "asian":
                return new Foods[] {MELON, CARROT, MUSHROOM_STEW, SOY_MILK, STEAMED_RICE, STEAK, CHICKEN, PORK, TOFU, SALMONROLL, ONIGIRI,
                                    MISO_SOUP, EGG_F_RICE, VEG_RICEBOWL, SPICYROLL, CODSWALLOP, MUSHROOMROLL, MISO_RAMEN, CHONGQING_NOODLES,
                                    FISH_RICEBOWL, TAKEOUT};
            case "occultist":
            	return new Foods[] {ROTTENFLESH, BONEMEAL, STOCK_STEW, BREAD, PLAGUED_BREAD, SPIDER_BLOOD, STRINGWEASEL, TENDERED_MEAT, BLOOD_STEW,
            						SKELE_SKULL, HEART, GARNISHED_BREAD, LIVER, PERFECT_MEAL, PLAGUE_CAKE, THE_HEAD, WILLING_FLESH};
            	
        }
        return null;
    }
    
    public int isValidCraft(CraftingInventory cr) { // returns 0 if not found, 1 if found but not satisfied, 2 if found and valid.
    	if (recipe == null || ! (recipe.startsWith("S") || recipe.startsWith("L")))
    		{return 0;}
    	int output = 2;
    	List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    	for (int i = 1; i < recipe.length(); ++i) {
    		if (recipe.charAt(i) == ' ') {
    			if (recipe.startsWith("S") && cr.getItem(i) != null)
    				return 0;
    			else continue;
    		}
    		Object INGR = ingredients[recipe.charAt(i) - '0'];
    		if (recipe.startsWith("S")) {
    			ItemStack is = cr.getItem(i);
        		if (is == null)
        			return 0;
        		ItemStack is2 = is.clone();
        		is2.setAmount(1);
    			if (is2.getType() != getM(INGR))
    				return 0;
    			else if (! is2.equals(getIS(INGR)))
    				output = 1;
    		} else {
    			ItemStack toFind = getIS(INGR).clone();
    			int found = 0;
    			for (int a = 0; a < ids.size(); ++a) {
    				ItemStack is = cr.getItem(ids.get(a));
    				if (is == null) {
    					ids.remove(a);
    					continue;
    				}
    				toFind.setAmount(is.getAmount());
    				if (toFind.equals(is)) {
    					found = 2;
    					ids.remove(a);
    					break;
    				} else if (toFind.getType() == is.getType())
    					found = 1;
    			}
    			if (found == 0)
    				return 0;
    			if (found == 1)
    				output = 1;
    		}
    	}
    	return output;
    }

    @SuppressWarnings("deprecation")
	public static void addAllRecipes() {
        for (Foods f : Foods.values()) {
            if (f.recipe == null) // no recipe; skip
                continue;
            String name = "";
            for (int i = 0; i < 30; ++i) name += (char) (new Random().nextInt(26) + 'a');
            if (f.recipe.startsWith("S")) { // shaped recipe
                ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), name), f.product);
                sr.shape(f.recipe.substring(1, 4), f.recipe.substring(4, 7), f.recipe.substring(7, 10));
                for (int i = 0; i < f.ingredients.length; ++i)
                    sr.setIngredient((char) (i + '0'), new RecipeChoice.ExactChoice(getIS(f.ingredients[i])));
                Bukkit.addRecipe(sr);
            } else if (f.recipe.startsWith("L")) { // shapeless recipe
                ShapelessRecipe sr = new ShapelessRecipe(new NamespacedKey(Main.getPlugin(), name), f.product);
                for (int i = 1; i < f.recipe.length(); ++i)
                	if (f.recipe.charAt(i) != ' ')
                		sr.addIngredient(new RecipeChoice.ExactChoice(getIS(f.ingredients[f.recipe.charAt(i) - '0'])));
                Bukkit.addRecipe(sr);
            } else if (f.recipe.startsWith("F")) { // furnace recipe
                FurnaceRecipe fr = new FurnaceRecipe(new NamespacedKey(Main.getPlugin(), name), f.product, new RecipeChoice.ExactChoice(getIS(f.ingredients[0])), 0, 200);
                Bukkit.addRecipe(fr);
            }
        }
    }

    public static void openValidRecipeInventory(Player p, ItemStack clicked) {
        // PRECONDITION: clicked is of amount 1.
        Foods f = null;
        for (Foods f2 : Foods.values())
            if (f2.product.equals(clicked)) {
                f = f2;
                break;
            }
        Inventory inv = Bukkit.createInventory(p, 9 * 5, "Chris's Cookbook");
        // set brown glass panes
        for (int i = 0; i < 9; ++i) { inv.setItem(i, new ItemStack(Material.BROWN_STAINED_GLASS_PANE)); inv.setItem(i + 4 * 9, new ItemStack(Material.BROWN_STAINED_GLASS_PANE, 1)); }
        for (int i = 1; i < 4; ++i) { inv.setItem(i * 9, new ItemStack(Material.BROWN_STAINED_GLASS_PANE)); inv.setItem(i * 9 + 8, new ItemStack(Material.BROWN_STAINED_GLASS_PANE)); }

        if (f == null || f.recipe == null) { // no recipe
            inv.setItem(9 * 2 + 4, Foods.custom(Material.COBWEB, "N/A"));
        } else if (f.recipe.equals("F")) { // furnace recipe
            inv.setItem(9 * 2 + 4, Foods.custom(Material.FURNACE, "Smelting"));
            inv.setItem(9 * 2 + 6, f.product);
            inv.setItem(9 * 2 + 2, getIS(f.ingredients[0]));
        } else { // crafting
            if (f.recipe.startsWith("C"))
                inv.setItem(9 * 2 + 5, Foods.custom(Material.CRAFTING_TABLE, "Crafting (Shaped)"));
            else inv.setItem(9 * 2 + 5, Foods.custom(Material.CRAFTING_TABLE, "Crafting (Shapeless)"));
            inv.setItem(9 * 2 + 7, f.product);
            for (int i = 0; i < f.recipe.length() - 1; ++i) {
                char ingrID = f.recipe.charAt(i + 1);
                if (ingrID != ' ') // if an ingredient exists in this spot
                    inv.setItem((i / 3 + 1) * 9 + 1 + i % 3, getIS(f.ingredients[ingrID - '0']));
            }
        }

        p.closeInventory();
        p.openInventory(inv);
    }
    
    private static ItemStack getIS(Object obj) {
    	if (obj instanceof ItemStack)
    		return (ItemStack) obj;
    	if (obj instanceof Foods)
    		return ((Foods) obj).product;
    	if (obj instanceof Material)
    		return new ItemStack((Material) obj);
    	return null;
    }
    
    private static Material getM(Object obj) {
    	if (obj instanceof ItemStack)
    		return ((ItemStack) obj).getType();
    	if (obj instanceof Foods)
    		return ((Foods) obj).product.getType();
    	if (obj instanceof Material)
    		return (Material) obj;
    	return null;
    }

    public static ItemStack custom(Material m, String name) {
        ItemStack is = new ItemStack(m, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }
    
    public static ItemStack custom(Material m, String name, int amt) {
        ItemStack is = new ItemStack(m, amt);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }
    
    @SuppressWarnings("deprecation")
	private static ItemStack custHead(String headName, String itemName) {
    	ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
    	String[] data = new String[2];
    	switch(headName) {
    	case "flesh":
    		data = new String[] {"4c096480-4ad6-4b84-b9aa-24c4f01f5c97", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFkN2M4MTZmYzhjNjM2ZDdmNTBhOTNhMGJhN2FhZWZmMDZjOTZhNTYxNjQ1ZTllYjFiZWYzOTE2NTVjNTMxIn19fQ=="};
    		break;
    	case "onigiri":
            data = new String[] {"706536fa-fe57-478e-b433-67b9de73b5de", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjljMmRkZjJiZDc0YTQ2NTVlOGYwMTUzYTc0NTNlNjdkYjJhMjFkYmZhYzY3NTY3ODk0ODFhZGJlYzQ4M2EifX19"};
            break;
    	case "ramen":
            data = new String[] {"8e12050e-6ee8-40b7-b5f6-2f1def1eb9fe", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY0MTE4NzBkOTgzMmM4ODE4ODY2OWM4NmRkOGM2ODA2OGI4NDY2YjM4ZTk4Y2VkY2Y4ZWViNzVjZmIzIn19fQ=="};
            break;
        case "chongqing":
            data = new String[] {"f15ab073-412e-4fe2-8668-1be12066e2ac", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY4MzRiNWIyNTQyNmRlNjM1MzhlYzgyY2E4ZmJlY2ZjYmIzZTY4MmQ4MDYzNjQzZDJlNjdhNzYyMWJkIn19fQ=="};
            break;
        case "fishRice":
            data = new String[] {"cd0dfb89-80e8-449a-b467-c0e8c1351fce", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThlMDEwMmE5MGRiODI5MTlmZGRlOTc2YTc2MDJjNTEzZDMwMWEwY2RhZTk3ZWYyNTkyMTg2NTBmY2VhOCJ9fX0="};
            break;
        case "vegRice":
            data = new String[] {"95af831a-de97-47d4-8e77-93d0f34eac16", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk1OTU3MzU5YTMyZGM4NmJlZjllYTJlNWFmNGY0OWQ3ZWE3MjM4ZDBmNGNkMmRiZDk3NWNiYzVjYmYyNzQ5In19fQ=="};
            break;
        case "misoSoup":
            data = new String[] {"dd3c2362-ab60-4175-bb37-4518d8c0c02c", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTM4MWUxZWIzZGUxNDZjNzgxYWE4YjM3NGQ3NDc0YjRhYTYzZmI4ZDAzNGZiODFjMTM1NTgxNDQzZmQ1OTYyIn19fQ=="};
            break;
        case "eggRice":
            data = new String[] {"aa1370ef-c195-44e0-aa93-fad4f8419174", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJmODhjMGNjYmYwNjk3ZjY0ZjlhN2QyMDM0NzY5ZTQ4MTg0OGZiMjJlMTk3ZGZhZTI5ZTFmNTY1ZjIwMDNlYSJ9fX0="};
            break;
    	case "codswallop":
    		data = new String[] {"3de7a033-81ba-406c-a466-36ba96156079", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2NjNGE0NTVkNTM3YzgyMTgyMGFhZDIwYjk1MzY4NjQ4NDBhODczYmM5MDE2M2FhMzU1ODY2YjMyZTM1ZDA0MCJ9fX0="};
    		break;
    	case "takeout":
    		data = new String[] {"af205cbb-f025-4556-ae51-d4272b0dd3f0", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU0MjI4NmRhMzNhMjM4ZTRmMjdmZTcwM2ZjOGEwODcyMDFiNjk0MGZjMjM3NDRkZjk2NjNmYjk4NWRhMDI0In19fQ=="};
    		break;
    	case "sushi4":
    		data = new String[] {"216f5683-3506-4098-a741-18780b", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ2ZDMxNzJlN2Q2YWQ2ZGY2YTIxODk3NTVjZTBiMmRjODVhMWY5Y2NkMTA5ZGM5MzI5ODU4NjdiYTYifX19"};
    		break;
    	case "sushi3":
    		data = new String[] {"bc8ee27d-b320-47b9-aa5e-b473ad", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNiZjhmY2EyYWYzNTkyYzU1NzRiMTNlM2JjZjYxZTJmYWU4Mjk3ODg1MzVmMGRkZWFhN2EyZTQ1YjZiYTQifX19"};
    		break;
    	case "sushi2":
    		data = new String[] {"95ef1561-33cd-412e-8483-2185daab814e", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTIwODRiZWZjOGE3YmY3M2Y3NjIxOTVmYjkzMGRjMTA4YWJmMjBkNjdiYTgwYzQ5YTM0MDZlMGRjOGEzN2I5ZCJ9fX0="};
    		break;
    	case "sushi1":
    		data = new String[] {"54bc6927-b195-4d36-b09c-95a1f02dedb", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUzNDdkYWRmNjgxOTlmYTdhMWI2NmYwNDgxYWQ4ZTlkYWVlMTUxMDg2NWFkZDZmMzNkMTVmYjM3OGQxM2U5MSJ9fX0="};
    		break;
    	case "hamburger":
    		data = new String[] {"f597e06f-e01a-492d-b63f-c23b9809ce01", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzVlMjc5ODhhNjUzODAxMGVmYzBlMjQ3NTZiYzNlM2VlYTI0ZDc1MzZiMjA5MzJjMTdlMDQwNGU1Y2M1NWYifX19"};
    		break;
    	case "cheeseburger":
    		data = new String[] {"cd02b2ac-24de-4da3-b66d-8405fa", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RhZGYxNzQ0NDMzZTFjNzlkMWQ1OWQyNzc3ZDkzOWRlMTU5YTI0Y2Y1N2U4YTYxYzgyYmM0ZmUzNzc3NTUzYyJ9fX0="};
    		break;
    	case "gordonburger":
    		data = new String[] {"bb7dc51c-1dce-42f4-ae93-6a7907", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZlZjFjMjVmNTE2ZjJlN2Q2Zjc2Njc0MjBlMzNhZGNmM2NkZjkzOGNiMzdmOWE0MWE4YjM1ODY5ZjU2OWIifX19"};
    		break;
    	case "potatoe":
    		data = new String[] {"0e8a0f94-2a9d-4ec1-8700-a81487", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmYzMTJhMjQzYWE0ZTY5YTVlZGM2N2U1NGU0NGY3NmViODRmNjZlYzgyNDA4OTU1N2E2NGJlYTcxZjZkYyJ9fX0="};
    		break;
    	case "kfc":
    		data = new String[] {"7efe7ed5-fe6a-411e-b864-eaf81e18a833", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzlhZmExNzgxZTNhMTkyNzdiZWNjODY3NThiNGMwNGI4NWY3ZWM3MzM2MWE3NWU4OTFmZjk2YmYxMWI0In19fQ=="};
    		break;
    	case "pigblanket":
    		data = new String[] {"46bbd502-1ef0-4fe8-a033-9a5687", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWExZjM1ODVmNTQ4NDliNDFkYTVkMzI2YTk0Mzk1NmEyNTQ5NDM2NGJiOTYxZDI0NThjNDMwYmU5YTZiMjcifX19"};
    		break;
    	case "muffin":
    		data = new String[] {"a77b189f-c30a-4962-8647-5cecac", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQzMjE2YjNhZDJmNDc1N2EyM2MzYTI0Y2RhZjhjZTg3YmM0MjFkNGI2ZThiYzJjZTY0MmFhNDgwM2U5OSJ9fX0="};
    		break;
    	} 
    	head = Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:\"" + data[0] + "\",Properties:{textures:[{Value:\"" + data[1] + "\"}]}}}");
    	ItemMeta im = head.getItemMeta();
    	im.setDisplayName(itemName);
    	head.setItemMeta(im);
		return head;
    }
    
    public static void openRecipeBook(Player p) {
    	Inventory inv = Bukkit.createInventory(p, 9 * 6, "Chris's Recipe Book");
    	for (Foods f : Foods.values())
    		if (f.recipe != null)
    			inv.addItem(f.product);
    	
    	p.closeInventory();
    	p.openInventory(inv);
    }

}