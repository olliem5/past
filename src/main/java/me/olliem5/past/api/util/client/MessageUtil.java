package me.olliem5.past.api.util.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;

public class MessageUtil {
    private static final EntityPlayerSP player = Minecraft.getMinecraft().player;

    public static String prefix = ChatFormatting.GRAY + "[" + ChatFormatting.RED + "Past Client" + ChatFormatting.GRAY + "]";
    public static String entityalertPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "EntityAlert" + ChatFormatting.GRAY + "]";
    public static String weaknessAlertPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "WeaknessDetect" + ChatFormatting.GRAY + "]";
    public static String bedAuraPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "BedAura" + ChatFormatting.GRAY + "]";
    public static String surroundPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "Surround" + ChatFormatting.GRAY + "]";
    public static String autoBuilderPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "AutoBuilder" + ChatFormatting.GRAY + "]";
    public static String friendsPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "Friends" + ChatFormatting.GRAY + "]";
    public static String autoCrystalPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "AutoCrystal" + ChatFormatting.GRAY + "]";
    public static String autoInsultPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "AutoInsult" + ChatFormatting.GRAY + "]";
    public static String durabilityWarnPrefix = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_RED + "DurabilityWarn" + ChatFormatting.GRAY + "]";

    public static void sendRawMessage(String message) {
        player.sendMessage(new TextComponentString(message));
    }

    public static void sendMessagePrefix(String message) {
        sendRawMessage(prefix + " " + message);
    }

    public static void sendEntityAlertMessage(String message) {
        sendMessagePrefix(entityalertPrefix + " " + message);
    }

    public static void sendWeaknessAlertMessage(String message) {
        sendMessagePrefix(weaknessAlertPrefix + " " + message);
    }

    public static void sendBedAuraMessage(String message) {
        sendMessagePrefix(bedAuraPrefix + " " + message);
    }

    public static void sendSurroundMessage(String message) {
        sendMessagePrefix(surroundPrefix + " " + message);
    }

    public static void sendAutoBuilderMessage(String message) {
        sendMessagePrefix(autoBuilderPrefix + " " + message);
    }

    public static void sendFreindsMessage(String message) {
        sendMessagePrefix(friendsPrefix + " " + message);
    }

    public static void sendAutoCrystalMessage(String message) {
        sendMessagePrefix(autoCrystalPrefix + " " + message);
    }

    public static void sendAutoInsultMessage(String message) {
        sendMessagePrefix(autoInsultPrefix + " " + message);
    }

    public static void sendDurabilityWarnMessage(String message) {
        sendMessagePrefix(durabilityWarnPrefix + " " + message);
    }
}