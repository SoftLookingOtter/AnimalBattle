package com.example.animalbattle.logic

import com.example.animalbattle.data.AnimalCard

object GameLogic {

    fun calculateRound(
        playerCard: AnimalCard,
        aiCard: AnimalCard,
        playerAction: String,
        aiAction: String
    ): Int {

        val playerBase = playerCard.strength
        val aiBase = aiCard.strength

        // Here I only give a bonus to DEFEND.
        // ATTACK uses the raw strength of the card,
        // while DEFEND gets a small boost so it can sometimes save a weaker animal.
        val playerBonus = if (playerAction == "DEFEND") 10 else 0
        val aiBonus = if (aiAction == "DEFEND") 10 else 0

        // I return the difference between the final values.
        // > 0 means the player won the round
        // < 0 means the AI won
        // 0 means it's a tie
        return (playerBase + playerBonus) - (aiBase + aiBonus)
    }
}
