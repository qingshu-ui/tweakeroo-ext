package com.github.shu.tweakerooExt

import net.fabricmc.loader.api.FabricLoader
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

var isTweakerooPresent = false
var isEssentailGUIPresent = false
const val mixinPackageTweakerooExt = "com.github.shu.tweakerooExt.mixins"

class MixinPlugin : IMixinConfigPlugin {

    override fun onLoad(mixinPackage: String?) {
        val loader = FabricLoader.getInstance()
        isTweakerooPresent = loader.isModLoaded("tweakeroo")
        isEssentailGUIPresent = loader.isModLoaded("essentialgui")
    }


    override fun getRefMapperConfig(): String? {
        return null
    }


    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        if (mixinClassName.startsWith("$mixinPackageTweakerooExt.minecraft")) return true
        if (mixinClassName.startsWith("$mixinPackageTweakerooExt.tweakeroo") && isTweakerooPresent) return true
        if (mixinClassName.startsWith("$mixinPackageTweakerooExt.essentialgui") && isEssentailGUIPresent) return true
        return false
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