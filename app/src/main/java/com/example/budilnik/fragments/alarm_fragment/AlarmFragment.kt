package com.example.budilnik.fragments.alarm_fragment

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.budilnik.*
import com.example.budilnik.data.adapter.AlarmAdapter
import com.example.budilnik.data.dao.AlarmDao
import com.example.budilnik.data.dao.AlarmDataBase
import com.example.budilnik.data.models.Alarm
import com.example.budilnik.databinding.FragmentAlarmBinding
import com.example.budilnik.databinding.ItemAlarmBinding
import com.example.budilnik.dialog.NoteDialog
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    lateinit var binding: FragmentAlarmBinding
    lateinit var dao: AlarmDao
    var adapter = AlarmAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlarmBinding.bind(view)

        initVariables()

        setAlarm()

        deleteAlarm()

        updateAlarm()

        adapter.setOnCommentClickListener {itemAlarmBinding: ItemAlarmBinding, alarm: Alarm ->
            val dialog = NoteDialog()
            dialog.show(parentFragmentManager, "TTTT")
            dialog.setOnAcceptClickListener { str ->
                if (str.isNotEmpty()){
                    itemAlarmBinding.tvComment.text = str
                    lifecycleScope.launchWhenResumed {
                        alarm.comment = str
                        dao.updateAlarm(alarm)
                        adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }.toMutableList())
                    }
                }
                dialog.dismiss()
            }
        }
    }

    fun initVariables() {
        binding.recyclerView.adapter = adapter

        dao = AlarmDataBase.getInstance((requireActivity() as MainActivity)).getAlarmDao()

        lifecycleScope.launchWhenResumed {
            adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }.toMutableList())
        }
    }

    private fun deleteAlarm() {
        val itemTouchHelperCallBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val alarm: Alarm? = adapter.getItemByPosition(position)

                Toast.makeText((requireActivity() as MainActivity), "Deleted", Toast.LENGTH_SHORT)
                    .show()

                lifecycleScope.launchWhenResumed {
                    if (alarm != null) {
                        dao.deleteAlarm(alarm)
                    }
                }
                if (alarm != null) {
                    adapter.removeItem(alarm)
                }

                adapter.notifyItemRemoved(position)

                Snackbar.make(
                    viewHolder.itemView,
                    "Deleted",
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setAction("Undo") {
                        lifecycleScope.launchWhenResumed {
                            if (alarm != null) {
                                dao.addAlarm(alarm)
                            }
                        }
                        if (alarm != null) {
                            adapter.addItem(alarm)
                        }
                        adapter.notifyItemInserted(position)

                        binding.recyclerView.scrollToPosition(position)
                    }
                    setActionTextColor(Color.YELLOW)
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.recyclerView)
        }

        adapter.setOnDeleteClickListener { alarm ->

            lifecycleScope.launchWhenResumed {
                dao.deleteAlarm(alarm)
            }
            adapter.removeItem(alarm)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setAlarm() {
        binding.btnAdd.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetlistener =
                TimePickerDialog.OnTimeSetListener { timePicker: TimePicker?, hour: Int, minute: Int ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    val time = SimpleDateFormat("HH:mm").format(cal.time)
                    val alarm = Alarm(0, time, isActivate = true, isMondayActivated = true, comment = "Yorliq kiritish")
                    lifecycleScope.launchWhenResumed {
                        dao.addAlarm(alarm)
                    }
                    adapter.addItem(alarm)
                }
            TimePickerDialog(
                requireActivity(),
                timeSetlistener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateAlarm() {
        adapter.setOnWeekDaysClickListener { alarm ->
            lifecycleScope.launchWhenResumed {
                dao.updateAlarm(alarm)
                adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }.toMutableList())
            }
        }

        adapter.setOnTimeClickListener { alarm ->
            val cal = Calendar.getInstance()
            val timeSetlistener =
                TimePickerDialog.OnTimeSetListener { timePicker: TimePicker?, hour: Int, minute: Int ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    alarm.time = SimpleDateFormat("HH:mm").format(cal.time)
                    lifecycleScope.launchWhenResumed {
                        dao.updateAlarm(alarm)
                        adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }.toMutableList())
                    }
                }
            TimePickerDialog(
                requireActivity(),
                timeSetlistener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }
}