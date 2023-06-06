package com.example.oh_sheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SetGoals : AppCompatActivity(), View.OnClickListener {

    //finding ids for the textviews
    var textView1 = findViewById<TextView>(R.id.txtGoalName)
    var textView2 = findViewById<TextView>(R.id.txtMinGoal)
    var textView3 = findViewById<TextView>(R.id.txtMaxGoal)

    //------------------------------------------------------------------------------------------------\\
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)
    }

    //------------------------------------------------------------------------------------------------\\
    //click event for the save button
    override fun onClick(view: View) {
        val intent = Intent(this, SetGoalsAcitivity2 :: class.java)
        startActivityForResult(intent, 1)
    }

    //------------------------------------------------------------------------------------------------\\
    //this method will run when the setGoals2 closes
    //setGoals2 returns min, max, name using intents
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val str1 = data.getStringExtra("name")
                val str2 = data.getStringExtra("min")
                val str3 = data.getStringExtra("max")

                textView1.setText(str1)
                textView2.setText(str2)
                textView3.setText(str3)
            }
        }
    }
}
//------------------------------------------End of File------------------------------------------------------\\