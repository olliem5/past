package me.olliem5.past.util.module;

import me.olliem5.past.Past;
import me.olliem5.past.util.colour.ColourListUtil;

public class ClickGUIUtil {

    public static int getGUIColour() {
        return ColourListUtil.toRGBA(
                Past.settingsManager.getSettingID("ClickGUIRed").getValueInt(),
                Past.settingsManager.getSettingID("ClickGUIGreen").getValueInt(),
                Past.settingsManager.getSettingID("ClickGUIBlue").getValueInt(),
                Past.settingsManager.getSettingID("ClickGUIAlpha").getValueInt()
        );
    }
}
