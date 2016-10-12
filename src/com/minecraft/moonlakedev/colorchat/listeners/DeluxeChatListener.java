package com.minecraft.moonlakedev.colorchat.listeners;

import com.minecraft.moonlakedev.colorchat.ColorChatPlugin;
import me.clip.deluxechat.events.DeluxeChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by MoonLake on 2016/10/12.
 */
public class DeluxeChatListener implements Listener {

    private final ColorChatPlugin main;

    public DeluxeChatListener(ColorChatPlugin main) {
        this.main = main;
    }

    public ColorChatPlugin getMain() {
        return main;
    }

    @EventHandler
    public void onChat(DeluxeChatEvent event) {
        Player player = event.getPlayer();
        String colorChat = getMain().getManager().formatDeluxeChat(player, event.getChatMessage());
        if(colorChat != null)
            event.setChatMessage(getMain().toColor(colorChat));
    }
}
