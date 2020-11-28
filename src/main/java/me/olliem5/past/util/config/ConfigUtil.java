package me.olliem5.past.util.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.olliem5.past.Past;
import me.olliem5.past.friends.Friend;
import me.olliem5.past.gui.click.ClickGUI;
import me.olliem5.past.gui.click.Panel;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.gui.editor.component.HudComponentManager;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class ConfigUtil {
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    static JsonParser parser = new JsonParser();
    public static void load(){
        try{
            JsonParser jsonParser = new JsonParser();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("PastConfig.json"));
            JsonElement jsonElement = jsonParser.parse(reader);

            //Loading Modules
            JsonElement moduleMap = jsonElement.getAsJsonObject().get("modules");
            if(moduleMap != null){
                for(Module module: Past.moduleManager.getModules()) {
                    JsonElement tempModuleMap = moduleMap.getAsJsonObject().get(module.getName());
                    if (tempModuleMap != null) {
                        module.setKey(Keyboard.getKeyIndex(tempModuleMap.getAsJsonObject().get("bind").getAsString()));
                        if (tempModuleMap.getAsJsonObject().get("enabled").getAsBoolean()) {
                            module.toggle();
                        }

                        for (Setting setting : Past.settingsManager.getSettingsModule(module)) {
                            if (tempModuleMap.getAsJsonObject().get(setting.getName()) != null) {
                                if (setting.getType().equalsIgnoreCase("intslider")) {
                                    setting.setValueInt(tempModuleMap.getAsJsonObject().get(setting.getName()).getAsInt());
                                } else if (setting.getType().equalsIgnoreCase("boolean")){
                                    setting.setValBoolean(tempModuleMap.getAsJsonObject().get(setting.getName()).getAsBoolean());
                                }else if (setting.getType().equalsIgnoreCase("mode")){
                                    setting.setValueString(tempModuleMap.getAsJsonObject().get(setting.getName()).getAsString());
                                }
                            }
                        }

                    }
                }
            }
            //loading Clickgui panels from config
            JsonElement clickMap = jsonElement.getAsJsonObject().get("clickframes");
            if(clickMap != null){
                for(Panel panel: ClickGUI.getPanels()){
                    JsonElement tempPanelMap = clickMap.getAsJsonObject().get(panel.title);
                    if(tempPanelMap != null){
                        if(tempPanelMap.getAsJsonObject().get("x") != null ){
                            panel.setX(tempPanelMap.getAsJsonObject().get("x").getAsInt());
                        }
                        if(tempPanelMap.getAsJsonObject().get("y") != null ){
                            panel.setY(tempPanelMap.getAsJsonObject().get("y").getAsInt());
                        }
                        if(tempPanelMap.getAsJsonObject().get("open") != null ){
                            panel.setOpen(tempPanelMap.getAsJsonObject().get("open").getAsBoolean());
                        }
                    }
                }
            }
            JsonElement hudcomponents = jsonElement.getAsJsonObject().get("hudcomponents");
            if(hudcomponents != null){
                for(HudComponent component: HudComponentManager.getHudComponents()){
                    JsonElement tempCompMap = hudcomponents.getAsJsonObject().get(component.getName());
                    if(tempCompMap != null){
                        if(tempCompMap.getAsJsonObject().get("x") != null ){
                            component.setX(tempCompMap.getAsJsonObject().get("x").getAsInt());
                        }
                        if(tempCompMap.getAsJsonObject().get("y") != null ){
                            component.setY(tempCompMap.getAsJsonObject().get("y").getAsInt());
                        }
                        if(tempCompMap.getAsJsonObject().get("enabled") != null ){
                            component.setEnabled(tempCompMap.getAsJsonObject().get("enabled").getAsBoolean());
                        }
                    }
                }
            }
            //load friends
            JsonArray friends = jsonElement.getAsJsonObject().get("friends").getAsJsonArray();

            for(JsonElement element:friends){
                Past.friendsManager.addFriend(element.getAsJsonObject().get("username").getAsString());
            }


        } catch (FileNotFoundException event) {
            event.printStackTrace();
        }

    }

    public static void save(){
        JsonObject settingsarray = new JsonObject();
        JsonObject moduleArray = new JsonObject();
        for(Module module: Past.moduleManager.getModules()){
            JsonObject moduleSettingsArray = new JsonObject();
            moduleSettingsArray.addProperty("bind", Keyboard.getKeyName(module.getKey()));
            moduleSettingsArray.addProperty("enabled", module.isToggled());
            for(Setting setting:Past.settingsManager.getSettingsModule(module)){
                if(setting.getType().equalsIgnoreCase("intslider")){
                    moduleSettingsArray.addProperty(setting.getName(), setting.getValueInt());
                }else if (setting.getType().equalsIgnoreCase("boolean")){
                    moduleSettingsArray.addProperty(setting.getName(), setting.getValBoolean());
                }else if (setting.getType().equalsIgnoreCase("mode")) {
                    moduleSettingsArray.addProperty(setting.getName(), setting.getValueString());
                }
            }
            moduleArray.add(module.getName(), moduleSettingsArray);
        }
        settingsarray.add("modules", moduleArray);
        //ClickGUI Frames
        JsonObject panelsArray = new JsonObject();
        for(Panel panel: ClickGUI.getPanels()){
            JsonObject tempPanelArray = new JsonObject();
            tempPanelArray.addProperty("x", panel.getX());
            tempPanelArray.addProperty("y", panel.getY());
            tempPanelArray.addProperty("open", panel.isOpen());
            panelsArray.add(panel.title,tempPanelArray);
        }
        settingsarray.add("clickframes", panelsArray);
        JsonObject componentsArray = new JsonObject();
        for(HudComponent component:HudComponentManager.getHudComponents()){
            JsonObject tempcompArray = new JsonObject();
            tempcompArray.addProperty("x", component.getX());
            tempcompArray.addProperty("y", component.getY());
            tempcompArray.addProperty("enabled", component.isEnabled());
            componentsArray.add(component.getName(), tempcompArray);
        }
        settingsarray.add("hudcomponents", componentsArray);
        //friends
        JsonArray friends = new JsonArray();
        for(Friend friend:Past.friendsManager.getFriends()){
            friends.add(parser.parse(gson.toJson(friend)).getAsJsonObject());
        }
        settingsarray.add("friends", friends);
        try{
            FileWriter fw = new FileWriter("PastConfig.json");
            gson.toJson(settingsarray, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void init() {
        if(new File("PastConfig.json").exists()){
            System.out.println("loading settings");
            load();
        }else{
            System.out.println("Saving settings");
            save();
        }
    }
}