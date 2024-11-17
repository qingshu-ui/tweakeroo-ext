package com.github.shu.tweakerooExt.client

import net.fabricmc.api.ClientModInitializer

@Suppress("SpellCheckingInspection")
class TweakerooExtClient : ClientModInitializer {

    override fun onInitializeClient() {
        Reference.init()
    }
}
