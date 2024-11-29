package com.github.shu.tweakerooExt.mixins.minecraft;

import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Redirect(
            method = "hasBlindnessOrDarkness",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z"
            )
    )
    private boolean disableDarknessEffect_doesNotHaveStatusEffect(LivingEntity instance, RegistryEntry<StatusEffect> effect) {
        if (ConfigsExt.DisableExt.DISABLE_DARKNESS.getBooleanValue() && effect == StatusEffects.DARKNESS) {
            return false;
        }
        return instance.hasStatusEffect(effect);
    }
}
