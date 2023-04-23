package com.example.budilnik.data.adapter

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budilnik.data.models.Alarm
import com.example.budilnik.databinding.ItemAlarmBinding

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.ViewHolder>(myDiffUtil) {

    private var onWeekDaysClickListener: ((Alarm) -> Unit)? = null
    private var onTimeClickListener: ((Alarm) -> Unit)? = null
    private var onDeleteClickListener: ((Alarm) -> Unit)? = null
    private var onCommentClickListener: ((ItemAlarmBinding, Alarm) -> Unit)? = null

    fun setOnWeekDaysClickListener(block: (Alarm) -> Unit) {
        onWeekDaysClickListener = block
    }

    fun setOnTimeClickListener(block: (Alarm) -> Unit) {
        onTimeClickListener = block
    }

    fun setOnDeleteClickListener(block: (Alarm) -> Unit) {
        onDeleteClickListener = block
    }

    fun setOnCommentClickListener(block: (ItemAlarmBinding, Alarm) -> Unit) {
        onCommentClickListener = block
    }

    inner class ViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val alarm = getItem(position)
            binding.tvTime.text = alarm.time
            binding.tvComment.text = alarm.comment
            binding.tvMonday.isSelected = alarm.isMondayActivated
            binding.tvTuesday.isSelected = alarm.isTuesdayActivated
            binding.tvWednesday.isSelected = alarm.isWednesdayActivated
            binding.tvThursday.isSelected = alarm.isThursdayAcivated
            binding.tvFriday.isSelected = alarm.isFridayActivated
            binding.tvSaturday.isSelected = alarm.isSaturdayActivated
            binding.tvSunday.isSelected = alarm.isSundayActivated
            binding.switchOnOff.isChecked = alarm.isActivate

            binding.tvDays.text = setDaysToTextView(alarm)
            if (alarm.isActivate) {
                binding.tvTime.setTypeface(null, Typeface.BOLD)
            } else binding.tvTime.setTypeface(null, Typeface.NORMAL)

            if (alarm.isMondayActivated ||
                alarm.isTuesdayActivated ||
                alarm.isThursdayAcivated ||
                alarm.isWednesdayActivated ||
                alarm.isFridayActivated ||
                alarm.isSaturdayActivated ||
                alarm.isSundayActivated
            ) {
                binding.switchOnOff.isChecked = true
            } else if (setDaysToTextView(alarm).isEmpty()) {
                binding.switchOnOff.isChecked = false
            }

            binding.root.setOnClickListener {
                binding.expandableLayout1.toggle()
                binding.expandableLayout2.toggle()
                binding.ivShowHide.rotation = 180F
            }

            binding.switchOnOff.setOnCheckedChangeListener { buttonView, isChecked ->
                if (binding.switchOnOff.isChecked) {
                    binding.tvTime.setTypeface(null, Typeface.BOLD)
                } else binding.tvTime.setTypeface(null, Typeface.NORMAL)
            }

            binding.tvMonday.setOnClickListener {
                binding.tvMonday.isSelected = alarm.isMondayActivated.not()
                alarm.isMondayActivated = alarm.isMondayActivated.not()
                onWeekDaysClickListener?.invoke(alarm)
                binding.tvDays.text = setDaysToTextView(alarm)
            }

            binding.tvTuesday.setOnClickListener {
                binding.tvTuesday.isSelected = alarm.isTuesdayActivated.not()
                alarm.isTuesdayActivated = alarm.isTuesdayActivated.not()
                onWeekDaysClickListener?.invoke(alarm)
                binding.tvDays.text = setDaysToTextView(alarm)
            }

            binding.tvWednesday.setOnClickListener {
                binding.tvWednesday.isSelected = alarm.isWednesdayActivated.not()
                alarm.isWednesdayActivated = alarm.isWednesdayActivated.not()
                onWeekDaysClickListener?.invoke(alarm)
                binding.tvDays.text = setDaysToTextView(alarm)
            }

            binding.tvThursday.setOnClickListener {
                binding.tvThursday.isSelected = alarm.isThursdayAcivated.not()
                alarm.isThursdayAcivated = alarm.isThursdayAcivated.not()
                onWeekDaysClickListener?.invoke(alarm)
                binding.tvDays.text = setDaysToTextView(alarm)
            }

            binding.tvFriday.setOnClickListener {
                binding.tvFriday.isSelected = alarm.isFridayActivated.not()
                alarm.isFridayActivated = alarm.isFridayActivated.not()
                onWeekDaysClickListener?.invoke(alarm)
                binding.tvDays.text = setDaysToTextView(alarm)
            }

            binding.tvSaturday.setOnClickListener {
                binding.tvSaturday.isSelected = alarm.isSaturdayActivated.not()
                alarm.isSaturdayActivated = alarm.isSaturdayActivated.not()
                onWeekDaysClickListener?.invoke(alarm)
                binding.tvDays.text = setDaysToTextView(alarm)
            }

            binding.tvSunday.setOnClickListener {
                binding.tvSunday.isSelected = alarm.isSundayActivated.not()
                alarm.isSundayActivated = alarm.isSundayActivated.not()
                onWeekDaysClickListener?.invoke(alarm)
                binding.tvDays.text = setDaysToTextView(alarm)
            }

            binding.tvTime.setOnClickListener {
                onTimeClickListener?.invoke(alarm)
            }

            binding.tvDelete.setOnClickListener {
                onDeleteClickListener?.invoke(alarm)
            }

            binding.tvComment.setOnClickListener {
                onCommentClickListener?.invoke(binding, alarm)
                Log.d("SSSS", alarm.comment + "A")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    private object myDiffUtil: DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.isActivate == newItem.isActivate &&
                    oldItem.time == newItem.time &&
                    oldItem.comment == newItem.comment &&
                    oldItem.isMondayActivated == newItem.isMondayActivated &&
                    oldItem.isTuesdayActivated == newItem.isTuesdayActivated &&
                    oldItem.isThursdayAcivated == newItem.isThursdayAcivated &&
                    oldItem.isWednesdayActivated == newItem.isWednesdayActivated &&
                    oldItem.isFridayActivated == newItem.isFridayActivated &&
                    oldItem.isSundayActivated == newItem.isSundayActivated &&
                    oldItem.isSaturdayActivated == newItem.isSaturdayActivated
        }
    }

    fun removeItem(item: Alarm) {
        val currentList = currentList.toMutableList()
        currentList.remove(item)
        submitList(currentList.sortedBy { it.time }.toMutableList())
    }

    fun addItem(item: Alarm) {
        val currentList = currentList.toMutableList()
        currentList.add(item)
        submitList(currentList.sortedBy { it.time }.toMutableList())
    }

    fun getItemByPosition(position: Int) = getItem(position)

    private fun setDaysToTextView(alarm: Alarm): String {
        val resultList = mutableListOf<String>()
        if (alarm.isMondayActivated) {
            resultList.add("Du")
        } else resultList.remove("Du")
        if (alarm.isTuesdayActivated) {
            resultList.add("Se")
        } else resultList.remove("Se")
        if (alarm.isThursdayAcivated) {
            resultList.add("Chor")
        } else resultList.remove("Chor")
        if (alarm.isWednesdayActivated) {
            resultList.add("Pay")
        } else resultList.remove("Pay")
        if (alarm.isFridayActivated) {
            resultList.add("Ju")
        } else resultList.remove("Ju")
        if (alarm.isSaturdayActivated) {
            resultList.add("Sha")
        } else resultList.remove("Sha")
        if (alarm.isSundayActivated) {
            resultList.add("Ya")
        } else resultList.remove("Ya")

        if (resultList.isEmpty()) {
            return "Aktiv emas"
        } else if (resultList.size == 7) {
            return "Har kuni"
        } else if (resultList.size == 1) {
            return when (resultList.first()) {
                "Du" -> "Dushanba"
                "Se" -> "Seshanba"
                "Chor" -> "Chorshanba"
                "Pay" -> "Payshanba"
                "Ju" -> "Juma"
                "Sha" -> "Shanba"
                else -> "Yakshanba"
            }
        }

        var result = ""
        resultList.forEach {
            result += if (resultList.indexOf(it) == resultList.lastIndex) {
                it
            } else "$it, "
        }

        return result
    }
}
