@file: Suppress("SpellCheckingInspection")

package com.github.shu.tweakerooExt.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import kotlin.math.max
import kotlin.math.sqrt

object RayCastUtils {
    @JvmStatic
    fun rayCast(mc: MinecraftClient, cameraEntity: Entity) = findCrosshairTarget(
        cameraEntity,
        mc.player?.blockInteractionRange ?: 4.5,
        mc.player?.entityInteractionRange ?: 3.0,
        mc.renderTickCounter.getTickDelta(false)
    )

    @JvmStatic
    fun findCrosshairTarget(
        camera: Entity,
        blockInteractionRange: Double,
        entityInteraction: Double,
        tickDelta: Float
    ): HitResult {
        var d = max(blockInteractionRange, entityInteraction)
        var e = MathHelper.square(d)
        val vec3d = camera.getCameraPosVec(tickDelta)
        val hitResult = camera.raycast(d, tickDelta, false)
        val f = hitResult.pos.squaredDistanceTo(vec3d)
        if (hitResult.type != HitResult.Type.MISS) {
            e = f
            d = sqrt(f)
        }

        val vec3d2 = camera.getRotationVec(tickDelta)
        val vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d)
        val g = 1.0f
        val box = camera.boundingBox.stretch(vec3d2.multiply(d)).expand(1.0, 1.0, 1.0)
        val entityHitResult = ProjectileUtil.raycast(camera, vec3d, vec3d3, box, { !it.isSpectator && it.canHit() }, e)
        return if (entityHitResult != null && entityHitResult.pos.squaredDistanceTo(vec3d) < f) {
            ensureTargetInRange(entityHitResult, vec3d, entityInteraction)
        } else {
            ensureTargetInRange(hitResult, vec3d, blockInteractionRange)
        }
    }

    private fun ensureTargetInRange(hitResult: HitResult, cameraPos: Vec3d, interactionRange: Double): HitResult {
        val vec3d = hitResult.pos
        if (!vec3d.isInRange(cameraPos, interactionRange)) {
            val vec3d2 = hitResult.pos
            val direction = Direction.getFacing(vec3d2.x - cameraPos.x, vec3d2.y - cameraPos.y, vec3d2.z - cameraPos.z)
            return BlockHitResult.createMissed(vec3d2, direction, BlockPos.ofFloored(vec3d2))
        }
        return hitResult
    }
}