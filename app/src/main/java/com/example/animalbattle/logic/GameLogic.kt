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

        // Attack = högre offensiv bonus
        // Defend = lägre men finns ändå så man inte får noll
        val playerBonus = if (playerAction == "ATTACK") 20 else 10
        val aiBonus = if (aiAction == "ATTACK") 20 else 10

        // Returnerar skillnaden
        // > 0 = spelaren vann
        // < 0 = AI vann
        // 0 = oavgjort
        return (playerBase + playerBonus) - (aiBase + aiBonus)
    }
}
