package com.example.animalbattle

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalbattle.data.AnimalCard
import com.example.animalbattle.data.Deck
import com.example.animalbattle.util.ScoreManager


class GameActivity : AppCompatActivity() {

    // UI-element från layouten
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

        // Koppla UI-refs
        playerImage = findViewById(R.id.iv_player_image)
        playerName = findViewById(R.id.tv_player_name)
        playerStrength = findViewById(R.id.tv_player_strength)
        playerPersonality = findViewById(R.id.tv_player_personality)
        scoreText = findViewById(R.id.tv_score)
        attackButton = findViewById(R.id.button_attack)
        defendButton = findViewById(R.id.button_defend)

        startNewRound()

        private fun playRound(playerAction: String) {
            // Här lägger vi rundlogiken i nästa steg
        }

        attackButton.setOnClickListener {
            playRound("ATTACK")
        }

        defendButton.setOnClickListener {
            playRound("DEFEND")
        }

    }

    private fun startNewRound() {
        // Dra kort till spelaren och AI
        playerCard = deck.drawRandomCard()
        aiCard = deck.drawRandomCard()

        // Uppdatera spelarens kort i UI
        playerImage.setImageResource(playerCard.imageRes)
        playerName.text = playerCard.name
        playerStrength.text = "Strength: ${playerCard.strength}"
        playerPersonality.text = "Personality: ${playerCard.personality.name.lowercase().replaceFirstChar { it.uppercase() }}"

        // Uppdatera score-raden
        scoreText.text = "Score: Player ${ScoreManager.playerScore} vs AI ${ScoreManager.aiScore}"
    }

}
