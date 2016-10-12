package com.minecraft.moonlakedev.colorchat.listeners;

import com.minecraft.moonlakedev.colorchat.ColorChatPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by MoonLake on 2016/10/12.
 */
public class PlayerListener implements Listener {

    private final ColorChatPlugin main;

    public PlayerListener(ColorChatPlugin main) {
        this.main = main;
    }

    public ColorChatPlugin getMain() {
        return main;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
    }
}
