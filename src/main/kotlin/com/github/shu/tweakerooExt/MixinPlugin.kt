package com.github.shu.tweakerooExt

import net.fabricmc.loader.api.FabricLoader
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

var isTweakerooPresent = false
const val mixinPackageTweakerooExt = "com.github.shu.tweakerooExt.mixins"

class MixinPlugin : IMixinConfigPlugin {

    override fun onLoad(mixinPackage: String?) {
        val loader = FabricLoader.getInstance()
        isTweakerooPresent = loader.isModLoaded("tweakeroo")
    }


    override fun getRefMapperConfig(): String? {
        return null
    }


    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        return mixinClassName.startsWith("$mixinPackageTweakerooExt.tweakeroo")
    }


    override fun acceptTargets(myTargets: MutableSet<String>?, otherTargets: MutableSet<String>?) {

    }


    override fun getMixins(): MutableList<String>? {
        return null
    }


    override fun preApply(
        targetClassName: String?,
        targetClass: ClassNode?,
        mixinClassName: String?,
        mixinInfo: IMixinInfo?
    ) {

    }


    override fun postApply(
        targetClassName: String?,
        targetClass: ClassNode?,
        mixinClassName: String?,
        mixinInfo: IMixinInfo?
    ) {
    }
}