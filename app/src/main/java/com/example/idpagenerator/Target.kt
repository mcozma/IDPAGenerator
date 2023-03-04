package com.example.idpagenerator

import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

enum class TargetArea {
    BLANK, HEADONLY, VERTICALSTRIP,
    UP, DOWN, LEFT, RIGHT, TOPLEFT,
    TOPRIGHT
}

enum class TargetActivator {
    TRAP, STEEL, DOOR, ROPE
}

class Target {
    var nonThreatInFront = false
    var targetArea = TargetArea.BLANK
    var hasActivator = false
    var targetActivator = TargetActivator.STEEL

    companion object {
        fun generateTarget(): Target {
            val target = Target()
            target.nonThreatInFront = nextBoolean()
            if (!target.nonThreatInFront) {
                target.targetArea = TargetArea.values().toList().shuffled().first()
            }
            if (nextInt(100) > 90) {
                target.hasActivator = true
                target.targetActivator = TargetActivator.values().toList().shuffled().first()
            }
            return target
        }
    }
}