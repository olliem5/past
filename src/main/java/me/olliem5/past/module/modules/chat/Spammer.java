package me.olliem5.past.module.modules.chat;

import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;

import java.io.BufferedReader;
import java.io.FileReader;

public class Spammer extends Module {
    public Spammer() {
        super("Spammer", "Spams text from a file", Category.CHAT); //TODO: Delay
    }

    public void onUpdate() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(mc.gameDir.getAbsolutePath() + "/PastSpammer.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                mc.player.sendChatMessage(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
        }
    }
}
