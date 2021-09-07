package com.chrisw.restaurant.commands;

import com.chrisw.restaurant.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRestaurantStop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!Game.isInGame()) {
            commandSender.sendMessage("Not in game");
            return false;
        }
        Game.endGame();
        return true;
    }

}
