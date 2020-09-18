package me.ollieobama.past.managers;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.ollieobama.past.Past;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

public class DiscordManager {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    public static DiscordRichPresence rp = new DiscordRichPresence();
    private static String details;
    private static String state;

    public static void startup() {
        System.out.println("[" + Past.nameversion + "]" + " " + "Discord RPC starting up!");
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        rpc.Discord_Initialize(Past.appid, handlers, true, "");
        rp.startTimestamp = System.currentTimeMillis() / 1000L;
        rp.largeImageKey = "pastclient";
        rp.largeImageText = Past.nameversion;
        rpc.Discord_UpdatePresence(rp);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = "In the menus";
                    state = "discord.gg/3DBphxC";

                    if (mc.isIntegratedServerRunning()) {
                        details = "Singleplayer - " + mc.getIntegratedServer().getWorldName();
                    } else if (mc.currentScreen instanceof GuiMultiplayer) {
                        details = "Multiplayer Menu";
                    } else if (mc.currentScreen instanceof GuiWorldSelection) {
                        details = "Singleplayer Menu";
                    } else if (mc.getCurrentServerData() != null) {
                        details = "On " + mc.getCurrentServerData().serverIP.toLowerCase();
                    }

                    rp.details = details;
                    rp.state = state;
                    rpc.Discord_UpdatePresence(rp);
                } catch (Exception e1) {
                    e1.printStackTrace();
                } try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        System.out.println("[" + Past.nameversion + "]" + " " + "Discord RPC shutting down!");
        rpc.Discord_Shutdown();
    }
}
