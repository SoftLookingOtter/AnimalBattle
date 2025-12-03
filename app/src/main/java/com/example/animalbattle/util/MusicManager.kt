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

    /**
     * Generic play function used by all specific music calls.
     */
    private fun play(context: Context, resId: Int, loop: Boolean) {
        // If same track is already playing, do nothing
        if (mediaPlayer != null && mediaPlayer!!.isPlaying && currentResId == resId) {
            return
        }

        // Stop any existing music first
        stop()

        mediaPlayer = MediaPlayer.create(context.applicationContext, resId).apply {
            isLooping = loop
            setVolume(1f, 1f)
            start()
        }
        currentResId = resId
    }

    /**
     * Menu music – plays on the main screen.
     */
    fun playMenuMusic(context: Context) {
        play(context, R.raw.menu_music, loop = true)
    }

    /**
     * Backwards-compatible alias for older code in MainActivity.
     */
    fun startMenuMusic(context: Context) {
        playMenuMusic(context)
    }

    /**
     * Battle music – used in the game / battle screens.
     */
    fun playBattleMusic(context: Context) {
        play(context, R.raw.battle_music, loop = true)
    }

    /**
     * End jingle – short non-looping clip for the game over screen.
     */
    fun playEndJingle(context: Context) {
        play(context, R.raw.game_end_jingle, loop = false)
    }

    /**
     * Fade out the current music and then stop it.
     * onComplete will be called after fade-out is finished.
     */
    fun fadeOut(onComplete: () -> Unit) {
        val player = mediaPlayer
        if (player == null) {
            // Nothing playing, just continue
            onComplete()
            return
        }

        if (isFadingOut) {
            // Already fading, avoid double-calls
            return
        }

        isFadingOut = true
        val handler = Handler(Looper.getMainLooper())
        var volume = 1.0f
        val step = 0.1f
        val intervalMs = 70L

        val fadeRunnable = object : Runnable {
            override fun run() {
                val mp = mediaPlayer
                if (mp == null) {
                    isFadingOut = false
                    onComplete()
                    return
                }

                volume -= step
                if (volume <= 0f) {
                    // Fully faded out
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

    /**
     * Stop and release the current MediaPlayer.
     */
    fun stop() {
        mediaPlayer?.apply {
            try {
                stop()
            } catch (_: IllegalStateException) {
                // Ignore if already stopped
            }
            release()
        }
        mediaPlayer = null
        currentResId = null
        isFadingOut = false
    }

    /**
     * Call this from the Application or when the app is fully quitting, if needed.
     */
    fun release() {
        stop()
    }
}
