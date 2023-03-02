package com.example.idpagenerator

enum class BodyPosition {
    STANDING, SITTING
}

enum class HandsPosition {
    RELAXED, SURRENDER, ONDESIGNATEDAREA
}

class StartPosition {
    var bodyPosition = BodyPosition.STANDING
    var handsPosition = HandsPosition.RELAXED

    fun generateStartPosition(stageType: StageType){
        if(stageType == StageType.STANDARD) {
            bodyPosition = BodyPosition.values().toList().shuffled().first()
        }
        handsPosition = HandsPosition.values().toList().shuffled().first()
    }
}