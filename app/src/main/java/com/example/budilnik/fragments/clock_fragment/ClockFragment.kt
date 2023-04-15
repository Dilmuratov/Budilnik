package com.example.budilnik.fragments.clock_fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.example.budilnik.R
import com.example.budilnik.data.adapter.WorldClockAdapter
import com.example.budilnik.data.models.WorldClock
import com.example.budilnik.databinding.FragmentClockBinding

class ClockFragment : Fragment(R.layout.fragment_clock) {
    lateinit var binding: FragmentClockBinding
    private var adapter = WorldClockAdapter()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClockBinding.bind(view)

        binding.recyclerView.adapter = adapter

        val list = mutableListOf<WorldClock>()

        val worldClock = WorldClock(0, "America/New_York")
        val worldClock2 = WorldClock(1, "Europe/Barcelona")

        list.add(worldClock)
        list.add(worldClock2)

        adapter.submitList(list)

        binding.recyclerView.adapter = adapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
    }

    private fun addClock(){

    }

}