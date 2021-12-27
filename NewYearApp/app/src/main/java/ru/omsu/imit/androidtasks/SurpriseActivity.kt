package ru.omsu.imit.androidtasks

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_surprise.*

class SurpriseActivity : AppCompatActivity() {
    private var isPlays = false
    private lateinit var mPlayer: MediaPlayer

    private fun stopPlay() {
        with(mPlayer) {
            stop()
            prepare()
            seekTo(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surprise)

        mPlayer = MediaPlayer.create(this, R.raw.nysong).apply {
            setOnCompletionListener { stopPlay() }
        }

        playSoundImageButton.setOnClickListener {
            if (isPlays) {
                mPlayer.pause()

                isPlays = false
                playSoundImageButton.setImageResource(android.R.drawable.ic_media_play)
            } else {
                mPlayer.start()

                isPlays = true
                playSoundImageButton.setImageResource(android.R.drawable.ic_media_pause)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isPlays) {
            stopPlay()
        }
    }
}