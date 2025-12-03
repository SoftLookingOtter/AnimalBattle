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

        // 🔊 Spela kort end-jingle när Game Over-skärmen visas
        MusicManager.playEndJingle(this)

        // Views
        val tvResult = findViewById<TextView>(R.id.tv_game_over_result)
        val playAgainButton = findViewById<Button>(R.id.button_play_again)
        val backToMenuButton = findViewById<Button>(R.id.button_back_to_menu)

        // Hämta om spelaren vann eller förlorade
        val playerWon = intent.getBooleanExtra("PLAYER_WON", false)

        // Pokal vid vinst, ledsen gubbe vid förlust
        tvResult.text = if (playerWon) {
            "YOU WON! 🏆"
        } else {
            "YOU LOST! 😢"
        }

        // 🔁 Spela igen: nollställ poäng och starta nytt game
        playAgainButton.setOnClickListener {
            ScoreManager.reset()

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 🏡 Till meny: nollställ poäng och stäng denna skärm
        backToMenuButton.setOnClickListener {
            ScoreManager.reset()
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // ⏹ Stoppa end-jingle om användaren lämnar denna skärm
        MusicManager.stop()
    }
}
