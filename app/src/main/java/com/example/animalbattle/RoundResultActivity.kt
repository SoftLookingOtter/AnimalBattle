package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.util.MusicManager
import com.example.animalbattle.util.ScoreManager

class RoundResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_result)

        // Views
        val ivPlayer = findViewById<ImageView>(R.id.iv_player_result)
        val ivAi = findViewById<ImageView>(R.id.iv_ai_result)
        val tvPlayerName = findViewById<TextView>(R.id.tv_player_name_result)
        val tvAiName = findViewById<TextView>(R.id.tv_ai_name_result)
        val tvPlayerAction = findViewById<TextView>(R.id.tv_player_action_result)
        val tvAiAction = findViewById<TextView>(R.id.tv_ai_action_result)
        val tvPlayerStrength = findViewById<TextView>(R.id.tv_player_strength_result)
        val tvAiStrength = findViewById<TextView>(R.id.tv_ai_strength_result)
        val tvResult = findViewById<TextView>(R.id.tv_round_result)
        val scoreText = findViewById<TextView>(R.id.tv_score_result)
        val nextButton = findViewById<Button>(R.id.button_next_round)

        // Data from intent
        val playerName = intent.getStringExtra("playerName") ?: "Player"
        val aiName = intent.getStringExtra("aiName") ?: "AI"
        val playerImageRes = intent.getIntExtra("playerImage", 0)
        val aiImageRes = intent.getIntExtra("aiImage", 0)
        val resultMessage = intent.getStringExtra("resultMessage") ?: ""
        val playerStrength = intent.getIntExtra("playerStrength", 0)
        val aiStrength = intent.getIntExtra("aiStrength", 0)
        val playerAction = intent.getStringExtra("playerAction") ?: ""
        val aiAction = intent.getStringExtra("aiAction") ?: ""

        // Set images on the result cards
        if (playerImageRes != 0) ivPlayer.setImageResource(playerImageRes)
        if (aiImageRes != 0) ivAi.setImageResource(aiImageRes)

        // Set text content
        tvPlayerName.text = "Your Card: $playerName"
        tvAiName.text = "AI Card: $aiName"

        tvPlayerAction.text = playerAction
        tvAiAction.text = aiAction

        tvPlayerStrength.text = "Strength: $playerStrength"
        tvAiStrength.text = "Strength: $aiStrength"

        // Round result message
        tvResult.text = resultMessage

        // Total score from ScoreManager
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"

        nextButton.setOnClickListener {
            // Check if someone has won the entire game
            if (ScoreManager.playerScore >= ScoreManager.WIN_SCORE ||
                ScoreManager.aiScore >= ScoreManager.WIN_SCORE
            ) {
                val playerWonGame = ScoreManager.playerScore >= ScoreManager.WIN_SCORE

                val intent = Intent(this, GameOverActivity::class.java).apply {
                    // 👇 MATCHAR GameOverActivity
                    putExtra("PLAYER_WON", playerWonGame)
                }

                // Fadea ut battle-musiken innan vi går till Game Over
                MusicManager.fadeOut {
                    startActivity(intent)
                    finish()
                }
            } else {
                // Ingen har nått WIN_SCORE – fortsätt spelet
                val backToGame = Intent(this, GameActivity::class.java)
                startActivity(backToGame)
                finish()
                // Ingen fadeOut här, battle_music fortsätter.
            }
        }
    }
}
