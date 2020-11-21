package me.olliem5.past.module.modules.render;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Makes your game brighter, re-enable after changing mode", Category.RENDER);
    }

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
        }

        if (brightnessmode.getValueString() == "Potion") {
            mc.player.addPotionEffect(fullbrighteffect);
        }
    }

    @Override
    public void onDisable() {
        if (brightnessmode.getValueString() == "Gamma") {
            mc.gameSettings.gammaSetting = originalGamma;
        }

        if (brightnessmode.getValueString() == "Potion") {
            mc.player.removeActivePotionEffect(fullbrighteffect.getPotion());
        }
    }

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + brightnessmode.getValueString().toUpperCase();
    }
}
