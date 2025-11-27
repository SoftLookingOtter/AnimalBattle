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

        // UI-referenser
        val tvTitle = findViewById<TextView>(R.id.tv_game_over_title)
        val tvWinner = findViewById<TextView>(R.id.tv_winner)
        val tvFinalScore = findViewById<TextView>(R.id.tv_final_score)
        val buttonPlayAgain = findViewById<Button>(R.id.button_play_again)
        val buttonMenu = findViewById<Button>(R.id.button_return_menu)

        // Hämta aktuella poäng
        val playerScore = ScoreManager.playerScore
        val aiScore = ScoreManager.aiScore

        // Har spelaren vunnit?
        val playerWon = playerScore > aiScore

        tvTitle.text = if (playerWon) "You Won!" else "Game Over"
        tvWinner.text = if (playerWon) "Winner: Player" else "Winner: AI"
        tvFinalScore.text = "Final Score: Player $playerScore vs AI $aiScore"

        // 🔁 Spela igen → ny match direkt
        buttonPlayAgain.setOnClickListener {
            ScoreManager.reset()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 🏠 Tillbaka till menyn
        buttonMenu.setOnClickListener {
            ScoreManager.reset()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity() // stänger ev. gamla activities
        }
    }
}
