package com.github.shu.tweakerooExt.mixins.minecraft;

import com.github.shu.tweakerooExt.client.events.world.TickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.shu.tweakerooExt.client.Reference.EVENT_BUS;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    public abstract Profiler getProfiler();

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void onTickPre(CallbackInfo ci) {
        getProfiler().push("_pre_update");
        EVENT_BUS.post(TickEvent.Pre.INSTANCE);
        getProfiler().pop();
    }


    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void onTickPost(CallbackInfo ci) {
        getProfiler().push("_post_update");
        EVENT_BUS.post(TickEvent.Post.INSTANCE);
        getProfiler().pop();
    }
}
