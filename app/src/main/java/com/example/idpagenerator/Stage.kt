package com.example.idpagenerator

import android.graphics.Point
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
        //poc count
        generatePocCount()
        if(shots.extraTargetRule){
            specialTargetPoC = (0..pocCount).random()
        }
        sp = PointOfContact.generateSp(shots, specialTargetPoC == 0, pocCount == 0)
        for(i in 1..pocCount) {
            pointsOfContact.add(PointOfContact.generatePoc(specialTargetPoC == i))
        }
        totalTargets += sp.targetCount
        for(poc in pointsOfContact)
            totalTargets += poc.targetCount
        totalShots = shots.shotsPerTarget * totalTargets
        if(shots.extraTargetRule)
            totalShots += shots.extraTargetShots - shots.shotsPerTarget

        //gun condition
        gunCondition.generateGunCondition(stageType, totalShots)
    }

    private fun generatePocCount() {
        if(nextInt(100) > 10) pocCount++
        if(pocCount == 1 && nextInt(100) > 30) pocCount++;
        if(pocCount == 2 && nextInt(100) > 70) pocCount++;
        if(pocCount == 3 && nextInt(100) > 90) pocCount++;
    }
}