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

        // I start the battle music as soon as this screen loads,
        // and I let it keep playing when I move to the round result screen.
        MusicManager.playBattleMusic(this)

        // Just grabbing all the views I need here.
        playerImage = findViewById(R.id.iv_player_image)
        playerName = findViewById(R.id.tv_player_name)
        playerStrength = findViewById(R.id.tv_player_strength)
        playerPersonality = findViewById(R.id.tv_player_personality)
        scoreText = findViewById(R.id.tv_score)
        attackButton = findViewById(R.id.button_attack)
        defendButton = findViewById(R.id.button_defend)

        startNewRound()

        // When the player taps a button, I run one round with that action.
        attackButton.setOnClickListener { playRound("ATTACK") }
        defendButton.setOnClickListener { playRound("DEFEND") }
    }

    private fun startNewRound() {
        // I draw new cards for both the player and the AI at the start of every round.
        playerCard = deck.drawRandomCard()
        aiCard = deck.drawRandomCard()

        // Then I update the UI so the player sees their new card.
        playerImage.setImageResource(playerCard.imageRes)
        playerName.text = playerCard.name
        playerStrength.text = "Strength: ${playerCard.strength}"
        playerPersonality.text = "Personality: ${
            playerCard.personality.name.lowercase().replaceFirstChar { it.uppercase() }
        }"

        // And I refresh the score display so it's always up to date.
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"
    }

    private fun playRound(playerAction: String) {
        // The AI picks its move based on the personality of its card.
        val aiAction = AiLogic.chooseAction(aiCard.personality)

        // This gives me the numerical result of the round.
        val diff = GameLogic.calculateRound(
            playerCard,
            aiCard,
            playerAction,
            aiAction
        )

        // I update the scores depending on how the round ended.
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

        // I update the running score before leaving this screen.
        scoreText.text =
            "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"

        // These strings are just for the round result UI (to make it look nicer).
        val playerActionPretty =
            if (playerAction == "ATTACK") "ATTACK ⚔️" else "DEFEND 🛡️"
        val aiActionPretty =
            if (aiAction == "ATTACK") "ATTACK ⚔️" else "DEFEND 🛡️"

        // I pass everything to the round result screen so it can show what happened.
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

        // I keep the music running here; the transition to result is instant anyway.
        startActivity(intent)
        finish()
    }
}
