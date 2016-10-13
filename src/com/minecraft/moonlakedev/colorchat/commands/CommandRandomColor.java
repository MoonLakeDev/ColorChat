package com.minecraft.moonlakedev.colorchat.commands;

import com.minecraft.moonlakedev.colorchat.ColorChatPlugin;
import com.minecraft.moonlakedev.colorchat.type.ColorChatType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/10/12.
 */
public class CommandRandomColor implements CommandExecutor {

    private final ColorChatPlugin main;

    public CommandRandomColor(ColorChatPlugin main) {
        this.main = main;
    }

    public ColorChatPlugin getMain() {
        return main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(getMain().getMessage("NoConsoleUse"));
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("colorchat.random")) {
            player.sendMessage(getMain().getMessage("NoPermission"));
            return true;
        }
        // random color set
        player.sendMessage(getMain().getMessage("SetColorChatRandomContent"));
        getMain().getManager().setColorChatType(player, ColorChatType.RANDOM, null);
        return true;
    }
}
