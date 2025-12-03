package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.animalbattle.util.MusicManager
import com.example.animalbattle.util.ScoreManager

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val root = findViewById<ConstraintLayout>(R.id.game_over_root)
        val tvResult = findViewById<TextView>(R.id.tv_game_over_result)
        val backButton = findViewById<Button>(R.id.button_back_to_menu)
        val playAgainButton = findViewById<Button>(R.id.button_play_again)

        // Info från RoundResultActivity: har spelaren vunnit?
        val playerWon = intent.getBooleanExtra("playerWonGame", false)

        // 🎉 / 😢 text – alltid kort variant
        tvResult.text = if (playerWon) {
            "YOU WON! 🏆"
        } else {
            "YOU LOST 😢"
        }

        // 🔁 Play Again vs Try Again
        playAgainButton.text = if (playerWon) {
            "🔁  Play Again"
        } else {
            "🔁  Try Again"
        }

        // Olika bakgrund för vinst/förlust
        if (playerWon) {
            root.setBackgroundResource(R.drawable.bg_game_over_win)
        } else {
            root.setBackgroundResource(R.drawable.bg_game_over_lose)
        }

        // 🏡 Tillbaka till menyn
        backButton.setOnClickListener {
            ScoreManager.reset()
            finish()
        }

        // 🔁 Ny omgång
        playAgainButton.setOnClickListener {
            ScoreManager.reset()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        MusicManager.playGameOverJingle(this)
    }

    override fun onPause() {
        super.onPause()
        MusicManager.stop()
    }
}
