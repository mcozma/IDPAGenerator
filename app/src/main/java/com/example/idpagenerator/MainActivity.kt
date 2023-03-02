package com.example.idpagenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var listOfTableRows = mutableListOf<TableRow>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun generateNewStage(view: View) {
        val stage = Stage();
        stage.generate();

        //stage type
        var textView: TextView = findViewById(R.id.stageType)
        textView.text =
            stage.stageType.toString() + ", " + stage.totalTargets.toString() + " targets"

        //rounds
        textView = findViewById(R.id.roundsStructure)
        textView.text = stage.totalShots.toString() + ", " + stage.roundsStructure.toString()

        //concealment
        textView = findViewById(R.id.concealmentRequired)
        if (stage.concealmentRequired)
            textView.text = "Yes"
        else
            textView.text = "No"

        //gun condition
        textView = findViewById(R.id.gunCondition)
        var textBuild = ""
        if (stage.gunCondition.loaded) {
            textBuild = "Loaded, "
            if (stage.gunCondition.chamberEmpty)
                textBuild += "chamber empty, "
            else
                textBuild += "round chambered, "
        } else
            textBuild = "Unloaded, "
        textBuild += stage.gunCondition.holstered.toString()
        textView.text = textBuild

        //mag condition
        textView = findViewById(R.id.magsCondition)
        textBuild = ""
        for (load in stage.gunCondition.magLoading) {
            if (load == stage.gunCondition.maxRounds)
                textBuild += "Div cap"
            else
                textBuild += load.toString()
            textBuild += ", "
        }
        if (stage.gunCondition.pickupMags)
            textBuild += "pick-up"
        else
            textBuild += "on shooter"
        textView.text = textBuild

        //start position
        textView = findViewById(R.id.startPosition)
        textView.text =
            stage.startPosition.bodyPosition.toString() + ", hands " + stage.startPosition.handsPosition.toString()

        //draw
        textView = findViewById(R.id.draw)
        textBuild = stage.draw.drawType.toString()
        if (stage.draw.drawType == DrawType.STEPPING || stage.draw.drawType == DrawType.PIVOTING)
            textBuild += ", " + stage.draw.drawHour + " o'clock"
        textView.text = textBuild

        //shots
        textView = findViewById(R.id.shots)
        if(!(stage.shots.extraTargetRule && stage.totalTargets == 1)) {
            textBuild = stage.shots.shotsPerTarget.toString() + " shots"
            if (!stage.shots.shootAnywhere) {
                textBuild += ", " + stage.shots.shootBody.toString() + " to the body, " + stage.shots.shootHead.toString() + " to the head"
            }
        } else {
            textBuild = "No"
        }
        textView.text = textBuild


        //special target
        textView = findViewById(R.id.shotsSpecial)
        if (stage.shots.extraTargetRule) {
            textBuild = stage.shots.extraTargetShots.toString() + " shots"
            if (!stage.shots.extraTargetAnywhere) {
                textBuild += ", " + stage.shots.extraTargetBody.toString() + " to the body, " + stage.shots.extraTargetHead.toString() + " to the head"
            }
        } else {
            textBuild = "No"
        }
        textView.text = textBuild

        //sp
        textView = findViewById(R.id.sp)
        textBuild = "Enter  " + stage.sp.entryDirection.toString() + " o'clock"
        if (stage.sp.targetCount > 0) {
            textBuild += ", " + stage.sp.targetCount.toString() + " targets"
            if(stage.specialTargetPoC == 0)
                textBuild += ", Special Target"
            textBuild += "\n"
            for (target in stage.sp.targets) {
                textBuild += target.targetArea.toString()
                if (target.nonThreatInFront)
                    textBuild += ", non-threat in front"
                if (target.hasActivator)
                    textBuild += ", activated by " + target.targetActivator.toString()
                textBuild += "\n"
            }
        }
        textView.text = textBuild

        //clear reviews PoCs
        var tableLayout: TableLayout = findViewById(R.id.stageTable)
        if(listOfTableRows.isNotEmpty()){
            for(row in listOfTableRows){
                tableLayout.removeView(row)
            }
        }
        listOfTableRows.clear()

        //add all Pocs
        for (poc in stage.pointsOfContact) {
            var rowLabel = TextView(this)
            var index = stage.pointsOfContact.indexOf(poc) + 1
            rowLabel.text = "PoC " + index + ": "

            textBuild = "Enter " + poc.entryDirection.toString() + " o'clock, " + poc.faultLineDifficulty.toString() + " difficulty, "
            textBuild += poc.targetCount.toString() + " targets"
            if(stage.specialTargetPoC == index)
                textBuild += ", Special Target"
            textBuild += "\n"
            for (target in poc.targets) {
                textBuild += target.targetArea.toString()
                if (target.nonThreatInFront)
                    textBuild += ", non-threat in front"
                if (target.hasActivator)
                    textBuild += ", activated by " + target.targetActivator.toString()
                textBuild += "\n"
            }
            var rowValue = TextView(this)
            rowValue.text = textBuild

            var newRow = TableRow(this)
            newRow.addView(rowLabel)
            newRow.addView(rowValue)
            tableLayout.addView(newRow)
            listOfTableRows.add(newRow)
        }
    }
}