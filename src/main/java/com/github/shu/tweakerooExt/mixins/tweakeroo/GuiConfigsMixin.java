package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.tweakeroo.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GuiConfigs.class)
public abstract class GuiConfigsMixin {

    @Redirect(
            method = "getConfigs",
            at = @At(
                    value = "FIELD",
                    target = "Lfi/dy/masa/tweakeroo/config/Configs$Generic;OPTIONS:Lcom/google/common/collect/ImmutableList;"
            ),
            remap = false
    )
    private ImmutableList<IConfigBase> onGetConfigs() {
        return ConfigsExt.GenericExt.OPTIONS;
    }
}
