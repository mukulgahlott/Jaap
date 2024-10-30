package com.coretechies.jaap.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSBundle

@OptIn(ExperimentalForeignApi::class)
actual fun playBeep(context :Any?){
    var audioPlayer: AVAudioPlayer? = null

    val url = NSBundle.mainBundle().URLForResource("play_beep", "mp3")
    if (url != null) {
        audioPlayer = AVAudioPlayer(contentsOfURL = url, error = null)
        audioPlayer.play()
    } else {
        // Handle the case where the URL is null (e.g., file not found)
        println("Beep sound file not found.")
    }

}