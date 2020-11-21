package me.olliem5.past.module.modules.movement;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.colour.ColourUtil;
import me.olliem5.past.util.module.MathUtil;
import net.minecraft.network.play.client.CPacketEntityAction;

import java.util.ArrayList;

public class ElytraFly extends Module {
    public ElytraFly() {
        super("ElytraFly", "Allows you to fly infinitely with an elytra", Category.MOVEMENT);
    }

    Setting flymode;
    Setting speed;
    Setting upspeed;
    Setting downspeed;

    private ArrayList<String> flymodes;

    @Override
    public void setup() {
        flymodes = new ArrayList<>();
        flymodes.add("Control");

        Past.settingsManager.registerSetting(flymode = new Setting("Mode", "ElytraFlyFlyMode", this, flymodes, "Control"));
        Past.settingsManager.registerSetting(speed = new Setting("Speed", "ElytraFlySpeed", 0, 1, 10, this));
        Past.settingsManager.registerSetting(upspeed = new Setting("Up Speed", "ElytraFlyUpSpeed", 0, 1, 10, this));
        Past.settingsManager.registerSetting(downspeed = new Setting("Down Speed", "ElytraFlyDownSpeed", 0, 1, 10, this));
    }

    @Override
    public void onEnable() {
        if (mc.player == null) {
            return;
        }

        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
    }

    public void onUpdate() {
        if (mc.player == null) {
            return;
        }

        if (flymode.getValueString() == "Control") {

            if (mc.player.isElytraFlying()) {

                final double[] forwardDirectionSpeed = MathUtil.directionSpeed(speed.getValueInt());
                mc.player.setVelocity(0, 0, 0);

                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.motionY += upspeed.getValueInt();
                }

                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.motionY -= downspeed.getValueInt();
                }

                if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
                    mc.player.motionX = forwardDirectionSpeed[0];
                    mc.player.motionZ = forwardDirectionSpeed[1];
                } else {
                    mc.player.motionX = 0;
                    mc.player.motionZ = 0;
                }
            }
        }
    }

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + flymode.getValueString().toUpperCase();
    }
}
