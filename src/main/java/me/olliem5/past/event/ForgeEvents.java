package me.olliem5.past.event;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.module.Module;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ForgeEvents {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if(Keyboard.getEventKey() == Keyboard.KEY_NONE || Keyboard.getEventKey() == Keyboard.CHAR_NONE) return;
            for (Module modules : Past.moduleManager.getModules()) {
                if (modules.getKey() == Keyboard.getEventKey()) {
                    modules.toggle();
                }
            }
//            Past.EVENT_BUS.post(event);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        for (Module module : Past.moduleManager.getModules()) {
            if (module.isToggled()) {
                module.onUpdate();
            }
        }
//        Past.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        for (HudComponent hudComponent : Past.hudComponentManager.getHudComponents()) {
            if (hudComponent.isEnabled()) {
                hudComponent.render(event.getPartialTicks());
            }
        }
        Past.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        Past.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        Past.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void fovEvent(EntityViewRenderEvent.FOVModifier event) {
        Past.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void fogColours(EntityViewRenderEvent.FogColors event) {
        Past.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        Past.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void renderBlockOverlay(RenderBlockOverlayEvent event) {
        Past.EVENT_BUS.post(event);
    }
}
