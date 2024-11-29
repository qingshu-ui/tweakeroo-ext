package com.github.shu.tweakerooExt.tweakeroo

import com.github.shu.tweakerooExt.client.Reference
import com.github.shu.tweakerooExt.tweakeroo.ConfigsExt.GenericExt.OPTIONS
import com.google.common.collect.ImmutableList
import com.google.gson.JsonObject
import fi.dy.masa.malilib.config.ConfigUtils
import fi.dy.masa.malilib.config.IConfigBase
import fi.dy.masa.malilib.config.IConfigHandler
import fi.dy.masa.malilib.config.IHotkeyTogglable
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed
import fi.dy.masa.malilib.config.options.ConfigOptionList
import fi.dy.masa.malilib.util.FileUtils
import fi.dy.masa.malilib.util.JsonUtils
import java.io.File

const val CONFIG_FILE_NAME = "${Reference.MOD_ID}.json"

class ConfigsExt : IConfigHandler {

    companion object {
        @JvmStatic
        fun saveToFile() {
            val dir = FileUtils.getConfigDirectory()
            if ((dir.exists() && dir.isDirectory) || dir.mkdirs()) {
                val root = JsonObject()
                ConfigUtils.writeConfigBase(root, "Generic", OPTIONS)
                ConfigUtils.writeHotkeyToggleOptions(root, "DisableHotkeys", "DisableToggles", DisableExt.OPTIONS)
                JsonUtils.writeJsonToFile(root, File(dir, CONFIG_FILE_NAME))
            }
        }

        @JvmStatic
        fun loadFromFile() {
            val file = File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME)
            if (file.exists() && file.isFile && file.canRead()) {
                val element = JsonUtils.parseJsonFile(file)
                if (element != null && element.isJsonObject) {
                    val root = element.asJsonObject
                    ConfigUtils.readConfigBase(root, "Generic", OPTIONS)
                    ConfigUtils.readHotkeyToggleOptions(root, "DisableHotkeys", "DisableToggles", DisableExt.OPTIONS)
                }
            }
        }

        @JvmStatic
        fun add(original: Collection<IConfigBase>, ext: Collection<IConfigBase>): ImmutableList<IConfigBase> {
            return ImmutableList.copyOf(
                mutableListOf<IConfigBase>().apply {
                    addAll(original)
                    addAll(ext)
                }
            )
        }
    }

    object GenericExt {
        @JvmField
        val SLOT_CLICK_ON_PERIODIC_ATTACK: ConfigOptionList = ConfigOptionList(
            "slotClickOnPeriodicAttack",
            SlotClickMode.NONE,
            "tweakeroo-ext.config.generic.comment.slotClickOnPeriodicAttack"
        ).translatedName("tweakeroo-ext.config.generic.name.slotClickOnPeriodicAttack")

        @JvmField
        val SLOT_CLICK_ON_PERIODIC_USE: ConfigOptionList = ConfigOptionList(
            "slotClickOnPeriodicUse",
            SlotClickMode.NONE,
            "tweakeroo-ext.config.generic.comment.slotClickOnPeriodicUse"
        ).translatedName("tweakeroo-ext.config.generic.name.slotClickOnPeriodicUse")

        @JvmField
        val OPTIONS: ImmutableList<IConfigBase> = ImmutableList.copyOf(
            listOf(SLOT_CLICK_ON_PERIODIC_ATTACK, SLOT_CLICK_ON_PERIODIC_USE)
        )
    }

    object DisableExt {
        @JvmField
        val DISABLE_DARKNESS: ConfigBooleanHotkeyed =
            ConfigBooleanHotkeyed(
                "disableDarkness",
                false,
                "",
                "tweakeroo-ext.config.disable.comment.disableDarkness"
            ).translatedName("tweakeroo-ext.config.disable.name.disableDarkness")

        @JvmField
        val OPTIONS: ImmutableList<IHotkeyTogglable> = ImmutableList.of(
            DISABLE_DARKNESS,
        )
    }

    override fun load() {
        loadFromFile()
    }

    override fun save() {
        saveToFile()
    }
}