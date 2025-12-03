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

        // When the player taps the start button, I reset the scores and begin a new game.
        startButton.setOnClickListener {
            ScoreManager.reset()

            // I fade out the menu music first so the transition feels smoother.
            MusicManager.fadeOut {
                startActivity(Intent(this, GameActivity::class.java))
                // If I ever want to prevent users from returning to this screen with the back button,
                // I can uncomment finish() here.
                // finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // I restart the menu music whenever the player returns to this screen.
        MusicManager.playMenuMusic(this)
    }

    override fun onPause() {
        super.onPause()
        // I stop the menu music when leaving the screen.
        // Most of the time fadeOut already took care of it, but calling stop() again is safe.
        MusicManager.stop()
    }
}
