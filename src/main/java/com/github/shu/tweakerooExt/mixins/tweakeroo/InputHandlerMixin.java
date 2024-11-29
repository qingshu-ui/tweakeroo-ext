package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.tweakeroo.event.InputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InputHandler.class)
public abstract class InputHandlerMixin {

    @ModifyExpressionValue(
            method = "addKeysToMap",
            at = @At(
                    value = "FIELD",
                    target = "Lfi/dy/masa/tweakeroo/config/Configs$Disable;OPTIONS:Lcom/google/common/collect/ImmutableList;"
            ),
            remap = false
    )
    private ImmutableList<IConfigBase> onAddKeysToMap(ImmutableList<IHotkeyTogglable> original){
        return ConfigsExt.add(original, ConfigsExt.DisableExt.OPTIONS);
    }
}
