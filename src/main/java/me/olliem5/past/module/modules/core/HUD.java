package me.olliem5.past.module.modules.core;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourListUtil;
import me.olliem5.past.util.ColourUtil;
import me.olliem5.past.util.StringUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Calendar;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Draws the HUD", Category.CORE);
    }

    Setting rgb;
    Setting shadow;
    Setting customfont;
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
    Setting sidebox;
    Setting background;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(rgb = new Setting("RGB", "HUDRGB", true, this));
        Past.settingsManager.registerSetting(shadow = new Setting("Shadow", "HUDShadow", true, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "HUDCustomFont", true, this));
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
        Past.settingsManager.registerSetting(sidebox = new Setting("Side Box", "HUDArraylistSidebox", true, this));
        Past.settingsManager.registerSetting(background = new Setting("Background", "HUDArraylistBackground", true, this));
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

    @SubscribeEvent
    public void onGui(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (watermark.getValBoolean()) {
                if (shadow.getValBoolean()) {
                    if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                        Past.customFontRenderer.drawStringWithShadow(Past.nameversion, watermarkx.getValueInt(), watermarky.getValueInt(), getColour());
                    } else {
                        mc.fontRenderer.drawStringWithShadow(Past.nameversion, watermarkx.getValueInt(), watermarky.getValueInt(), getColour());
                    }
                } else {
                    if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                        Past.customFontRenderer.drawString(Past.nameversion, watermarkx.getValueInt(), watermarky.getValueInt(), getColour());
                    } else {
                        mc.fontRenderer.drawString(Past.nameversion, watermarkx.getValueInt(), watermarky.getValueInt(), getColour());
                    }
                }
            }

            if (welcomer.getValBoolean()) {
                if (shadow.getValBoolean()) {
                    if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                        Past.customFontRenderer.drawStringWithShadow(getWelcomerMessage() + mc.getSession().getUsername(), welcomerx.getValueInt(), welcomery.getValueInt(), getColour());
                    } else {
                        mc.fontRenderer.drawStringWithShadow(getWelcomerMessage() + mc.getSession().getUsername(), welcomerx.getValueInt(), welcomery.getValueInt(), getColour());
                    }
                } else {
                    if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                        Past.customFontRenderer.drawString(getWelcomerMessage() + mc.getSession().getUsername(), welcomerx.getValueInt(), welcomery.getValueInt(), getColour());
                    } else {
                        mc.fontRenderer.drawString(getWelcomerMessage() + mc.getSession().getUsername(), welcomerx.getValueInt(), welcomery.getValueInt(), getColour());
                    }
                }
            }

            if (arraylist.getValBoolean()) {
                if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                    Past.moduleManager.modules.sort((module1, module2) -> StringUtil.getStringWidthCustomFont(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - StringUtil.getStringWidthCustomFont(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
                } else {
                    Past.moduleManager.modules.sort((module1, module2) -> StringUtil.getStringWidth(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - StringUtil.getStringWidth(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
                }

                ScaledResolution sr = new ScaledResolution(mc);
                int count = 0;

                for (Module module : Past.moduleManager.getModules()) {
                    if (!module.isToggled()) { continue; }

                    double offset = count * (mc.fontRenderer.FONT_HEIGHT + 4);

                    if (background.getValBoolean()) {
                        if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                            Gui.drawRect(sr.getScaledWidth() - Past.customFontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 10, (int) offset, sr.getScaledWidth(), 6 + Past.customFontRenderer.getHeight() + (int) offset, 0x75101010);
                        } else {
                            Gui.drawRect(sr.getScaledWidth() - mc.fontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 10, (int) offset, sr.getScaledWidth(), 4 + mc.fontRenderer.FONT_HEIGHT + (int) offset, 0x75101010);
                        }
                    }

                    if (sidebox.getValBoolean()) {
                        if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                            Gui.drawRect(sr.getScaledWidth() - Past.customFontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 10, (int) offset, sr.getScaledWidth() - Past.customFontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 8, 6 + Past.customFontRenderer.getHeight() + (int) offset, getColour());
                        } else {
                            Gui.drawRect(sr.getScaledWidth() - mc.fontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 10, (int) offset, sr.getScaledWidth() - mc.fontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 8, 4 + mc.fontRenderer.FONT_HEIGHT + (int) offset, getColour());
                        }
                    }

                    if (shadow.getValBoolean()) {
                        if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                            Past.customFontRenderer.drawStringWithShadow(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - Past.customFontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                        } else {
                            mc.fontRenderer.drawStringWithShadow(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - mc.fontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                        }
                    } else {
                        if (Past.settingsManager.getSettingID("HUDCustomFont").getValBoolean()) {
                            Past.customFontRenderer.drawString(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - Past.customFontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                        } else {
                            mc.fontRenderer.drawString(module.getName() + module.getArraylistInfo(), sr.getScaledWidth() - mc.fontRenderer.getStringWidth(module.getName() + module.getArraylistInfo()) - 4, (int) (4 + offset), getColour());
                        }
                    }
                    count++;
                }
            }
        }
    }
}
