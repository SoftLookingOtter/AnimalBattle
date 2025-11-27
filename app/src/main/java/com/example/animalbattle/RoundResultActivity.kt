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

        // Hämta UI-element
        val ivPlayer = findViewById<ImageView>(R.id.iv_player_result)
        val ivAi = findViewById<ImageView>(R.id.iv_ai_result)
        val tvPlayerLabel = findViewById<TextView>(R.id.tv_player_card_label)
        val tvAiLabel = findViewById<TextView>(R.id.tv_ai_card_label)
        val tvDescription = findViewById<TextView>(R.id.tv_round_description)
        val tvResult = findViewById<TextView>(R.id.tv_round_result)
        val tvPlayerScore = findViewById<TextView>(R.id.tv_player_score_result)
        val tvAiScore = findViewById<TextView>(R.id.tv_ai_score_result)
        val nextButton = findViewById<Button>(R.id.button_next_round)

        // Plocka ut data som skickades från GameActivity
        val playerName = intent.getStringExtra("playerName") ?: "Player"
        val aiName = intent.getStringExtra("aiName") ?: "AI"
        val playerImageRes = intent.getIntExtra("playerImage", 0)
        val aiImageRes = intent.getIntExtra("aiImage", 0)
        val description = intent.getStringExtra("description") ?: ""
        val resultMessage = intent.getStringExtra("resultMessage") ?: ""

        // Sätt bilder om de finns
        if (playerImageRes != 0) ivPlayer.setImageResource(playerImageRes)
        if (aiImageRes != 0) ivAi.setImageResource(aiImageRes)

        // Sätt texter
        tvPlayerLabel.text = "Your Card: $playerName"
        tvAiLabel.text = "AI Card: $aiName"
        tvDescription.text = description
        tvResult.text = resultMessage

        // Poäng från ScoreManager
        tvPlayerScore.text = "Your Score: ${ScoreManager.playerScore}"
        tvAiScore.text = "AI Score: ${ScoreManager.aiScore}"

        // Knappen för nästa steg
        nextButton.setOnClickListener {
            // Har någon nått vinstgränsen?
            if (ScoreManager.playerScore >= ScoreManager.WIN_SCORE ||
                ScoreManager.aiScore >= ScoreManager.WIN_SCORE
            ) {
                // Gå till GameOverActivity
                val gameOverIntent = Intent(this, GameOverActivity::class.java)
                startActivity(gameOverIntent)
            } else {
                // Ny runda → tillbaka till GameActivity
                val gameIntent = Intent(this, GameActivity::class.java)
                startActivity(gameIntent)
            }
            finish()
        }
    }
}
