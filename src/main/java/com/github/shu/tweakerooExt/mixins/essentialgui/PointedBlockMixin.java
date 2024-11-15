package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import com.github.shu.tweakerooExt.utils.RayCastUtils;
import fi.dy.masa.tweakeroo.util.CameraEntity;
import lordrius.essentialgui.gui.hud.PointedBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PointedBlock.class)
public abstract class PointedBlockMixin {

    @Redirect(
            method = "<init>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private HitResult onInit(MinecraftClient instance) {
        CameraEntity cameraEntity = FreeCameraExt.getCameraEntity();
        if (null != cameraEntity) {
            return RayCastUtils.rayCast(instance, cameraEntity);
        }
        return instance.crosshairTarget;
    }
}
