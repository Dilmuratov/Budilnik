package com.example.budilnik.fragments.timer_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.budilnik.R
import com.example.budilnik.databinding.FragmentTimerBinding

class TimerFragment: Fragment(R.layout.fragment_timer) {
    lateinit var binding: FragmentTimerBinding
    var result: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)

        clickButton()
    }

    private fun checkLenght(num: String){
        if (num.length == 1){
            if (result.length < 6){
                result = "$result$num"
                placement()
            }
        } else{
            if (result.length < 5){
                result = "$result$num"
            } else if (result.length == 5){
                result = "$result${num.first()}"
                placement()
            }
        }
    }

    private fun clickButton(){
        binding.cvOne.setOnClickListener {
            checkLenght("1")
        }
        binding.cvTwo.setOnClickListener {
            checkLenght("2")
        }
        binding.cvThree.setOnClickListener {
            checkLenght("3")
        }
        binding.cvFour.setOnClickListener {
            checkLenght("4")
        }
        binding.cvFive.setOnClickListener {
            checkLenght("5")
        }
        binding.cvSix.setOnClickListener {
            checkLenght("6")
        }
        binding.cvSeven.setOnClickListener {
            checkLenght("7")
        }
        binding.cvEight.setOnClickListener {
            checkLenght("8")
        }
        binding.cvNine.setOnClickListener {
            checkLenght("9")
        }
        binding.cvDoubleZero.setOnClickListener {
            if (result.isNotEmpty()){
                checkLenght("00")
            }
        }
        binding.cvZero.setOnClickListener {
            if (result.isNotEmpty()){
                checkLenght("0")
            }
        }
        binding.cvClear.setOnClickListener {
            result = result.dropLast(1)
            placement()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun placement(){
        if (result.isNotEmpty()){
            val lenght = result.length
            if (lenght == 6){
                binding.tvHour.text = "${result[0]}${result[1]}"
                binding.tvMinute.text = "${result[2]}${result[3]}"
                binding.tvSecunds.text = "${result[4]}${result[5]}"
            } else if (lenght == 5){
                binding.tvHour.text = "0${result[0]}"
                binding.tvMinute.text = "${result[1]}${result[2]}"
                binding.tvSecunds.text = "${result[3]}${result[4]}"
            } else if (lenght == 4){
                binding.tvHour.text = "00"
                binding.tvMinute.text = "${result[0]}${result[1]}"
                binding.tvSecunds.text = "${result[2]}${result[3]}"
            } else if (lenght == 3){
                binding.tvHour.text = "00"
                binding.tvMinute.text = "0${result[0]}"
                binding.tvSecunds.text = "${result[1]}${result[2]}"
            } else if (lenght == 2){
                binding.tvHour.text = "00"
                binding.tvMinute.text = "00"
                binding.tvSecunds.text = "${result[0]}${result[1]}"
            } else {
                binding.tvHour.text = "00"
                binding.tvMinute.text = "00"
                binding.tvSecunds.text = "0${result[0]}"
            }
        } else {
            binding.tvHour.text = "00"
            binding.tvMinute.text = "00"
            binding.tvSecunds.text = "00"
        }
    }
}

