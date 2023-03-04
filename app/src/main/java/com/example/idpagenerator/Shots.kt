package com.example.idpagenerator

import kotlin.random.Random.Default.nextInt

class Shots {
    val minShotsPerTarget = 2
    val maxShotsPerTarget = 6
    var shotsPerTarget = minShotsPerTarget
    var shootAnywhere = true
    var shootBody = 0
    var shootHead = 0
    var extraTargetRule = false
    var extraTargetAnywhere = true
    var extraTargetShotsPerTarget = 0
    var extraTargetBody = 0
    var extraTargetHead = 0
    var availableTargetsCount = 0

    fun generateShots() {
        var shotsBudget = nextInt(4, 18)
        //count
        val rand = nextInt(100)
        if(rand > 20) {
            shotsPerTarget = 2
        }
        else if(rand > 10) {
            shotsPerTarget = 3
        }
        else if(rand > 5) {
            shotsPerTarget = 4
        }
        else if(rand > 2 && shotsBudget >= 5) {
            shotsPerTarget = 5
        }
        else if(shotsBudget >= 6){
            shotsPerTarget = 6
        }
        //area
        if(nextInt(100) > 60) {
            shootAnywhere = false
            shootHead = shotsPerTarget / 2
            shootBody = shootHead
            if(shotsPerTarget % 2 != 0) {
                shootBody++
            }
        }
        //different rule per 1 target
        if(nextInt(100) > 60) {
            extraTargetRule = true
            do {
                extraTargetShotsPerTarget = nextInt(minShotsPerTarget, maxShotsPerTarget)
            }while (extraTargetShotsPerTarget == shotsPerTarget) //careful, potentially infinite loop
            if(nextInt(100) > 60) {
                extraTargetAnywhere = false
                extraTargetHead = extraTargetShotsPerTarget / 2
                extraTargetBody = extraTargetHead
                if(extraTargetShotsPerTarget % 2 != 0) {
                    extraTargetBody++
                }
            }
        }
        //calculate target count
        if(extraTargetRule) {
            shotsBudget -= extraTargetShotsPerTarget
            availableTargetsCount++
        }
        availableTargetsCount += shotsBudget / shotsPerTarget
    }
}