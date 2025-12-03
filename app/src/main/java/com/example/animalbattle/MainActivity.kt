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

        // Start new game from main menu
        startButton.setOnClickListener {
            // Reset scores before a fresh game
            ScoreManager.reset()

            // Fade out menu music, then go to the battle screen
            MusicManager.fadeOut {
                startActivity(Intent(this, GameActivity::class.java))
                // Optional: close main menu so back button doesn’t return here
                // finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Play menu music whenever we return to the main screen
        MusicManager.playMenuMusic(this)
        // eller MusicManager.startMenuMusic(this) om du vill använda aliaset
    }

    override fun onPause() {
        super.onPause()
        // Stop music when leaving the main screen (e.g. app goes to background)
        // Detta kommer oftast ske efter fadeOut ovan, men det är säkert att kalla stop() igen.
        MusicManager.stop()
    }
}
