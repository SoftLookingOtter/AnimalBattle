// com/example/animalbattle/MainActivity.kt
package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.util.ScoreManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.button_start_game)

        startButton.setOnClickListener {
            ScoreManager.reset()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
