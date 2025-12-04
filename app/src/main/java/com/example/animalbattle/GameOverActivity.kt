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

        // Play end jingle when this screen is shown
        MusicManager.playEndJingle(this)

        val tvResult = findViewById<TextView>(R.id.tv_game_over_result)
        val tvSub = findViewById<TextView>(R.id.tv_game_over_sub)
        val playAgainButton = findViewById<Button>(R.id.button_play_again)
        val backToMenuButton = findViewById<Button>(R.id.button_back_to_menu)

        // Check if the player won the whole game
        val playerWon = intent.getBooleanExtra("PLAYER_WON", false)

        // Update text depending on result
        if (playerWon) {
            tvResult.text = "YOU WON! 🏆"
            tvSub.text = "Your animal reigns supreme!"
        } else {
            tvResult.text = "YOU LOST! 😢"
            tvSub.text = "Better luck next battle!"
        }

        // Starting a new game should always reset the score first
        playAgainButton.setOnClickListener {
            ScoreManager.reset()
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        // Returning to the menu should also start fresh
        backToMenuButton.setOnClickListener {
            ScoreManager.reset()
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // Stop the jingle if the player leaves this screen
        MusicManager.stop()
    }
}
