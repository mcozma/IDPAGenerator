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
        fun generateSp(shots: Shots, specialTarget: Boolean, onlySp: Boolean): PointOfContact {
            var poc = PointOfContact()
            poc.faultLineDifficulty = FaultLineDifficulty.values().toList().shuffled().first()

            //max 6 shots in the open
            var availableShots = 6;
            if(specialTarget){
                poc.targetCount = 1;
                availableShots -= shots.extraTargetShots
            }
            var maxRemainingTargets = availableShots / shots.shotsPerTarget
            if(onlySp && !specialTarget)
                poc.targetCount += (1..maxRemainingTargets).random()
            else
                poc.targetCount += (0..maxRemainingTargets).random()

            for(i in 1..poc.targetCount) {
                poc.targets.add(Target.generateTarget(specialTarget && i == 1))
            }
            return poc
        }
        fun generatePoc(specialTarget: Boolean): PointOfContact {
            var poc = PointOfContact()
            poc.entryDirection = nextInt(1, 12)
            poc.faultLineDifficulty = FaultLineDifficulty.values().toList().shuffled().first()

            poc.targetCount = nextInt(1, 3)

            for(i in 1..poc.targetCount) {
                poc.targets.add(Target.generateTarget(specialTarget && i == 1))
            }
            return poc
        }
    }
}