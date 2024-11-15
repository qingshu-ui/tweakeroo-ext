package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import com.github.shu.tweakerooExt.utils.RayCastUtils;
import fi.dy.masa.tweakeroo.util.CameraEntity;
import lordrius.essentialgui.gui.hud.PlayerEquipment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEquipment.class)
public abstract class PlayerEquipmentMixin {

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
        if (cameraEntity != null) {
            return RayCastUtils.rayCast(instance, cameraEntity);
        }
        return instance.crosshairTarget;
    }
}