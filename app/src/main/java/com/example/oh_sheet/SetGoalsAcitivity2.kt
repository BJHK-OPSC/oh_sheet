package com.example.oh_sheet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class SetGoalsAcitivity2 : AppCompatActivity(), View.OnClickListener {

    private lateinit var textViewGoal: TextView
    private lateinit var textViewMin: TextView
    private lateinit var textViewMax: TextView
    //------------------------------------------------------------------------------------------------\\
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals_acitivity2)

        textViewGoal = findViewById(R.id.txtGoalName) as TextView
        textViewMin = findViewById(R.id.txtGoalName) as TextView
        textViewMax = findViewById(R.id.txtGoalName) as TextView

        val intent = Intent(this, SetGoalsActivity :: class.java)

        val backButton: ImageButton = findViewById(R.id.bbGoals)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

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