package me.olliem5.past.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;

public class MessageManager {
    public static String prefix = ColourManager.gray + "[" + ColourManager.red + "Past Client"  + ColourManager.gray + "]";
    public static String entityalertPrefix = ColourManager.gray + "[" + ColourManager.blue + "EntityAlert" + ColourManager.gray + "]";
    public static String weaknessAlertPrefix = ColourManager.gray + "[" + ColourManager.lightPurple + "WeaknessDetect" + ColourManager.gray + "]";
    private static final EntityPlayerSP player = Minecraft.getMinecraft().player;

    public static void sendRawMessage(String message) { player.sendMessage(new TextComponentString(message)); }
    public static void sendMessagePrefix(String message) { sendRawMessage(prefix + " " + message); }
    public static void sendEntityAlertMessage(String message) { sendMessagePrefix(entityalertPrefix + " " + message); }
    public static void sendWeaknessAlertMessage(String message) { sendMessagePrefix(weaknessAlertPrefix + " " + message); }
}