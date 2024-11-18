package com.github.shu.tweakerooExt.utils

import com.github.shu.tweakerooExt.client.Reference.mc
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult

object Utils {

    @JvmStatic
    fun shiftClick(): Boolean {
        if (mc.crosshairTarget == null) {
            return false
        } else if (mc.player?.isRiding == true) return false
        else {
            val itemStack = mc.player!!.getStackInHand(Hand.MAIN_HAND)
            if (!itemStack.isItemEnabled(mc.world?.enabledFeatures)) {
                return false
            } else {
                var bl = false
                when (mc.crosshairTarget!!.type) {
                    HitResult.Type.MISS -> {
                        mc.player?.resetLastAttackedTicks()
                    }

                    HitResult.Type.BLOCK -> {
                        val blockHitResult = mc.crosshairTarget as BlockHitResult
                        val blockPos = blockHitResult.blockPos
                        if (mc.world?.getBlockState(blockPos)?.isAir != true) {
                            mc.interactionManager?.attackBlock(blockPos, blockHitResult.side)
                            if (mc.world?.getBlockState(blockPos)?.isAir == true) {
                                bl = true
                            }
                        }
                    }

                    HitResult.Type.ENTITY -> {
                        mc.interactionManager?.attackEntity(mc.player, (mc.crosshairTarget as EntityHitResult).entity)
                    }

                    else -> {}
                }
                mc.player?.swingHand(Hand.MAIN_HAND)
                return bl
            }
        }
    }
}