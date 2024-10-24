package com.coretechies.jaap.utils

actual fun playBeep(context :Any?){
    private var audioPlayer: AVAudioPlayer? = null

    val url = NSBundle.mainBundle().urlForResource("play_beep", "mp3")
    audioPlayer = AVAudioPlayer(contentsOfURL = url, error = null)
    audioPlayer?.play()

}