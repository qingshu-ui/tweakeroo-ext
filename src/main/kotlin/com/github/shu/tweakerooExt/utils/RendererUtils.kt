package com.github.shu.tweakerooExt.utils

import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import net.minecraft.util.shape.VoxelShape

object RendererUtils {

    @JvmStatic
    fun drawCuboidShapeOutline(
        matrices: MatrixStack,
        vertexConsumer: VertexConsumer,
        shape: VoxelShape,
        x: Double,
        y: Double,
        z: Double,
        r: Float,
        g: Float,
        b: Float,
        a: Float
    ) {
        val entry = matrices.peek()
        shape.forEachEdge { minX, minY, minZ, maxX, maxY, maxZ ->
            var k = (maxX - minX).toFloat()
            var l = (maxY - minY).toFloat()
            var m = (maxZ - minZ).toFloat()
            val n = MathHelper.sqrt(k * k + l * l + m * m)
            k /= n; l /= n; m /= n
            vertexConsumer
                .vertex(entry, (minX + x).toFloat(), (minY + y).toFloat(), (minZ + z).toFloat())
                .color(r, g, b, a)
                .normal(entry, k, l, m)
            vertexConsumer
                .vertex(entry, (maxX + x).toFloat(), (maxY + y).toFloat(), (maxZ + z).toFloat())
                .color(r, g, b, a)
                .normal(entry, k, l, m)
        }
    }
}