package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.util.MusicManager
import com.example.animalbattle.util.ScoreManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.button_start_game)
        val titleText = findViewById<TextView>(R.id.text_title)
        val animalsImage = findViewById<ImageView>(R.id.iv_animals_group)

        startButton.setOnClickListener {
            ScoreManager.reset()

            MusicManager.fadeOut {
                startActivity(Intent(this, GameActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MusicManager.startMenuMusic(this)
    }

    override fun onPause() {
        super.onPause()
        MusicManager.stop()
    }
}
