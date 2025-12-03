package com.example.animalbattle.util

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.example.animalbattle.R

object MusicManager {

    private var mediaPlayer: MediaPlayer? = null
    private var currentType: MusicType? = null
    private val handler = Handler(Looper.getMainLooper())

    private enum class MusicType {
        MENU,
        BATTLE,
        JINGLE
    }

    // ========================
    //  Publika metoder
    // ========================

    fun startMenuMusic(context: Context) {
        startMusic(
            context = context,
            resId = R.raw.menu_music,   // din meny-musik
            type = MusicType.MENU,
            looping = true
        )
    }

    fun startBattleMusic(context: Context) {
        startMusic(
            context = context,
            resId = R.raw.battle_music, // din battle-musik
            type = MusicType.BATTLE,
            looping = true
        )
    }

    fun playGameOverJingle(context: Context) {
        startMusic(
            context = context,
            resId = R.raw.game_end_jingle, // din jingle
            type = MusicType.JINGLE,
            looping = false
        )

        // När jingeln är slut → starta menymusik
        mediaPlayer?.setOnCompletionListener {
            startMenuMusic(context)
        }
    }

    /**
     * Fadea ut nuvarande musik och kör [onComplete] när det är klart.
     * Används t.ex. när du går från Main → Game.
     */
    fun fadeOut(onComplete: () -> Unit) {
        val player = mediaPlayer
        if (player == null) {
            onComplete()
            return
        }

        val durationMs = 800L
        val steps = 8
        val delay = durationMs / steps
        var currentStep = 0

        fun stepFade() {
            val p = mediaPlayer
            if (p == null) {
                onComplete()
                return
            }

            if (currentStep >= steps) {
                try {
                    if (p.isPlaying) {
                        p.stop()
                    }
                } catch (_: IllegalStateException) {
                    // Ignorera om den redan är stoppad
                }
                p.release()
                mediaPlayer = null
                currentType = null
                onComplete()
                return
            }

            val volume = 1f - (currentStep.toFloat() / steps.toFloat())
            try {
                p.setVolume(volume, volume)
            } catch (_: IllegalStateException) {
                onComplete()
                return
            }

            currentStep++
            handler.postDelayed({ stepFade() }, delay)
        }

        stepFade()
    }

    fun stop() {
        val p = mediaPlayer ?: return
        try {
            if (p.isPlaying) {
                p.stop()
            }
        } catch (_: IllegalStateException) { }
        p.release()
        mediaPlayer = null
        currentType = null
    }

    fun release() = stop()

    // ========================
    //  Privata helpers
    // ========================

    private fun startMusic(
        context: Context,
        resId: Int,
        type: MusicType,
        looping: Boolean
    ) {
        if (currentType == type && mediaPlayer?.isPlaying == true) return

        stop()

        mediaPlayer = MediaPlayer.create(context.applicationContext, resId).apply {
            isLooping = looping
            setVolume(1f, 1f)
            start()
        }

        currentType = type
    }
}
