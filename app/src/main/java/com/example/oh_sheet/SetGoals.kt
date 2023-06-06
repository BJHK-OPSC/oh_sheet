package com.example.oh_sheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SetGoals : AppCompatActivity(), View.OnClickListener {

    var textView1 = findViewById<TextView>(R.id.txtGoalName)
    var textView2 = findViewById<TextView>(R.id.txtMinGoal)
    var textView3 = findViewById<TextView>(R.id.txtMaxGoal)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)
    }

    override fun onClick(view: View) {
        val intent = Intent(this, SetGoalsAcitivity2 :: class.java)
        startActivityForResult(intent, 1)
    }

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