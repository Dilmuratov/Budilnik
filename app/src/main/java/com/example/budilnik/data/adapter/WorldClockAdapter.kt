package com.example.budilnik.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budilnik.data.models.WorldClock
import com.example.budilnik.databinding.ItemWorldClockBinding
import java.util.*

class WorldClockAdapter : ListAdapter<WorldClock, WorldClockAdapter.ViewHolder>(myDiffUtil) {

    inner class ViewHolder(private var binding: ItemWorldClockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val worldClock = getItem(position)
            binding.tvCountry.text = worldClock.timeZone
            binding.tvTime.text = getDate(worldClock.timeZone)
            binding.tvInterval.text = "${getInterval(worldClock.timeZone)} soat"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWorldClockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    private fun getDate(timeZoneId: String): String {
        val calendar = Calendar.getInstance()
        val timeZone = TimeZone.getTimeZone(timeZoneId)
        calendar.timeZone = timeZone

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val newHour = if (hour > 9) "$hour" else "0$hour"
        val newMinute = if (minute > 9) "$minute" else "0$minute"

        return "$newHour:$newMinute"
    }

    private fun getInterval(timeZoneId: String): Int {
        val userCalendar = Calendar.getInstance()
        val calendar1 = Calendar.getInstance()
        val userTimeZone = TimeZone.getTimeZone("Asia/Tashkent")
        val timeZone1 = TimeZone.getTimeZone(timeZoneId)

        userCalendar.timeZone = userTimeZone
        calendar1.timeZone = timeZone1

        val userHour = userCalendar.get(Calendar.HOUR_OF_DAY)
        val hour = calendar1.get(Calendar.HOUR_OF_DAY)

        return userHour - hour

    }

    private object myDiffUtil : DiffUtil.ItemCallback<WorldClock>() {

        override fun areItemsTheSame(oldItem: WorldClock, newItem: WorldClock) = oldItem == newItem

        override fun areContentsTheSame(oldItem: WorldClock, newItem: WorldClock) =
            (oldItem.timeZone == newItem.timeZone) && (newItem.timeZone == oldItem.timeZone)

    }

}