package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.tweakeroo.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Collection;

@Mixin(value = GuiConfigs.class)
public abstract class GuiConfigsMixin {

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
}
