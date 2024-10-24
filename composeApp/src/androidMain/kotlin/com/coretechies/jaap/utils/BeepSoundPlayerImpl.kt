package com.coretechies.jaap.utils

import android.content.Context
import android.media.MediaPlayer
import com.coretechies.jaap.R

    actual fun playBeep(context :Any?) {
        val androidContext = context as? Context
        if (androidContext != null) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.play_beep)
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        mediaPlayer.start()
    }
}