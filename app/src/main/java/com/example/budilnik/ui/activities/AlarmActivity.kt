package com.example.budilnik.ui.activities

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.budilnik.R
import com.example.budilnik.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stopViewAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_stop)

        val pauseViewAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_pause)

        val alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha)

        binding.viewStop.startAnimation(stopViewAnimation)

        stopViewAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.viewPause.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (binding.viewPause.visibility == View.INVISIBLE){
                    //binding.viewStop.startAnimation(alphaAnimation)
                    binding.viewPause.visibility = View.VISIBLE
                    binding.viewPause.startAnimation(pauseViewAnimation)
                } else{
                    //binding.viewPause.startAnimation(alphaAnimation)
                    binding.viewStop.visibility = View.VISIBLE
                    binding.viewStop.startAnimation(stopViewAnimation)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })

        pauseViewAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.viewStop.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (binding.viewPause.visibility == View.INVISIBLE){
                    //binding.viewStop.startAnimation(alphaAnimation)
                    binding.viewPause.visibility = View.VISIBLE
                    binding.viewPause.startAnimation(pauseViewAnimation)
                } else{
                    //binding.viewPause.startAnimation(alphaAnimation)
                    binding.viewStop.visibility = View.VISIBLE
                    binding.viewStop.startAnimation(stopViewAnimation)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })

        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }
}