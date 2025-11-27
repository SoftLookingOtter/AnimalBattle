package com.example.animalbattle.util

object ScoreManager {
    var playerScore: Int = 0
    var aiScore: Int = 0

    // Hur många rundvinster krävs för att vinna matchen
    const val WIN_SCORE: Int = 3

    fun reset() {
        playerScore = 0
        aiScore = 0
    }
}
