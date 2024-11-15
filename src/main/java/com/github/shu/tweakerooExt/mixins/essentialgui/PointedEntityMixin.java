package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
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
        if (null != FreeCameraExt.getCameraEntity() && null != FreeCameraExt.getCrosshairTarget()) {
            return FreeCameraExt.getCrosshairTarget();
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
        if (null != FreeCameraExt.getCameraEntity()) {
            return FreeCameraExt.getCameraEntity();
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
        if (null != FreeCameraExt.getCameraEntity()) {
            return FreeCameraExt.getCameraEntity();
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
        if (null != FreeCameraExt.getCameraEntity()) {
            return FreeCameraExt.getCameraEntity();
        }
        return instance.player;
    }

}
