package com.github.shu.tweakerooExt.client

import net.fabricmc.api.ClientModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@JvmField
val log: Logger = LogManager.getLogger(Reference.MOD_ID)

@Suppress("SpellCheckingInspection")
class TweakerooExtClient : ClientModInitializer {

    override fun onInitializeClient() {
        Reference.init()
    }
}
