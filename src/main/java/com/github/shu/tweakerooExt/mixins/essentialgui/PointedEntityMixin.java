package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import com.github.shu.tweakerooExt.utils.RayCastUtils;
import fi.dy.masa.tweakeroo.util.CameraEntity;
import lordrius.essentialgui.gui.hud.PointedEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.HitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PointedEntity.class)
public abstract class PointedEntityMixin {

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

    @Redirect(
            method = "drawPlayerSkin",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/MinecraftClient;player:Lnet/minecraft/client/network/ClientPlayerEntity;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private ClientPlayerEntity onDrawPlayerSkin(MinecraftClient instance) {
        CameraEntity cameraEntity = FreeCameraExt.getCameraEntity();
        if (null != cameraEntity) {
            return cameraEntity;
        }
        return instance.player;
    }

    @Redirect(
            method = "getEntityExtendedProperties",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/MinecraftClient;player:Lnet/minecraft/client/network/ClientPlayerEntity;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private ClientPlayerEntity onGetEntityExtendedProperties(MinecraftClient instance) {
        CameraEntity cameraEntity = FreeCameraExt.getCameraEntity();
        if (null != cameraEntity) {
            return cameraEntity;
        }
        return instance.player;
    }


    @Redirect(
            method = "isFoxTrustingPlayer",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/MinecraftClient;player:Lnet/minecraft/client/network/ClientPlayerEntity;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private ClientPlayerEntity onIsFoxTrustingPlayer(MinecraftClient instance) {
        CameraEntity cameraEntity = FreeCameraExt.getCameraEntity();
        if (null != cameraEntity) {
            return cameraEntity;
        }
        return instance.player;
    }

}
