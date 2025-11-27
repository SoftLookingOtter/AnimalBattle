package com.example.animalbattle.logic

import com.example.animalbattle.data.Personality

object AiLogic {

    fun chooseAction(personality: Personality): String {
        return when (personality) {

            Personality.AGGRESSIVE -> {
                // Aggressiv → attackerar 80% av tiden
                if ((1..10).random() > 2) "ATTACK" else "DEFEND"
            }

            Personality.DEFENSIVE -> {
                // Defensiv → försvarar 70% av tiden
                if ((1..10).random() > 3) "DEFEND" else "ATTACK"
            }

            Personality.RANDOM -> {
                // 50/50 chans
                listOf("ATTACK", "DEFEND").random()
            }
        }
    }
}