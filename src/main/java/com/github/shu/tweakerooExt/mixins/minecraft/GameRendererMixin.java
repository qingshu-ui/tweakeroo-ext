package com.github.shu.tweakerooExt.mixins.minecraft;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import com.github.shu.tweakerooExt.utils.RayCastUtils;
import fi.dy.masa.tweakeroo.util.CameraEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("SpellCheckingInspection")
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    MinecraftClient client;

    @Inject(
            method = "updateCrosshairTarget",
            at = @At("TAIL")
    )
    private void onUpdateCrosshairTarget(float tickDelta, CallbackInfo ci) {
        CameraEntity cameraEntity = FreeCameraExt.getCameraEntity();
        if (null != cameraEntity) {
            HitResult hitResult = RayCastUtils.rayCast(client, cameraEntity, tickDelta);
            FreeCameraExt.setCrosshairTarget(hitResult);
            if (hitResult instanceof EntityHitResult entityHitResult) {
                FreeCameraExt.setTargetedEntity(entityHitResult.getEntity());
            } else FreeCameraExt.setTargetedEntity(null);
        }
    }
}
