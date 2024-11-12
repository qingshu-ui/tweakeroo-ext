package com.github.shu.tweakerooExt.mixins.tweakeroo;

import fi.dy.masa.tweakeroo.tweaks.MiscTweaks;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MiscTweaks.class)
public abstract class MiscTweaksMixin {

    @Redirect(
            method = "doPeriodicClicks",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/GuiUtils;getCurrentScreen()Lnet/minecraft/client/gui/screen/Screen;"
            )
    )
    private static Screen onDoPeriodicClicks() {
        return null;
    }
}
