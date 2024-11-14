package com.github.shu.tweakerooExt.mixinInterface

import net.minecraft.screen.slot.Slot

@Suppress("FunctionName")
interface IHandledScreen {
    fun `tweakeroo_ext$getSlotAt`(iX: Double, iY: Double): Slot?
    fun `tweakeroo_ext$getTouchHoveredSlot`(): Slot?
    fun `tweakeroo_ext$getFocusedSlot`(): Slot?
}