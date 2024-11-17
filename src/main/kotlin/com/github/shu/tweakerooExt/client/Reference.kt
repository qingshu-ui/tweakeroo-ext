package com.github.shu.tweakerooExt.client

import com.github.shu.tweakerooExt.utils.PreInit
import com.github.shu.tweakerooExt.utils.ReflectInit
import meteordevelopment.orbit.EventBus
import net.minecraft.client.MinecraftClient

object Reference {
    const val MOD_ID = "tweakeroo-ext"
    val mc: MinecraftClient = MinecraftClient.getInstance()

    @JvmField
    val EVENT_BUS = EventBus()
    private const val ROOT_PACKAGE = "com.github.shu.tweakerooExt"

    fun init() {
        ReflectInit.registerPackage(ROOT_PACKAGE)
        ReflectInit.init(PreInit::class)
    }
}