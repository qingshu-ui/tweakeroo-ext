package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import fi.dy.masa.tweakeroo.util.CameraEntity;
import lordrius.essentialgui.gui.hud.PointedBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PointedBlock.class)
public abstract class PointedBlockMixin {

    @Shadow
    private MinecraftClient client;

    @ModifyVariable(
            method = "<init>",
            at = @At("STORE")
    )
    private BlockPos onInit(BlockPos value) {
        CameraEntity cameraEntity = FreeCameraExt.getCameraEntity();
        if (cameraEntity != null) {
            HitResult hitResult = cameraEntity.raycast(4.5, client.getRenderTickCounter().getTickDelta(true), false);
            if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                return ((BlockHitResult) hitResult).getBlockPos();
            }
        }
        return value;
    }
}
