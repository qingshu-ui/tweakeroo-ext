package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.mixinInterface.IHandledScreen;
import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import com.github.shu.tweakerooExt.tweakeroo.SlotClickMode;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
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

    @Shadow
    private int intervalCounter;

    @Shadow
    private int durationCounter;

    @Inject(
            method = "handlePeriodicClick",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"
            ),
            cancellable = true
    )
    private void onHandlePeriodicClick(int interval, MinecraftClient mc, CallbackInfo ci) {
        if (GuiUtils.getCurrentScreen() instanceof GenericContainerScreen currentScreen) {
            ci.cancel();
            handleUseClick(mc);
            handleSlotClick(mc, currentScreen);
            intervalCounter = 0;
            durationCounter = 0;
        }
    }

    @Unique
    private void handleSlotClick(MinecraftClient mc, GenericContainerScreen currentScreen) {
        if (
                keybind == mc.options.attackKey &&
                ConfigsExt.GenericExt.SLOT_CLICK_ON_PERIODIC_ATTACK.getOptionListValue() != SlotClickMode.NONE ||
                keybind == mc.options.useKey &&
                ConfigsExt.GenericExt.SLOT_CLICK_ON_PERIODIC_USE.getOptionListValue() != SlotClickMode.NONE
        ) {
            onPeriodicClickHandle(mc, currentScreen);
        }
    }

    @Unique
    private void handleUseClick(MinecraftClient mc) {
        if (keybind == mc.options.useKey && FeatureToggle.TWEAK_PERIODIC_USE.getKeybind().getSettings().getContext() != KeybindSettings.Context.ANY) {
            FeatureToggle.TWEAK_PERIODIC_USE.setBooleanValue(false);
            FeatureToggle.TWEAK_PERIODIC_USE.onValueChanged();
        }
    }

    @Unique
    private void onPeriodicClickHandle(MinecraftClient client, GenericContainerScreen currentScreen) {
        if (client.player == null || client.interactionManager == null || !(currentScreen instanceof GenericContainerScreen))
            return;
        if (!(client.player.currentScreenHandler instanceof GenericContainerScreenHandler handler)) return;
        Slot slot = ((IHandledScreen) currentScreen).tweakeroo_ext$getFocusedSlot();
        if (slot == null) return;
        SlotActionType action = getActionType(client);

        int button = switch (action) {
            case SWAP, PICKUP_ALL, QUICK_MOVE, QUICK_CRAFT -> 0;
            case CLONE -> 2;
            case THROW, PICKUP -> 1;
            case null -> 0;
        };
        client.interactionManager.clickSlot(handler.syncId, slot.id, button, action, client.player);
    }

    @Unique
    private @Nullable SlotActionType getActionType(MinecraftClient client) {
        return keybind == client.options.useKey ?
                ((SlotClickMode) ConfigsExt.GenericExt.SLOT_CLICK_ON_PERIODIC_USE.getOptionListValue()).getAction() :
                ((SlotClickMode) ConfigsExt.GenericExt.SLOT_CLICK_ON_PERIODIC_ATTACK.getOptionListValue()).getAction();
    }
}
