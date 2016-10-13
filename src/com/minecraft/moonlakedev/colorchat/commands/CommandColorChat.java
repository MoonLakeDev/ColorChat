package com.minecraft.moonlakedev.colorchat.commands;

import com.minecraft.moonlakedev.colorchat.ColorChatPlugin;
import com.minecraft.moonlakedev.colorchat.type.ColorChatType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/10/12.
 */
public class CommandColorChat implements CommandExecutor {

    private final ColorChatPlugin main;

    public CommandColorChat(ColorChatPlugin main) {
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
        if(!player.hasPermission("colorchat.use")) {
            player.sendMessage(getMain().getMessage("NoPermission"));
            return true;
        }
        if(args.length != 1) {
            player.sendMessage(getMain().getMessage("ErrorCommandArgs", "/colorchat <&颜色或样式字符>"));
            return true;
        }
        if(getMain().getManager().isRandomColorChat(player)) {
            player.sendMessage(getMain().getMessage("ErrorColorChatRandom"));
            return true;
        }
        if(!args[0].matches("&([0-9a-fA-Fl-oL-O]?)")) {
            player.sendMessage(getMain().getMessage("ErrorChatColorValue"));
            return true;
        }
        ChatColor value = ChatColor.getByChar(args[0].charAt(1));
        if(value != null) {
            player.sendMessage(getMain().getMessage("SetColorChatContent"));
            getMain().getManager().setColorChatType(player, ColorChatType.SPECIFY, value);
        }
        return true;
    }
}
