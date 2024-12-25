package com.github.shu.tweakerooExt.mixins.minecraft;

import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {"net.minecraft.client.render.BackgroundRenderer$StatusEffectFogModifier"})
public interface BackgroundRendererStatusEffectFogModifierMixin {

    @Shadow
    RegistryEntry<StatusEffect> getStatusEffect();

    @Inject(
            method = "shouldApply",
            at = @At("TAIL"),
            cancellable = true
    )
    default void disableDarknessEffect_doNotApplyIfItIsDarknessEffect(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigsExt.DisableExt.DISABLE_DARKNESS.getBooleanValue() && this.getStatusEffect() == StatusEffects.DARKNESS) {
            cir.setReturnValue(false);
        }
    }
}
