package com.github.shu.tweakerooExt.mixins.tweakeroo;

import com.github.shu.tweakerooExt.client.Reference;
import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.tweakeroo.InitHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InitHandler.class)
public abstract class InitHandlerMixin {

    @Inject(
            method = "registerModHandlers",
            at = @At("HEAD"),
            remap = false
    )
    private void onRegisterModHandlers(CallbackInfo ci) {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new ConfigsExt());
    }
}
