package com.example.androidbackgroundservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AudioPlayService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    companion object {
        const val FILENAME = "FILENAME"
        const val COMMAND = "COMMAND"
        const val PLAY = "PLAY"
        const val STOP = "STOP"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val filename = intent.getStringExtra(FILENAME)
        val command = intent.getStringExtra(COMMAND)

        when (command) {
            PLAY -> {
                audioPlay(filename)
            }
            STOP -> {
                audioStop()
            }
        }
        return START_STICKY
    }

    private fun audioPlay(filename: String?) {
        if (filename != null) {
            val assetFileDescriptor = assets.openFd(filename)
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            assetFileDescriptor.close()
            mediaPlayer.prepare()
            mediaPlayer.setVolume(10f, 10f)
            mediaPlayer.isLooping = false
            mediaPlayer.start()
        }
    }

    private fun audioStop() {
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}