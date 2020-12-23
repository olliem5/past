package me.olliem5.past.impl.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.olliem5.past.Past;
import me.olliem5.past.api.module.Category;
import me.olliem5.past.api.module.Module;
import me.olliem5.past.api.module.ModuleInfo;
import me.olliem5.past.api.setting.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

@ModuleInfo(name = "Fullbright", description = "Makes your game brighter, re-enable after changing mode", category = Category.RENDER)
public class Fullbright extends Module {

    private PotionEffect fullbrighteffect = new PotionEffect(Potion.getPotionById(16));

    private float originalGamma;

    Setting brightnessmode;

    private ArrayList<String> brightnessmodes;

    @Override
    public void setup() {
        brightnessmodes = new ArrayList<>();
        brightnessmodes.add("Gamma");
        brightnessmodes.add("Potion");

        Past.settingsManager.registerSetting(brightnessmode = new Setting("Mode", "FullbrightMode", this, brightnessmodes, "Gamma"));
    }

    @Override
    public void onEnable() {
        if (brightnessmode.getValueString() == "Gamma") {
            originalGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 10;
        } else {
            mc.player.addPotionEffect(fullbrighteffect);
        }
    }

    @Override
    public void onDisable() {
        if (brightnessmode.getValueString() == "Gamma") {
            mc.gameSettings.gammaSetting = originalGamma;
        } else {
            mc.player.removeActivePotionEffect(fullbrighteffect.getPotion());
        }
    }

    public String getArraylistInfo() {
        return ChatFormatting.GRAY + " " + brightnessmode.getValueString().toUpperCase();
    }
}
