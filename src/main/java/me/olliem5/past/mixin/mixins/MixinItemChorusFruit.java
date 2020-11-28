package me.olliem5.past.mixin.mixins;

import me.olliem5.past.Past;
import me.olliem5.past.event.events.ChorusTeleportEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemChorusFruit.class)
public class MixinItemChorusFruit {
    @Inject(method = "onItemUseFinish", at = @At("HEAD"), cancellable = true)
    private void finishUse(ItemStack itemStack, World world, EntityLivingBase entityLivingBase, CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        ChorusTeleportEvent event = new ChorusTeleportEvent();
        Past.EVENT_BUS.post(event);
    }
}
