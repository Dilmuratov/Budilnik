package com.example.budilnik.ui.fragments.clock_fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.budilnik.R
import com.example.budilnik.data.adapter.WorldClockAdapter
import com.example.budilnik.data.models.WorldClock
import com.example.budilnik.databinding.FragmentClockBinding

class ClockFragment : Fragment(R.layout.fragment_clock) {
    lateinit var binding: FragmentClockBinding
    private val adapter = WorldClockAdapter()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClockBinding.bind(view)

        binding.recyclerView.adapter = adapter

        adapter.submitList(fillList())

        adapter.setOnItemSelectedListener {worldClock ->
            adapter.removeItem(worldClock)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
    }

    private fun toSearchFragment(){
        binding.btnAdd.setOnClickListener {

        }
    }

    private fun fillList(): MutableList<WorldClock> {
        val list = mutableListOf<WorldClock>()

        list.add(WorldClock(
            0,
            "Astana",
            "Asia/Astana"
        ))

        list.add(WorldClock(
            0,
            "Tashkent",
            "Asia/Tashkent"
        ))

        list.add(WorldClock(
            0,
            "New York",
            "America/New_York"
        ))

        list.add(WorldClock(
            0,
            "Seul",
            "Asia/Seul"
        ))

        return list
    }
}