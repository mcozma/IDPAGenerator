package com.example.idpagenerator

import kotlin.random.Random.Default.nextInt

enum class FaultLineDifficulty {
    EASY, MED, HARD
}

class PointOfContact {
    var entryDirection = 12 //round the clock, can be 1 to 12
    var faultLineDifficulty = FaultLineDifficulty.MED
    var targetCount = 0
    var targets = mutableListOf<Target>()

    companion object {
        fun generateSp(shots: Shots, specialTarget: Boolean, allInTheOpen: Boolean): PointOfContact {
            val poc = PointOfContact()
            poc.faultLineDifficulty = FaultLineDifficulty.values().toList().shuffled().first()

            if(allInTheOpen) {
                poc.targetCount = shots.availableTargetsCount
            } else {
                //max 6 shots in the open
                var availableShotsInTheOpen = 6
                if (specialTarget) {
                    poc.targetCount = 1
                    availableShotsInTheOpen -= shots.extraTargetShotsPerTarget
                }
                var maxRemainingTargets = availableShotsInTheOpen / shots.shotsPerTarget
                if (maxRemainingTargets > shots.availableTargetsCount)
                    maxRemainingTargets = shots.availableTargetsCount
                poc.targetCount += (0..maxRemainingTargets).random()
                shots.availableTargetsCount -= poc.targetCount
            }
            for(i in 1..poc.targetCount) {
                poc.targets.add(Target.generateTarget())
            }
            return poc
        }
        fun generatePoc(shots: Shots): PointOfContact {
            val poc = PointOfContact()
            poc.entryDirection = nextInt(1, 12)
            poc.faultLineDifficulty = FaultLineDifficulty.values().toList().shuffled().first()

            if(shots.availableTargetsCount > 1)
                poc.targetCount = nextInt(1, shots.availableTargetsCount)
            else
                poc.targetCount = 1
            shots.availableTargetsCount -= poc.targetCount

            for(i in 1..poc.targetCount) {
                poc.targets.add(Target.generateTarget())
            }
            return poc
        }
    }
}