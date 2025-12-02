package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.util.ScoreManager

class RoundResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_result)

        val ivPlayer = findViewById<ImageView>(R.id.iv_player_result)
        val ivAi = findViewById<ImageView>(R.id.iv_ai_result)
        val tvPlayerLabel = findViewById<TextView>(R.id.tv_player_card_label)
        val tvAiLabel = findViewById<TextView>(R.id.tv_ai_card_label)
        val tvPlayerStrength = findViewById<TextView>(R.id.tv_player_strength_result)
        val tvAiStrength = findViewById<TextView>(R.id.tv_ai_strength_result)
        val tvResult = findViewById<TextView>(R.id.tv_round_result)
        val tvPlayerScore = findViewById<TextView>(R.id.tv_player_score_result)
        val tvAiScore = findViewById<TextView>(R.id.tv_ai_score_result)
        val nextButton = findViewById<Button>(R.id.button_next_round)

        val playerName = intent.getStringExtra("playerName") ?: "Player"
        val aiName = intent.getStringExtra("aiName") ?: "AI"
        val playerImageRes = intent.getIntExtra("playerImage", 0)
        val aiImageRes = intent.getIntExtra("aiImage", 0)
        val resultMessage = intent.getStringExtra("resultMessage") ?: ""
        val playerStrength = intent.getIntExtra("playerStrength", 0)
        val aiStrength = intent.getIntExtra("aiStrength", 0)

        if (playerImageRes != 0) ivPlayer.setImageResource(playerImageRes)
        if (aiImageRes != 0) ivAi.setImageResource(aiImageRes)

        tvPlayerLabel.text = "Your Card: $playerName"
        tvAiLabel.text = "AI Card: $aiName"
        tvPlayerStrength.text = "Strength: $playerStrength"
        tvAiStrength.text = "Strength: $aiStrength"

        // Bara huvudresultatet visas
        tvResult.text = resultMessage

        tvPlayerScore.text = "Your Score: ${ScoreManager.playerScore}"
        tvAiScore.text = "AI Score: ${ScoreManager.aiScore}"

        nextButton.setOnClickListener {
            if (ScoreManager.playerScore >= ScoreManager.WIN_SCORE ||
                ScoreManager.aiScore >= ScoreManager.WIN_SCORE
            ) {
                startActivity(Intent(this, GameOverActivity::class.java))
            } else {
                startActivity(Intent(this, GameActivity::class.java))
            }
            finish()
        }
    }
}

