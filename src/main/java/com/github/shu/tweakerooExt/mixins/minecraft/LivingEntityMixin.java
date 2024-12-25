package com.github.shu.tweakerooExt.mixins.minecraft;

import com.github.shu.tweakerooExt.tweakeroo.FeatureToggleExt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.shu.tweakerooExt.client.Reference.mc;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    // fake night vision
    @Inject(
            method = "hasStatusEffect",
            at = @At("HEAD"),
            cancellable = true
    )
    private void fakeNightVisionInject(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggleExt.TWEAK_FAKE_NIGHT.getBooleanValue()) {
            LivingEntity self = (LivingEntity) (Object) this;
            if (effect == StatusEffects.NIGHT_VISION && (self == mc.player && self == mc.gameRenderer.getCamera().getFocusedEntity())) {
                cir.setReturnValue(true);
            }
        }
    }
}
