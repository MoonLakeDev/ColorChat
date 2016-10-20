package com.minecraft.moonlakedev.colorchat.manager;

import com.minecraft.moonlakedev.colorchat.type.ColorChatType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by MoonLake on 2016/10/12.
 */
public class ColorChatManager {

    private Map<String, ColorChatType> colorChatTypeMap;
    private Map<String, String> colorChatTypeSpecifyMap;
    private char[] RANDOM_COLOR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private final Random RANDOM = new Random();
    private final Pattern SKIP_COLOR_STYLE = Pattern.compile("([\\u00A7&])[k-oK-OrR]");
    private static ColorChatManager colorChatManagerInstance;

    private ColorChatManager() {
        this.colorChatTypeMap = new HashMap<>();
        this.colorChatTypeSpecifyMap = new HashMap<>();
    }

    public static ColorChatManager instance() {
        if(colorChatManagerInstance == null)
            colorChatManagerInstance = new ColorChatManager();
        return colorChatManagerInstance;
    }

    public void setRandomColorChars(String random) {
        if(random == null)
            return;
        this.RANDOM_COLOR = random.toCharArray();
    }

    public void setColorChatType(Player player, ColorChatType type, ChatColor value) {
        if(!colorChatTypeMap.containsKey(player.getName()))
            colorChatTypeMap.put(player.getName(), type);
        if(type != ColorChatType.RANDOM) {
            String oldValue = getColorChatType(player);

            if(oldValue.matches("&([0-9a-fA-F]?)")) {
                if (value.isFormat())
                    colorChatTypeSpecifyMap.put(player.getName(), oldValue + "&" + value.getChar());
                else
                    colorChatTypeSpecifyMap.put(player.getName(), "&" + value.getChar());
            }
            else if(oldValue.matches("&([0-9a-fA-F]?)&([l-oL-O]?)")) {
                if (value.isColor())
                    colorChatTypeSpecifyMap.put(player.getName(), "&" + value.getChar() + oldValue.substring(2, 4));
                else
                    colorChatTypeSpecifyMap.put(player.getName(), oldValue.substring(0, 2) + "&" + value.getChar());
            }
        }
        else {
            if(colorChatTypeMap.containsKey(player.getName()))
                colorChatTypeMap.put(player.getName(), type);
            clearSpecify(player);
        }
    }

    public void clearSpecify(Player player) {
        if(colorChatTypeSpecifyMap.containsKey(player.getName()))
            colorChatTypeSpecifyMap.remove(player.getName());
    }

    public String getColorChatType(Player player) {
        return colorChatTypeMap.containsKey(player.getName()) && colorChatTypeSpecifyMap.containsKey(player.getName()) ? colorChatTypeSpecifyMap.get(player.getName()) : "&f";
    }

    public boolean isRandomColorChat(Player player) {
        return colorChatTypeMap.containsKey(player.getName()) && colorChatTypeMap.get(player.getName()) == ColorChatType.RANDOM;
    }

    public String formatDeluxeChat(Player player, String deluxeChat) {
        if(!colorChatTypeMap.containsKey(player.getName()))
            return deluxeChat;
        if(isRandomColorChat(player))
            return formatRandomDeluxeChat(player, deluxeChat);
        return formatSpecifyDeluxeChat(player, deluxeChat);
    }

    private String formatSpecifyDeluxeChat(Player player, String deluxeChat) {
        deluxeChat = clearSrcColor(player, deluxeChat);
        return getColorChatType(player) + deluxeChat;
    }

    private String formatRandomDeluxeChat(Player player, String deluxeChat) {
        deluxeChat = clearSrcColor(null, deluxeChat);

        char[] srcChat = deluxeChat.toCharArray();
        String temp = "";
        for(char src : srcChat) {
            temp += "&" + RANDOM_COLOR[RANDOM.nextInt(RANDOM_COLOR.length)] + src;
        }
        return temp;
    }

    private String clearSrcColor(Player player, String deluxeChat) {
        if(player != null && player.hasPermission("colorchat.scolor"))
            return deluxeChat == null ? "" : SKIP_COLOR_STYLE.matcher(deluxeChat).replaceAll("");
        return deluxeChat == null ? "" : ChatColor.stripColor(deluxeChat);
    }

    public void close(Player player) {
        if(colorChatTypeMap.containsKey(player.getName()))
            colorChatTypeMap.remove(player.getName());
        if(colorChatTypeSpecifyMap.containsKey(player.getName()))
            colorChatTypeSpecifyMap.remove(player.getName());
    }

    public void close() {
        colorChatTypeMap.clear();
        colorChatTypeSpecifyMap.clear();
    }
}
