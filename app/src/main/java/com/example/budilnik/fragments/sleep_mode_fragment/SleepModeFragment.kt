package com.example.budilnik.fragments.sleep_mode_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.budilnik.R
import com.example.budilnik.databinding.FragmentSleepModeBinding

class SleepModeFragment: Fragment(R.layout.fragment_sleep_mode) {

    lateinit var binding: FragmentSleepModeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepModeBinding.bind(view)
    }
}