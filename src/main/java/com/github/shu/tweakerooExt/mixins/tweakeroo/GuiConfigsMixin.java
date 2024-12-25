package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import com.github.shu.tweakerooExt.tweakeroo.FeatureToggleExt;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.BooleanHotkeyGuiWrapper;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.tweakeroo.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Mixin(value = GuiConfigs.class)
public abstract class GuiConfigsMixin {

    @Shadow
    public static ImmutableList<FeatureToggle> TWEAK_LIST;

    @Shadow
    protected abstract BooleanHotkeyGuiWrapper wrapConfig(FeatureToggle config);

    @ModifyExpressionValue(
            method = "getConfigs",
            at = @At(
                    value = "FIELD",
                    target = "Lfi/dy/masa/tweakeroo/config/Configs$Generic;OPTIONS:Lcom/google/common/collect/ImmutableList;"
            ),
            remap = false
    )
    private ImmutableList<IConfigBase> onGetGeneric(ImmutableList<IConfigBase> original) {
        return ConfigsExt.add(original, ConfigsExt.GenericExt.OPTIONS);
    }

    @ModifyArg(
            method = "getConfigs",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/GuiConfigsBase$ConfigOptionWrapper;createFor(Ljava/util/Collection;)Ljava/util/List;",
                    ordinal = 0
            ),
            remap = false
    )
    private Collection<? extends IConfigBase> onGetDisables(Collection<? extends IConfigBase> original) {
        return ConfigsExt.add(original, ConfigsExt.DisableExt.OPTIONS);
    }

    @Inject(
            method = "getConfigs",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/GuiConfigsBase$ConfigOptionWrapper;createFor(Ljava/util/Collection;)Ljava/util/List;",
                    ordinal = 1
            ),
            cancellable = true,
            remap = false
    )
    private void injectFeatureToggles(CallbackInfoReturnable<List<GuiConfigsBase.ConfigOptionWrapper>> cir) {
        List<BooleanHotkeyGuiWrapper> allTweaks = Stream.concat(
                TWEAK_LIST.stream().map(this::wrapConfig),
                FeatureToggleExt.VALUES.stream().map(this::extWrapConfig)
        ).toList();
        cir.setReturnValue(GuiConfigsBase.ConfigOptionWrapper.createFor(allTweaks));
    }

    @Unique
    private BooleanHotkeyGuiWrapper extWrapConfig(FeatureToggleExt config) {
        return new BooleanHotkeyGuiWrapper(config.getName(), config, config.getKeybind());
    }
}
