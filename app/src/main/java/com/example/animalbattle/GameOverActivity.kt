package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.util.MusicManager
import com.example.animalbattle.util.ScoreManager

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // I play a short ending jingle as soon as this screen shows up.
        MusicManager.playEndJingle(this)

        val tvResult = findViewById<TextView>(R.id.tv_game_over_result)
        val playAgainButton = findViewById<Button>(R.id.button_play_again)
        val backToMenuButton = findViewById<Button>(R.id.button_back_to_menu)

        // I check whether the player won or lost the whole game.
        val playerWon = intent.getBooleanExtra("PLAYER_WON", false)

        // Simple win/lose message with a small emoji to make it feel nicer.
        tvResult.text = if (playerWon) {
            "YOU WON! 🏆"
        } else {
            "YOU LOST! 😢"
        }

        // When the player wants to play again, I reset the score and start a new game.
        playAgainButton.setOnClickListener {
            ScoreManager.reset()

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Going back to the menu should also reset everything so the next run is fresh.
        backToMenuButton.setOnClickListener {
            ScoreManager.reset()
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // If the user leaves this screen, I stop the ending jingle immediately.
        MusicManager.stop()
    }
}
