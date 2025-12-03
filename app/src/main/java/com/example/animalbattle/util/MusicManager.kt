package com.example.animalbattle.util

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.example.animalbattle.R

object MusicManager {

    private var mediaPlayer: MediaPlayer? = null
    private var currentResId: Int? = null
    private var isFadingOut = false

    // I use this internal helper to start any track I want, so the public functions stay simple.
    private fun play(context: Context, resId: Int, loop: Boolean) {
        // If this exact track is already running, I just leave it as it is.
        if (mediaPlayer != null && mediaPlayer!!.isPlaying && currentResId == resId) {
            return
        }

        // If something else is playing, I clean it up first.
        stop()

        mediaPlayer = MediaPlayer.create(context.applicationContext, resId).apply {
            isLooping = loop
            setVolume(1f, 1f)
            start()
        }
        currentResId = resId
    }

    // This is the music I use in the main menu.
    fun playMenuMusic(context: Context) {
        play(context, R.raw.menu_music, loop = true)
    }

    // I keep this for older code that still calls startMenuMusic().
    fun startMenuMusic(context: Context) {
        playMenuMusic(context)
    }

    // This track plays whenever I enter the battle/game screens.
    fun playBattleMusic(context: Context) {
        play(context, R.raw.battle_music, loop = true)
    }

    // This is the short jingle I play on the game-over screen (no looping).
    fun playEndJingle(context: Context) {
        play(context, R.raw.game_end_jingle, loop = false)
    }

    // I use this whenever I want a smoother transition between screens.
    fun fadeOut(onComplete: () -> Unit) {
        val player = mediaPlayer
        if (player == null) {
            // Nothing to fade, so I just continue immediately.
            onComplete()
            return
        }

        if (isFadingOut) {
            // Avoid double-triggering the fade animation.
            return
        }

        isFadingOut = true
        val handler = Handler(Looper.getMainLooper())
        var volume = 1.0f
        val step = 0.1f
        val intervalMs = 70L

        val fadeRunnable = object : Runnable {
            override fun run() {
                val mp = mediaPlayer ?: run {
                    isFadingOut = false
                    onComplete()
                    return
                }

                volume -= step
                if (volume <= 0f) {
                    // Fully faded out — now I kill the track and move on.
                    mp.setVolume(0f, 0f)
                    stop()
                    isFadingOut = false
                    onComplete()
                } else {
                    mp.setVolume(volume, volume)
                    handler.postDelayed(this, intervalMs)
                }
            }
        }

        handler.post(fadeRunnable)
    }

    // Whenever I switch tracks, I stop and release the current MediaPlayer cleanly.
    fun stop() {
        mediaPlayer?.apply {
            try {
                stop()
            } catch (_: IllegalStateException) {
                // If it's already stopped, I just ignore it.
            }
            release()
        }
        mediaPlayer = null
        currentResId = null
        isFadingOut = false
    }

    // I call this if I need to fully shut down audio across the entire app.
    fun release() {
        stop()
    }
}
