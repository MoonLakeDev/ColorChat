package com.minecraft.moonlakedev.colorchat;

import com.minecraft.moonlakedev.colorchat.commands.CommandClearColor;
import com.minecraft.moonlakedev.colorchat.commands.CommandColorChat;
import com.minecraft.moonlakedev.colorchat.commands.CommandRandomColor;
import com.minecraft.moonlakedev.colorchat.listeners.DeluxeChatListener;
import com.minecraft.moonlakedev.colorchat.manager.ColorChatManager;
import me.clip.deluxechat.DeluxeChat;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by MoonLake on 2016/10/12.
 */
public class ColorChatPlugin extends JavaPlugin {

    private String prefix;
    private ColorChatManager manager;

    public ColorChatPlugin() {
    }

    @Override
    public void onEnable() {
        if(!setupDeluxeChat()) {
            this.getLogger().log(Level.SEVERE, "前置插件杜蕾斯聊天 DeluxeChat 加载失败.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.initFolder();
        this.manager = ColorChatManager.instance();
        this.getCommand("colorchat").setExecutor(new CommandColorChat(this));
        this.getCommand("clearcolor").setExecutor(new CommandClearColor(this));
        this.getCommand("randomcolor").setExecutor(new CommandRandomColor(this));
        this.getServer().getPluginManager().registerEvents(new DeluxeChatListener(this), this);
        this.getLogger().log(Level.INFO, "聊天颜色 ColorChat 插件 v" + getDescription().getVersion() + " 成功加载.");
    }

    @Override
    public void onDisable() {
    }

    public String getMessage(String key, Object... args) {

        return toColor(prefix + String.format(getConfig().getString("Messages." + key, ""), args));
    }

    public String toColor(String src) {
        return ChatColor.translateAlternateColorCodes('&', src);
    }

    public ColorChatManager getManager() {
        return manager;
    }

    private void initFolder() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists())
            saveDefaultConfig();
        this.prefix = toColor(getConfig().getString("Prefix", "&f[&4C&co&6l&eo&ar&bC&9h&da&5t&f] "));
    }

    private boolean setupDeluxeChat() {
        Plugin plugin = getServer().getPluginManager().getPlugin("DeluxeChat");
        return plugin != null && plugin instanceof DeluxeChat;
    }
}
