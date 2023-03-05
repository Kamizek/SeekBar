package com.example.seekbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private fun getSeekBarProgress(seekBar: SeekBar): Int{
        return seekBar.progress
    }

    private fun setProgressBarProgress(progressBar: ProgressBar, progress: Int){
        progressBar.progress = progress
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = mutableListOf<ImageView>(
            findViewById(R.id.img1),
            findViewById(R.id.img2),
            findViewById(R.id.img3)
        )

        val xSeekBarList = mutableListOf<SeekBar>(
            findViewById(R.id.x1),
            findViewById(R.id.x2),
            findViewById(R.id.x3)
        )

        val ySeekBarList = mutableListOf<SeekBar>(
            findViewById(R.id.y1),
            findViewById(R.id.y2),
            findViewById(R.id.y3)
        )

        val reset = findViewById<Button>(R.id.reset)
        val progressX = findViewById<ProgressBar>(R.id.progresbarx)
        val progressY = findViewById<ProgressBar>(R.id.progresbary)

        reset.setOnClickListener() {
            for(seekBar in xSeekBarList + ySeekBarList){
                seekBar.progress = 100
            }
        }

        fun onSeekBarChangeListener(
            seekBar: SeekBar,
            scaleAxis: (ImageView, Float) -> Unit,
            sumFunction: () -> Int,
            progressBar: ProgressBar
        ) = object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progres: Int, fromUser: Boolean
            ) {
                val scale = progres/100f
                images[xSeekBarList.indexOf(seekBar)].let { imageView ->
                    scaleAxis(imageView, scale)
                }
                setProgressBarProgress(progressBar, sumFunction() / 3)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }

        for (xSeekBar in xSeekBarList){
            xSeekBar.setOnSeekBarChangeListener(
                onSeekBarChangeListener(
                    xSeekBar, ImageView::setScaleX, this::suma_xu, progressX
                )
            )
        }

        for (ySeekBar in ySeekBarList){
            ySeekBar.setOnSeekBarChangeListener(
                onSeekBarChangeListener(
                    ySeekBar, ImageView::setScaleY, this::suma_yu, progressY
                )
            )
        }
    }

    private fun suma_xu(): Int{
        return (0 until 3).sumOf {
            getSeekBarProgress(findViewById<SeekBar>(resources.getIdentifier(
                "x${it+1}", "id", packageName
            )))
        }
    }

    private fun suma_yu(): Int{
        return (0 until 3).sumOf {
            getSeekBarProgress(findViewById<SeekBar>(resources.getIdentifier(
                "y${it+1}", "id", packageName
            )))
        }
    }
}