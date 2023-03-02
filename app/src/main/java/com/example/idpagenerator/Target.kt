package com.example.idpagenerator

import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

enum class TargetArea {
    BLANK, HEADONLY, VERTICALSTRIP,
    UP, DOWN, LEFT, RIGHT, TOPLEFT,
    TOPRIGHT
}

enum class TargetActivator {
    NONE, TRAP, STEEL, DOOR, ROPE
}

class Target {
    var nonThreatInFront = false
    var targetArea = TargetArea.BLANK
    var hasActivator = false
    var targetActivator = TargetActivator.NONE

    companion object {
        fun generateTarget(specialTarget: Boolean): Target {
            var target = Target()
            target.nonThreatInFront = nextBoolean()
            if (!target.nonThreatInFront) {
                target.targetArea = TargetArea.values().toList().shuffled().first()
                if(specialTarget && target.targetArea == TargetArea.HEADONLY)
                    target.targetArea = TargetArea.BLANK
            }
            if (nextInt(100) > 90) {
                target.hasActivator = true
                target.targetActivator = TargetActivator.values().toList().shuffled().first()
            }
            return target
        }
    }
}