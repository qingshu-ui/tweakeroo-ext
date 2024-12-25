@file: Suppress("SpellCheckingInspection")

package com.github.shu.tweakerooExt.tweakeroo

import com.github.shu.tweakerooExt.client.Reference
import com.github.shu.tweakerooExt.client.log
import com.google.common.collect.ImmutableList
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import fi.dy.masa.malilib.config.ConfigType
import fi.dy.masa.malilib.config.IConfigBoolean
import fi.dy.masa.malilib.config.IConfigNotifiable
import fi.dy.masa.malilib.config.IHotkeyTogglable
import fi.dy.masa.malilib.gui.GuiBase
import fi.dy.masa.malilib.hotkeys.IKeybind
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage
import fi.dy.masa.malilib.hotkeys.KeybindMulti
import fi.dy.masa.malilib.hotkeys.KeybindSettings
import fi.dy.masa.malilib.interfaces.IValueChangeCallback
import fi.dy.masa.malilib.util.StringUtils

enum class FeatureToggleExt(
    val displayName: String,
    val defaultValue: Boolean,
    val defaultHotkey: String,
    private val singlePlayer: Boolean = false,
    settings: KeybindSettings = KeybindSettings.DEFAULT,
    private val comment: String = "${Reference.MOD_ID}.config.feature_toggle.comment.$displayName",
    private val prettyName: String = "${Reference.MOD_ID}.config.feature_toggle.prettyName.$displayName",
    private val translatedName: String = "${Reference.MOD_ID}.config.feature_toggle.name.$displayName",
) : IHotkeyTogglable, IConfigNotifiable<IConfigBoolean> {

    TWEAK_FAKE_NIGHT(displayName = "tweakFakeNightVision", defaultValue = false, defaultHotkey = "");

    companion object {
        @JvmField
        val VALUES: ImmutableList<FeatureToggleExt> = ImmutableList.copyOf(entries.toTypedArray())
    }

    private var keybind: IKeybind = KeybindMulti.fromStorageString(defaultHotkey, settings).apply {
        callback = KeyCallbackToggleBooleanConfigWithMessage(this@FeatureToggleExt)
    }
    private var valueBoolean: Boolean = defaultValue
    private val defaultValueBoolean: Boolean = defaultValue
    private var callback: IValueChangeCallback<IConfigBoolean>? = null


    override fun getType(): ConfigType {
        return ConfigType.HOTKEY
    }

    override fun getName(): String {
        return if (this.singlePlayer)
            GuiBase.TXT_GOLD + this.displayName + GuiBase.TXT_RST
        else displayName
    }

    override fun getConfigGuiDisplayName(): String? {
        val name = StringUtils.getTranslatedOrFallback(this.getTranslatedName(), this.getName())
        return if (this.singlePlayer) "${GuiBase.TXT_GOLD}$name${GuiBase.TXT_RST}" else name
    }

    override fun getPrettyName(): String? {
        return StringUtils.getTranslatedOrFallback(
            this.prettyName,
            this.prettyName.ifEmpty {
                StringUtils.splitCamelCase(
                    this.displayName.substring(5)
                )
            }
        )
    }

    override fun getStringValue(): String {
        return this.valueBoolean.toString()
    }

    override fun getDefaultStringValue(): String {
        return this.defaultValueBoolean.toString()
    }

    override fun setValueFromString(value: String?) {

    }

    override fun onValueChanged() {
        this.callback?.onValueChanged(this)
    }

    override fun setValueChangeCallback(p0: IValueChangeCallback<IConfigBoolean>?) {
        this.callback = p0
    }

    override fun getComment(): String? {
        val comment = StringUtils.getTranslatedOrFallback(this.comment, this.comment)
        return if (comment != null && this.singlePlayer)
            "$comment\n${StringUtils.translate("tweakeroo-ext.label.config_comment.single_player_only")}"
        else comment
    }

    override fun getTranslatedName(): String {
        return this.translatedName
    }

    override fun getKeybind(): IKeybind {
        return this.keybind
    }

    override fun getBooleanValue(): Boolean {
        return this.valueBoolean
    }

    override fun getDefaultBooleanValue(): Boolean {
        return this.defaultValueBoolean
    }

    override fun setBooleanValue(p0: Boolean) {
        val oldValue = this.valueBoolean
        this.valueBoolean = p0
        if (oldValue != this.valueBoolean) {
            this.onValueChanged()
        }
    }

    override fun isModified(): Boolean {
        return this.valueBoolean != this.defaultValueBoolean
    }

    override fun isModified(newValue: String?): Boolean {
        return newValue.toBoolean() != this.defaultValueBoolean
    }

    override fun resetToDefault() {
        this.valueBoolean = this.defaultValueBoolean
    }

    override fun getAsJsonElement(): JsonElement {
        return JsonPrimitive(this.valueBoolean)
    }

    override fun setValueFromJsonElement(p0: JsonElement) {
        try {
            if (p0.isJsonPrimitive) {
                this.booleanValue = p0.asBoolean
            } else {
                log.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), p0)
            }
        } catch (e: Throwable) {
            log.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), p0, e)
        }
    }


}