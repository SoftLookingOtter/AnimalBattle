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

        // Start battle music and keep it playing across game/result screens
        // (MusicManager knows internally which track to use.)
        MusicManager.playBattleMusic(this)

        // View references
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
        // Draw cards for player and AI
        playerCard = deck.drawRandomCard()
        aiCard = deck.drawRandomCard()

        // Update player card UI
        playerImage.setImageResource(playerCard.imageRes)
        playerName.text = playerCard.name
        playerStrength.text = "Strength: ${playerCard.strength}"
        playerPersonality.text = "Personality: ${
            playerCard.personality.name.lowercase().replaceFirstChar { it.uppercase() }
        }"

        // Update score UI
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"
    }

    private fun playRound(playerAction: String) {
        // Let AI choose its action
        val aiAction = AiLogic.chooseAction(aiCard.personality)

        // Calculate round result
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
            else -> {
                "It's a tie! 😅"
            }
        }

        // Update score text on this screen
        scoreText.text =
            "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"

        // Pretty action text with emojis for result screen
        val playerActionPretty =
            if (playerAction == "ATTACK") "ATTACK ⚔️" else "DEFEND 🛡️"
        val aiActionPretty =
            if (aiAction == "ATTACK") "ATTACK ⚔️" else "DEFEND 🛡️"

        // Send round data to RoundResultActivity
        val intent = Intent(this, RoundResultActivity::class.java).apply {
            putExtra("playerName", playerCard.name)
            putExtra("playerImage", playerCard.imageRes)
            putExtra("aiName", aiCard.name)
            putExtra("aiImage", aiCard.imageRes)
            putExtra("resultMessage", resultMessage)
            putExtra("playerStrength", playerCard.strength)
            putExtra("aiStrength", aiCard.strength)
            putExtra("playerAction", playerActionPretty)
            putExtra("aiAction", aiActionPretty)
        }

        // Keep music playing – no fadeOut/stop here
        startActivity(intent)
        finish()
    }
}
