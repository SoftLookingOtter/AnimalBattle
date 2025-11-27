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
import com.example.animalbattle.util.ScoreManager

class GameActivity : AppCompatActivity() {

    // Kortlek och aktuella kort
    private val deck = Deck()
    private lateinit var playerCard: AnimalCard
    private lateinit var aiCard: AnimalCard

    // UI-element
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

        // Koppla UI
        playerImage = findViewById(R.id.iv_player_image)
        playerName = findViewById(R.id.tv_player_name)
        playerStrength = findViewById(R.id.tv_player_strength)
        playerPersonality = findViewById(R.id.tv_player_personality)
        scoreText = findViewById(R.id.tv_score)
        attackButton = findViewById(R.id.button_attack)
        defendButton = findViewById(R.id.button_defend)

        // Starta första rundan
        startNewRound()

        // Knappar
        attackButton.setOnClickListener {
            playRound("ATTACK")
        }

        defendButton.setOnClickListener {
            playRound("DEFEND")
        }
    }

    // ---------- Här börjar "egna" funktioner (UTANFÖR onCreate) ----------

    private fun startNewRound() {
        // Dra kort
        playerCard = deck.drawRandomCard()
        aiCard = deck.drawRandomCard()

        // Uppdatera spelarens kort i UI
        playerImage.setImageResource(playerCard.imageRes)
        playerName.text = playerCard.name
        playerStrength.text = "Strength: ${playerCard.strength}"
        playerPersonality.text = "Personality: ${
            playerCard.personality.name.lowercase().replaceFirstChar { it.uppercase() }
        }"

        // Score-rad
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"
    }

    private fun playRound(playerAction: String) {
        // 1. AI väljer handling
        val aiAction = AiLogic.chooseAction(aiCard.personality)

        // 2. Räkna ut resultat
        val diff = GameLogic.calculateRound(
            playerCard,
            aiCard,
            playerAction,
            aiAction
        )

        // 3. Uppdatera poäng + resultattext
        val resultMessage = when {
            diff > 0 -> {
                ScoreManager.playerScore++
                "You won the round!"
            }
            diff < 0 -> {
                ScoreManager.aiScore++
                "You lost the round!"
            }
            else -> {
                "It's a tie!"
            }
        }

        // 4. Uppdatera score-raden
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"

        // 5. Beskrivning – text om vad du gjorde
        val description = if (playerAction == "ATTACK") {
            "You attacked the ${aiCard.name.lowercase()}!"
        } else {
            "You defended against the ${aiCard.name.lowercase()}!"
        }

        // 6. Starta RoundResultActivity och skicka med allt
        val intent = Intent(this, RoundResultActivity::class.java).apply {
            putExtra("playerName", playerCard.name)
            putExtra("playerImage", playerCard.imageRes)
            putExtra("aiName", aiCard.name)
            putExtra("aiImage", aiCard.imageRes)
            putExtra("description", description)
            putExtra("resultMessage", resultMessage)
        }

        startActivity(intent)
        finish() // stäng den här rundan, nästa startas från RoundResultActivity
    }
}

