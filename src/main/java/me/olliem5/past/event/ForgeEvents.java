package me.olliem5.past.event;

import me.olliem5.past.Past;
import me.olliem5.past.gui.editor.component.HudComponent;
import me.olliem5.past.module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ForgeEvents {

    //Toggling modules when their keybind is pressed
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (Module modules : Past.moduleManager.getModules()) {
                if (modules.getKey() == Keyboard.getEventKey()) {
                    modules.toggle();
                }
            }
        }
    }

    //Update for modules
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        for (Module module : Past.moduleManager.getModules()) {
            if (module.isToggled()) {
                module.onUpdate();
            }
        }
    }

    //Drawing enabled HUD Components
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        for (HudComponent hudComponent : Past.hudComponentManager.getHudComponents()) {
            if (hudComponent.isEnabled()) {
                hudComponent.render(event.getPartialTicks());
            }
        }
    }
}
