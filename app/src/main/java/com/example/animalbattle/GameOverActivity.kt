package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.util.ScoreManager

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val tvTitle = findViewById<TextView>(R.id.tv_game_over_title)
        val tvScore = findViewById<TextView>(R.id.tv_final_score)
        val playAgainButton = findViewById<Button>(R.id.button_play_again)
        val backToMenuButton = findViewById<Button>(R.id.button_back_to_menu)

        tvTitle.text = if (ScoreManager.playerScore >= ScoreManager.WIN_SCORE) {
            "You Win! 🎉"
        } else {
            "Game Over 😭"
        }

        tvScore.text = "Final Score: Player ${ScoreManager.playerScore} – AI ${ScoreManager.aiScore}"

        playAgainButton.setOnClickListener {
            ScoreManager.reset()
            startActivity(Intent(this, GameActivity::class.java))
        }

        backToMenuButton.setOnClickListener {
            ScoreManager.reset()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}

