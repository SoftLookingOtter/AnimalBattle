package com.example.animalbattle

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RulesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)

        val backButton = findViewById<Button>(R.id.button_back_rules)
        backButton.setOnClickListener {
            finish()
        }
    }
}

