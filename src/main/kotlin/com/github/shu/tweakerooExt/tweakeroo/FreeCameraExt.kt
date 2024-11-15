package com.github.shu.tweakerooExt.tweakeroo

import fi.dy.masa.tweakeroo.util.CameraEntity
import net.minecraft.entity.Entity
import net.minecraft.util.hit.HitResult

@Suppress("SpellCheckingInspection")
object FreeCameraExt {
    @JvmStatic
    var cameraEntity: CameraEntity? = null

    @JvmStatic
    var crosshairTarget: HitResult? = null

    @JvmStatic
    var targetedEntity: Entity? = null
}