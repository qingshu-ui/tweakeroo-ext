package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.mixinInterface.IHandledScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("SpellCheckingInspection")
@Mixin(targets = "fi.dy.masa.tweakeroo.tweaks.MiscTweaks$KeybindState")
public abstract class KeybindStateMixin {

    @Shadow
    @Final
    private KeyBinding keybind;

    @Inject(
            method = "handlePeriodicClick",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"
            )
    )
    private void onHandlePeriodicClick(int interval, MinecraftClient mc, CallbackInfo ci) {
        if (keybind == mc.options.attackKey && mc.currentScreen instanceof GenericContainerScreen) {
            onPeriodicClickHandle(mc);
        }
    }

    @Unique
    private void onPeriodicClickHandle(MinecraftClient client) {
        if (client.player == null || client.interactionManager == null || !(client.currentScreen instanceof GenericContainerScreen currentScreen))
            return;
        if (!(client.player.currentScreenHandler instanceof GenericContainerScreenHandler handler)) return;
        Slot slot = ((IHandledScreen) currentScreen).tweakeroo_ext$getFocusedSlot();
        if (slot == null) return;
        client.interactionManager.clickSlot(handler.syncId, slot.id, 0, SlotActionType.QUICK_MOVE, client.player);
    }
}
