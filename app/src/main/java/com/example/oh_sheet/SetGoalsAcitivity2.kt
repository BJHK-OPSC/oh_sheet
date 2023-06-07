package com.example.oh_sheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SetGoalsAcitivity2 : AppCompatActivity(), View.OnClickListener {

    val textViewGoal: TextView = findViewById(R.id.txtGoalName) as TextView
    val textViewMin: TextView = findViewById(R.id.txtGoalName) as TextView
    val textViewMax: TextView = findViewById(R.id.txtGoalName) as TextView

    //------------------------------------------------------------------------------------------------\\
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals_acitivity2)

        val intent = Intent(this, SetGoals :: class.java)
    }

    //------------------------------------------------------------------------------------------------\\
    override fun onClick(v: View?) {
        val name: String = textViewGoal.toString()
        val min: String = textViewMin.toString()
        val max: String = textViewMax.toString()

        if (textViewGoal.text.isNotEmpty() || textViewMin.text.isNotEmpty() || textViewMax.text.isNotEmpty()) {
            intent.putExtra("name", name)
            intent.putExtra("min", min)
            intent.putExtra("max", max)

            setResult(RESULT_OK, intent)
            finish()
        } else {
            // TextView is empty
        }

    }

}
//------------------------------------------End of File------------------------------------------------------\\