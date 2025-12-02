package com.example.animalbattle.util

object ScoreManager {
    var playerScore: Int = 0
    var aiScore: Int = 0

    const val WIN_SCORE = 3

    fun reset() {
        playerScore = 0
        aiScore = 0
    }
}

