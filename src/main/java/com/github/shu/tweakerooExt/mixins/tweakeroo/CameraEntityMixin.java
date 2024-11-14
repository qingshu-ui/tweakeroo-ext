package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import fi.dy.masa.tweakeroo.util.CameraEntity;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CameraEntity.class, remap = false)
public abstract class CameraEntityMixin {

    @Shadow
    @Nullable
    private static CameraEntity camera;

    @Inject(
            method = "createAndSetCamera",
            at = @At("TAIL")
    )
    private static void onCreateAndSetCamera(MinecraftClient mc, CallbackInfo ci) {
        FreeCameraExt.setCameraEntity(camera);
    }

    @Inject(
            method = "removeCamera",
            at = @At("TAIL")
    )
    private static void onRemoveCamera(MinecraftClient mc, CallbackInfo ci) {
        FreeCameraExt.setCameraEntity(null);
    }
}
