package com.example.idpagenerator

import kotlin.random.Random.Default.nextInt

enum class DrawType {
    STATIC, STEPPING, COVERLEFT, COVERRIGHT, PIVOTING
}

class Draw {
    var drawType = DrawType.STATIC
    var drawHour = 12 //can be 1-12

    fun generateDraw() {
        drawType = DrawType.values().toList().shuffled().first()
        if(drawType == DrawType.PIVOTING || drawType == DrawType.STEPPING) {
            drawHour = nextInt(1, 12)
        }
    }
}