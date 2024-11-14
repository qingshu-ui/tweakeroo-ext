package com.github.shu.tweakerooExt.mixins.minecraft;

import com.github.shu.tweakerooExt.mixinInterface.IHandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin implements IHandledScreen {

    @Shadow
    @Nullable
    protected Slot focusedSlot;
    @Shadow
    private @Nullable Slot touchHoveredSlot;

    @Shadow
    @Nullable
    protected abstract Slot getSlotAt(double x, double y);

    @Nullable
    @Override
    public Slot tweakeroo_ext$getSlotAt(double iX, double iY) {
        return getSlotAt(iX, iY);
    }

    @Nullable
    @Override
    public Slot tweakeroo_ext$getTouchHoveredSlot() {
        return null;
    }

    @Nullable
    @Override
    public Slot tweakeroo_ext$getFocusedSlot() {
        return focusedSlot;
    }
}
