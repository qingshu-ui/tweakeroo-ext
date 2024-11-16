package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;",
                    ordinal = 1
            )
    )
    private HitResult onRender(MinecraftClient instance) {
        if (FreeCameraExt.getCameraEntity() != null && FreeCameraExt.getCrosshairTarget() != null) {
            return FreeCameraExt.getCrosshairTarget();
        }
        return instance.crosshairTarget;
    }
}
