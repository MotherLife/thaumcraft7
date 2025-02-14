package me.alegian.thavma.impl.client.util

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f

fun PoseStack.translate(offset: Vec3) {
    translate(offset.x, offset.y, offset.z)
}

fun PoseStack.transformOrigin() = last().pose().transformPosition(Vector3f())