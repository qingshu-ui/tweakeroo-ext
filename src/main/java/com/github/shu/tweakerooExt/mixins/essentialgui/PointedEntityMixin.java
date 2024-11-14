package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import fi.dy.masa.tweakeroo.util.CameraEntity;
import lordrius.essentialgui.gui.hud.PointedEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PointedEntity.class)
public abstract class PointedEntityMixin {

    @Shadow
    private MinecraftClient client;

    @ModifyVariable(
            method = "<init>",
            at = @At("STORE")
    )
    private Entity onInit(Entity value) {
        CameraEntity cameraEntity = FreeCameraExt.getCameraEntity();
        if (null != cameraEntity) {
            HitResult hitResult = cameraEntity.raycast(4.5, client.getRenderTickCounter().getTickDelta(true), false);
            if (null != hitResult && hitResult.getType() == HitResult.Type.ENTITY) {
                return ((EntityHitResult) hitResult).getEntity();
            }
        }
        return value;
    }
}
