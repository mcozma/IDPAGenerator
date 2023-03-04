package com.example.idpagenerator

import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

enum class StageType {
    STANDARD, SCENARIO
}

enum class RoundsStructure {
    LIMITED, UNLIMITED
}

class Stage {
    var stageType = StageType.SCENARIO
    var roundsStructure = RoundsStructure.UNLIMITED
    var concealmentRequired = true
    var gunCondition = GunCondition()
    var startPosition = StartPosition()
    var draw = Draw()
    var shots = Shots()
    var sp = PointOfContact()
    var pocCount = 0 //does not include sp, that one is first in pointOfContacts
    var pointsOfContact = mutableListOf <PointOfContact>()
    var totalTargets = 0
    var totalShots = 0
    var specialTargetPoC = -1

    fun generate() {
        //stage type
        stageType = StageType.values().toList().shuffled().first()
        //limited/concealed
        if(stageType == StageType.STANDARD) {
            roundsStructure = RoundsStructure.values().toList().shuffled().first()
            concealmentRequired = nextBoolean()
        }
        //start position
        startPosition.generateStartPosition(stageType)
        //draw
        draw.generateDraw()
        //shots
        shots.generateShots()
        val allInTheOpen = nextInt(1, 10) == 1
        if(allInTheOpen){
            sp = PointOfContact.generateSp(shots, true, true)
            if (shots.extraTargetRule) {
                specialTargetPoC = 0
            }
        } else {
            if (shots.extraTargetRule) {
                if(nextInt(1, 5) == 1)
                    specialTargetPoC = 0
            }
            sp = PointOfContact.generateSp(shots, specialTargetPoC == 0, false)
            while (shots.availableTargetsCount > 0) {
                pointsOfContact.add(PointOfContact.generatePoc(shots))
                pocCount++
                if (shots.extraTargetRule && specialTargetPoC == -1) {
                    if (nextInt(1, 6) > 6 - pocCount)
                        specialTargetPoC = pocCount
                }
            }
            //if still no special target, assign it to the last poc
            if (shots.extraTargetRule && specialTargetPoC == -1)
                if(pocCount > 0)
                    specialTargetPoC = pocCount
                else
                    specialTargetPoC = 0
        }

        //count total targets and shots
        totalTargets += sp.targetCount
        for(poc in pointsOfContact)
            totalTargets += poc.targetCount
        totalShots = shots.shotsPerTarget * totalTargets
        if(shots.extraTargetRule)
            totalShots += shots.extraTargetShotsPerTarget - shots.shotsPerTarget

        //gun condition
        gunCondition.generateGunCondition(stageType,roundsStructure, totalShots)
    }
}