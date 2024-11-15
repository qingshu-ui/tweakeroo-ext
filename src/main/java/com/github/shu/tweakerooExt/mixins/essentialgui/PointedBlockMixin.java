package com.github.shu.tweakerooExt.mixins.essentialgui;

import com.github.shu.tweakerooExt.tweakeroo.FreeCameraExt;
import lordrius.essentialgui.gui.hud.PointedBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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
        if (null != FreeCameraExt.getCameraEntity() && null != FreeCameraExt.getCrosshairTarget()) {
            return FreeCameraExt.getCrosshairTarget();
        }
        return instance.crosshairTarget;
    }

    @Redirect(
            method = "drawPointedBlock",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/MinecraftClient;player:Lnet/minecraft/client/network/ClientPlayerEntity;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private ClientPlayerEntity onDrawPointedBlock(MinecraftClient instance) {
        if (null != FreeCameraExt.getCameraEntity()) {
            return FreeCameraExt.getCameraEntity();
        }
        return instance.player;
    }
}
