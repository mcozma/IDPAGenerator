package com.example.idpagenerator

import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

enum class GunPosition {
    HOLSTER, LOWREADY, TABLE
}

class GunCondition() {
    val maxRounds = 21;
    var chamberEmpty = false
    var loaded = true
    var holstered = GunPosition.HOLSTER
    var pickupMags = false;
    var magLoading = mutableListOf(maxRounds, maxRounds, maxRounds)

    fun generateGunCondition(stageType: StageType, totalShots: Int) {
        //loaded condition
        if(nextInt(100) > 80) {
            loaded = false;
            pickupMags = nextBoolean()
        } else {
            chamberEmpty = nextBoolean()
        }
        //position
        if(nextInt(100) > 50) {
            holstered = GunPosition.values().toList().shuffled().first()
        }
        //mags
        if(stageType == StageType.STANDARD) {
            var shotsPerMag = totalShots / 3
            for(i in 0..2) {
                magLoading[i] = shotsPerMag
            }
            magLoading[2] += totalShots % 3
        }
        else if (nextBoolean()) {
            magLoading[0] = nextInt(2, 10);
        }

    }
}