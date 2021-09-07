package com.chrisw.restaurant.commands;

import com.chrisw.restaurant.game.Foods;
import com.chrisw.restaurant.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRestaurant implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player))
            return false;
        if (args.length == 0)
            return false;
        Player p = (Player) commandSender;
        String restaurant = args[0];
        if (Game.isInGame()) {
            commandSender.sendMessage("In game");
            return false;
        }
        if (Foods.createMenu(restaurant) != null) {
            Game.startGame(p, restaurant);
            return true;
        } else return false;
    }

}
