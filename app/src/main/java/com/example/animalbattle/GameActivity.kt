package com.example.animalbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.data.AnimalCard
import com.example.animalbattle.data.Deck
import com.example.animalbattle.logic.AiLogic
import com.example.animalbattle.logic.GameLogic
import com.example.animalbattle.util.MusicManager
import com.example.animalbattle.util.ScoreManager

class GameActivity : AppCompatActivity() {

    private val deck = Deck()
    private lateinit var playerCard: AnimalCard
    private lateinit var aiCard: AnimalCard

    private lateinit var playerImage: ImageView
    private lateinit var playerName: TextView
    private lateinit var playerStrength: TextView
    private lateinit var playerPersonality: TextView
    private lateinit var scoreText: TextView
    private lateinit var attackButton: Button
    private lateinit var defendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // 🎵 Battle music
        MusicManager.startBattleMusic(this)

        playerImage = findViewById(R.id.iv_player_image)
        playerName = findViewById(R.id.tv_player_name)
        playerStrength = findViewById(R.id.tv_player_strength)
        playerPersonality = findViewById(R.id.tv_player_personality)
        scoreText = findViewById(R.id.tv_score)
        attackButton = findViewById(R.id.button_attack)
        defendButton = findViewById(R.id.button_defend)

        startNewRound()

        attackButton.setOnClickListener { playRound("ATTACK") }
        defendButton.setOnClickListener { playRound("DEFEND") }
    }

    private fun startNewRound() {
        playerCard = deck.drawRandomCard()
        aiCard = deck.drawRandomCard()

        playerImage.setImageResource(playerCard.imageRes)
        playerName.text = playerCard.name
        playerStrength.text = "Strength: ${playerCard.strength}"

        val personalityPretty = playerCard.personality.name
            .lowercase()
            .replaceFirstChar { it.uppercase() }

        playerPersonality.text = "Personality: $personalityPretty"

        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"
    }

    private fun playRound(playerAction: String) {
        // AI väljer sin action
        val aiAction = AiLogic.chooseAction(aiCard.personality)

        // Fina texter med emojis till RoundResultActivity
        val playerActionPretty =
            if (playerAction == "ATTACK") "ATTACK ⚔️" else "DEFEND 🛡️"

        val aiActionPretty =
            if (aiAction == "ATTACK") "ATTACK ⚔️" else "DEFEND 🛡️"

        // Räkna ut rundan
        val diff = GameLogic.calculateRound(
            playerCard,
            aiCard,
            playerAction,
            aiAction
        )

        val resultMessage = when {
            diff > 0 -> {
                ScoreManager.playerScore++
                "You won the round! 🎉"
            }
            diff < 0 -> {
                ScoreManager.aiScore++
                "You lost the round! 😭"
            }
            else -> "It's a tie! 😅"
        }

        // Uppdatera score-texten i battleskärmen
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"

        // Skicka ALL info till RoundResultActivity
        val intent = Intent(this, RoundResultActivity::class.java).apply {
            putExtra("playerName", playerCard.name)
            putExtra("playerImage", playerCard.imageRes)
            putExtra("aiName", aiCard.name)
            putExtra("aiImage", aiCard.imageRes)
            putExtra("resultMessage", resultMessage)
            putExtra("playerStrength", playerCard.strength)
            putExtra("aiStrength", aiCard.strength)

            // 👇 NYTT: vad som hände den här rundan
            putExtra("playerAction", playerActionPretty)
            putExtra("aiAction", aiActionPretty)
        }

        startActivity(intent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        MusicManager.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicManager.release()
    }
}
