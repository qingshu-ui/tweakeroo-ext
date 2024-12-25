package com.github.shu.tweakerooExt.mixins.minecraft;

import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public abstract class LightMapTextureManagerMixin {

    @Redirect(
            method = "getDarknessFactor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/effect/StatusEffectInstance;getFadeFactor(Lnet/minecraft/entity/LivingEntity;F)F"
            )
    )
    private float disableDarknessEffect_doesNotHaveStatusEffect(StatusEffectInstance instance, LivingEntity entity, float tickDelta) {
        if (ConfigsExt.DisableExt.DISABLE_DARKNESS.getBooleanValue()) {
            return 0.0F;
        }
        return instance.getFadeFactor(entity, tickDelta);
    }
}
