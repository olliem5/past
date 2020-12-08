package me.olliem5.past.util.module;

import me.olliem5.past.Past;
import me.olliem5.past.util.colour.ColourListUtil;

public class GUIColourUtil {

    public static int getGUIColour() {
        return ColourListUtil.toRGBA(
                Past.settingsManager.getSettingID("ClickGUIRed").getValueInt(),
                Past.settingsManager.getSettingID("ClickGUIGreen").getValueInt(),
                Past.settingsManager.getSettingID("ClickGUIBlue").getValueInt(),
                Past.settingsManager.getSettingID("ClickGUIAlpha").getValueInt()
        );
    }

    public static int getHudEditorColour() {
        return ColourListUtil.toRGBA(
                Past.settingsManager.getSettingID("HudEditorRed").getValueInt(),
                Past.settingsManager.getSettingID("HudEditorGreen").getValueInt(),
                Past.settingsManager.getSettingID("HudEditorBlue").getValueInt(),
                Past.settingsManager.getSettingID("HudEditorAlpha").getValueInt()
        );
    }
}
