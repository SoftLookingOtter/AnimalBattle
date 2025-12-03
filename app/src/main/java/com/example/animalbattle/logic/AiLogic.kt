package com.example.animalbattle.logic

import com.example.animalbattle.data.Personality

object AiLogic {

    fun chooseAction(personality: Personality): String {
        return when (personality) {

            Personality.AGGRESSIVE -> {
                // For aggressive animals I wanted them to attack most of the time,
                // so I set it to roughly an 80% chance to go for ATTACK.
                if ((1..10).random() > 2) "ATTACK" else "DEFEND"
            }

            Personality.DEFENSIVE -> {
                // Defensive animals should feel cautious,
                // so here I made them defend about 70% of the time instead.
                if ((1..10).random() > 3) "DEFEND" else "ATTACK"
            }

            Personality.RANDOM -> {
                // For the random personality I kept it completely even,
                // so it's basically a 50/50 coin flip between ATTACK and DEFEND.
                listOf("ATTACK", "DEFEND").random()
            }
        }
    }
}
