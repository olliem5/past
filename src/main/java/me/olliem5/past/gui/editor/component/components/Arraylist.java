package me.olliem5.past.gui.editor.component.components;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.text.StringUtil;

public class Arraylist extends HudComponent {
    public Arraylist() {
        super("Arraylist");
        setWidth(9);
        setHeight(9);
    }

    Setting lowercase;
    Setting rainbow;
    Setting customfont;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(lowercase = new Setting("Lowercase", "ArraylistLowercase", false, this));
        Past.settingsManager.registerSetting(rainbow = new Setting("Rainbow", "ArraylistRainbow", true, this));
        Past.settingsManager.registerSetting(customfont = new Setting("Custom Font", "ArraylistCustomFont", true, this));
    }

    public void render(float ticks) {
        if (customfont.getValBoolean()) {
            Past.moduleManager.modules.sort((module1, module2) -> StringUtil.getStringWidthCustomFont(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - StringUtil.getStringWidthCustomFont(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
        } else {
            Past.moduleManager.modules.sort((module1, module2) -> StringUtil.getStringWidth(StringUtil.capitalizeFirstLetter(module2.getName() + module2.getArraylistInfo())) - StringUtil.getStringWidth(StringUtil.capitalizeFirstLetter(module1.getName() + module1.getArraylistInfo())));
        }

        int count = 0;

        for (Module module : Past.moduleManager.getModules()) {
            if (!module.isToggled()) continue;

            double offset = count * (customfont.getValBoolean() ? Past.customFontRenderer.getHeight() + 2 : mc.fontRenderer.FONT_HEIGHT + 2);

            if (!lowercase.getValBoolean()) {
                if (customfont.getValBoolean()) {
                    Past.customFontRenderer.drawStringWithShadow(module.getName() + ColourUtil.gray + module.getArraylistInfo(), getX(), getY() + (int) offset, rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
                } else {
                    mc.fontRenderer.drawStringWithShadow(module.getName() + ColourUtil.gray + module.getArraylistInfo(), getX(), getY() + (int) offset, rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
                }
            } else {
                if (customfont.getValBoolean()) {
                    Past.customFontRenderer.drawStringWithShadow(module.getName().toLowerCase() + ColourUtil.gray + module.getArraylistInfo().toLowerCase(), getX(), getY() + (int) offset, rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
                } else {
                    mc.fontRenderer.drawStringWithShadow(module.getName().toLowerCase() + ColourUtil.gray + module.getArraylistInfo().toLowerCase(), getX(), getY() + (int) offset, rainbow.getValBoolean() ? ColourUtil.getMultiColour().getRGB() : -1);
                }
            }
            ++count;
        }
    }
}
