package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.util.MusicManager
import com.example.animalbattle.util.ScoreManager

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // I play the ending jingle as soon as this screen shows up.
        MusicManager.playEndJingle(this)

        val tvResult = findViewById<TextView>(R.id.tv_game_over_result)
        val playAgainButton = findViewById<Button>(R.id.button_play_again)
        val backToMenuButton = findViewById<Button>(R.id.button_back_to_menu)
        val bgImage = findViewById<ImageView>(R.id.iv_game_over_bg)

        // I check if the player actually won the whole game.
        val playerWon = intent.getBooleanExtra("PLAYER_WON", false)

        // I change both the text and the background depending on the result.
        if (playerWon) {
            tvResult.text = "YOU WON! 🏆"
            bgImage.setImageResource(R.drawable.bg_win)
        } else {
            tvResult.text = "YOU LOST! 😢"
            bgImage.setImageResource(R.drawable.bg_lose)
        }

        // Starting a new game should always reset the score first.
        playAgainButton.setOnClickListener {
            ScoreManager.reset()
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        // Returning to the menu should also start fresh.
        backToMenuButton.setOnClickListener {
            ScoreManager.reset()
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // If the player leaves this screen, I immediately stop the jingle.
        MusicManager.stop()
    }
}
