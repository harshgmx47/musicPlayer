package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    lateinit var runnable: Runnable;
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //new MediaPlayer Object
        val mediaPlayer : MediaPlayer = MediaPlayer.create(this,R.raw.music)
       // Create Play button
         val playButton = findViewById<ImageButton>(R.id.play_btn)
                playButton.setOnClickListener {
                    //check if media player is playing or not
                    if(!mediaPlayer.isPlaying){

                        mediaPlayer.start()
                        startService(Intent(this,NewService::class.java))

                        //change the button if media player is playing
                        playButton.setImageResource(R.drawable.ic_baseline_pause_24)
                    }else if(mediaPlayer.isPlaying){ // media player is playing and we can pause
                        mediaPlayer.pause()
                        playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)

                    }
                }
        //create seekBar
        val seekbar = findViewById<SeekBar>(R.id.seekbar)
        //initial is 0
        seekbar.progress = 0;
        //maximum duration of music
        seekbar.max =mediaPlayer.duration;
        //create event of seekbar
        seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if(changed){
                    mediaPlayer.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        //create runnable instance to get current position of song
        runnable = Runnable {
            seekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)

        //reset after music is finished
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            seekbar.progress = 0;
        }

    }
}