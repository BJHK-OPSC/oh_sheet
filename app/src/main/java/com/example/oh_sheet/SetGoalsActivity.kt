package com.example.oh_sheet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Goals(
    val name: String?,
    val min: String?,
    val max: String?
)

val goalsEntries: ArrayList<Goals> = ArrayList()
class SetGoalsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textViewGoalName: TextView
    private lateinit var textViewMinGoal: TextView
    private lateinit var textViewMaxGoal: TextView

    //------------------------------------------------------------------------------------------------\\

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)

        //finding ids for the textviews
        textViewGoalName = findViewById<TextView>(R.id.txtGoalName)
        textViewMinGoal = findViewById<TextView>(R.id.txtMinGoal)
        textViewMaxGoal = findViewById<TextView>(R.id.txtMaxGoal)

        val setGoalsButton: Button = findViewById(R.id.buttonNewGoal)
        setGoalsButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //------------------------------------------------------------------------------------------------\\
    //click event for the save timerBtn
    override fun onClick(view: View) {
        val intent = Intent(this, SetGoalsAcitivity2 :: class.java)
        startActivityForResult(intent, 1)
    }

    //------------------------------------------------------------------------------------------------\\
    //this method will run when the setGoals2 closes
    //setGoals2 returns min, max, name using intents
    //@Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val str1 = data?.getStringExtra("name")
                val str2 = data?.getStringExtra("min")
                val str3 = data?.getStringExtra("max")

                textViewGoalName.setText(str1)
                textViewMinGoal.setText(str2)
                textViewMaxGoal.setText(str3)

                val goalEntry = Goals(str1, str2, str3)

                goalsEntries.clear()
                goalsEntries.add(goalEntry)
            }
        }
    }
}
//------------------------------------------End of File------------------------------------------------------\\