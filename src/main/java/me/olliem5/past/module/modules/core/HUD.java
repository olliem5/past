package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourListUtil;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.text.FontUtil;
import me.olliem5.past.util.text.StringUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Calendar;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Draws the HUD", Category.CORE);
    }

    Setting rgb;
    Setting shadow;
    Setting red;
    Setting green;
    Setting blue;
    Setting watermark;
    Setting watermarkx;
    Setting watermarky;
    Setting welcomer;
    Setting welcomerx;
    Setting welcomery;
    Setting arraylist;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(rgb = new Setting("RGB", "HUDRGB", true, this));
        Past.settingsManager.registerSetting(shadow = new Setting("Shadow", "HUDShadow", true, this));
        Past.settingsManager.registerSetting(red = new Setting("Red", "HUDRed", 0, 255, 255, this));
        Past.settingsManager.registerSetting(green = new Setting("Green", "HUDGreen", 0, 10, 255, this));
        Past.settingsManager.registerSetting(blue = new Setting("Blue", "HUDBlue", 0, 10, 255, this));
        Past.settingsManager.registerSetting(watermark = new Setting("Watermark", "HUDWatermark", true, this));
        Past.settingsManager.registerSetting(watermarkx = new Setting("X Value", "HUDWatermarkX", 0, 2, 1000, this));
        Past.settingsManager.registerSetting(watermarky = new Setting("Y Value", "HUDWatermarkY", 0, 2, 1000, this));
        Past.settingsManager.registerSetting(welcomer = new Setting("Welcomer", "HUDWelcomer", true, this));
        Past.settingsManager.registerSetting(welcomerx = new Setting("X Value", "HUDWelcomerX", 0, 2, 1000, this));
        Past.settingsManager.registerSetting(welcomery = new Setting("Y Value", "HUDWelcomerY", 0, 12, 1000, this));
        Past.settingsManager.registerSetting(arraylist = new Setting("Arraylist", "HUDArraylist", true, this));
    }

    private int getColour() {
        if (rgb.getValBoolean()) {
            return ColourUtil.getMultiColour().getRGB();
        } else {
            return ColourListUtil.toRGBA(red.getValueInt(), green.getValueInt(), blue.getValueInt(), 255);
        }
    }

    private String getWelcomerMessage() {
        final int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) {
            return "Good Morning" + "," + " ";
        } else if (timeOfDay < 16) {
            return "Good Afternoon" + "," + " ";
        } else if (timeOfDay < 21) {
            return "Good Evening" + "," + " ";
        } else {
            return "Good Night" + "," + " ";
        }
    }

    @EventHandler
    public Listener<RenderGameOverlayEvent.Text> listener = new Listener<>(event -> {
        if (watermark.getValBoolean()) {
            FontUtil.drawText(Past.nameversion, watermarkx.getValueInt(), watermarky.getValueInt(), getColour());
        }

        if (welcomer.getValBoolean()) {
            FontUtil.drawText(getWelcomerMessage() + mc.getSession().getUsername(), welcomerx.getValueInt(), welcomery.getValueInt(), getColour());
        }

        if (arraylist.getValBoolean()) {
            if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
                Past.moduleManager.modules.sort((module1, module2) -> Past.latoFont.getStringWidth(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - Past.latoFont.getStringWidth(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
            } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
                Past.moduleManager.modules.sort((module1, module2) -> Past.verdanaFont.getStringWidth(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - Past.verdanaFont.getStringWidth(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
            } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
                Past.moduleManager.modules.sort((module1, module2) -> Past.arialFont.getStringWidth(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - Past.arialFont.getStringWidth(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
            } else {
                Past.moduleManager.modules.sort((module1, module2) -> mc.fontRenderer.getStringWidth(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - mc.fontRenderer.getStringWidth(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
            }

            ScaledResolution sr = new ScaledResolution(mc);
            int count = 0;

            for (Module module : Past.moduleManager.getModules()) {
                if (!module.isToggled()) {
                    continue;
                }

                double offset = count * (FontUtil.getFontHeight() + 2);

                if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
                    FontUtil.drawText(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - Past.latoFont.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
                    FontUtil.drawText(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - Past.verdanaFont.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                } else if (Past.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
                    FontUtil.drawText(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - Past.arialFont.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                } else {
                    FontUtil.drawText(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - mc.fontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                }

                count++;
            }
        }
    });
}
