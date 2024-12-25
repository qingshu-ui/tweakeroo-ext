package com.github.shu.tweakerooExt.client

import meteordevelopment.orbit.EventBus
import net.minecraft.client.MinecraftClient
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

object Reference {
    const val MOD_ID = "tweakeroo-ext"
    @JvmField
    val mc: MinecraftClient = MinecraftClient.getInstance()

    @JvmField
    val EVENT_BUS = EventBus().apply {
        registerLambdaFactory(ROOT_PACKAGE) { method: Method, clazz: Class<*> ->
            method.invoke(null, clazz, MethodHandles.lookup()) as MethodHandles.Lookup?
        }
    }

    private const val ROOT_PACKAGE = "com.github.shu.tweakerooExt"

    fun init() {

    }
}