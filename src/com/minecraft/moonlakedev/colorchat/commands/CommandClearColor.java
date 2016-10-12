package com.minecraft.moonlakedev.colorchat.commands;

import com.minecraft.moonlakedev.colorchat.ColorChatPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by MoonLake on 2016/10/12.
 */
public class CommandClearColor implements CommandExecutor {

    private final ColorChatPlugin main;

    public CommandClearColor(ColorChatPlugin main) {
        this.main = main;
    }

    public ColorChatPlugin getMain() {
        return main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return true;
    }
}
