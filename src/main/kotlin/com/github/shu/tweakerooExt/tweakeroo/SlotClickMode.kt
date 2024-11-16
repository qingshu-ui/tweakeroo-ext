package com.github.shu.tweakerooExt.tweakeroo

import fi.dy.masa.malilib.config.IConfigOptionListEntry
import fi.dy.masa.malilib.util.StringUtils
import net.minecraft.screen.slot.SlotActionType

enum class SlotClickMode(
    private val configString: String,
    private val translationKey: String,
    val action: SlotActionType?
) :
    IConfigOptionListEntry {

    NONE("none", "tweakeroo-ext.label.slot_click_mode.none", null),
    PICKUP("pickup", "tweakeroo-ext.label.slot_click_mode.pickup", SlotActionType.PICKUP),
    QUICK_MOVE("quick_move", "tweakeroo-ext.label.slot_click_mode.quick_move", SlotActionType.QUICK_MOVE),
    SWAP("swap", "tweakeroo-ext.label.slot_click_mode.swap", SlotActionType.SWAP),
    CLONE("clone", "tweakeroo-ext.label.slot_click_mode.clone", SlotActionType.CLONE),
    THROW("throw", "tweakeroo-ext.label.slot_click_mode.throw", SlotActionType.THROW),
    QUICK_CRAFT("quick_craft", "tweakeroo-ext.label.slot_click_mode.quick_craft", SlotActionType.QUICK_CRAFT),
    PICKUP_ALL("pickup_all", "tweakeroo-ext.label.slot_click_mode.pickup_all", SlotActionType.PICKUP_ALL);

    companion object {
        @JvmStatic
        fun fromStringStatic(name: String): SlotClickMode {
            for (entry in entries) {
                if (entry.configString.equals(name, ignoreCase = true)) {
                    return entry
                }
            }
            return QUICK_MOVE
        }
    }

    override fun getStringValue(): String {
        return configString
    }

    override fun getDisplayName(): String {
        return StringUtils.translate(translationKey)
    }

    override fun cycle(p0: Boolean): IConfigOptionListEntry {
        var id = this.ordinal
        if (p0) {
            if (++id >= entries.size) id = 0
        } else {
            if (--id < 0) id = entries.size - 1
        }
        return entries[id % entries.size]
    }

    override fun fromString(p0: String): IConfigOptionListEntry {
        return fromStringStatic(p0)
    }
}