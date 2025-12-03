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

        // Grabbing all the views I need for this screen.
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

        // I read all the data I passed from GameActivity so I can show what happened this round.
        val playerName = intent.getStringExtra("playerName") ?: "Player"
        val aiName = intent.getStringExtra("aiName") ?: "AI"
        val playerImageRes = intent.getIntExtra("playerImage", 0)
        val aiImageRes = intent.getIntExtra("aiImage", 0)
        val resultMessage = intent.getStringExtra("resultMessage") ?: ""
        val playerStrength = intent.getIntExtra("playerStrength", 0)
        val aiStrength = intent.getIntExtra("aiStrength", 0)
        val playerAction = intent.getStringExtra("playerAction") ?: ""
        val aiAction = intent.getStringExtra("aiAction") ?: ""

        // Showing the images of the cards used this round.
        if (playerImageRes != 0) ivPlayer.setImageResource(playerImageRes)
        if (aiImageRes != 0) ivAi.setImageResource(aiImageRes)

        // Filling in all the text fields so the player can review the round.
        tvPlayerName.text = "Your Card: $playerName"
        tvAiName.text = "AI Card: $aiName"

        tvPlayerAction.text = playerAction
        tvAiAction.text = aiAction

        tvPlayerStrength.text = "Strength: $playerStrength"
        tvAiStrength.text = "Strength: $aiStrength"

        tvResult.text = resultMessage

        // I also show the overall match score here.
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"

        nextButton.setOnClickListener {
            // I check here whether the match is finished or not.
            if (ScoreManager.playerScore >= ScoreManager.WIN_SCORE ||
                ScoreManager.aiScore >= ScoreManager.WIN_SCORE
            ) {
                val playerWonGame = ScoreManager.playerScore >= ScoreManager.WIN_SCORE

                val intent = Intent(this, GameOverActivity::class.java).apply {
                    // I only pass the win/loss boolean and let GameOverActivity decide the message.
                    putExtra("PLAYER_WON", playerWonGame)
                }

                // I fade out the battle music before switching to the game-over screen.
                MusicManager.fadeOut {
                    startActivity(intent)
                    finish()
                }
            } else {
                // If nobody has won yet, I simply go back to the main battle screen.
                val backToGame = Intent(this, GameActivity::class.java)
                startActivity(backToGame)
                finish()
                // I keep the battle music running here.
            }
        }
    }
}
