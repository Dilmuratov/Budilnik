package com.example.budilnik.ui.fragments.alarm_fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.budilnik.*
import com.example.budilnik.data.adapter.AlarmAdapter
import com.example.budilnik.data.local.dao.AlarmDao
import com.example.budilnik.data.local.dao.data_base.AlarmDataBase
import com.example.budilnik.data.models.Alarm
import com.example.budilnik.databinding.FragmentAlarmBinding
import com.example.budilnik.databinding.ItemAlarmBinding
import com.example.budilnik.ui.dialog.NoteDialog
import com.example.budilnik.ui.activities.AlarmActivity
import com.example.budilnik.ui.activities.MainActivity
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

        (requireActivity() as MainActivity).binding.icMore.setOnClickListener {
//            val intent = Intent(requireActivity(), AlarmActivity::class.java)
//            startActivity(intent)
            showNotification()
        }
    }

    private fun initVariables() {
        binding.recyclerView.adapter = adapter

        dao = AlarmDataBase.getInstance((requireActivity() as MainActivity)).getAlarmDao()

        lifecycleScope.launchWhenResumed {
            adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }
                .toMutableList())

            cancelAllAndReset()
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
                    val alarm = Alarm(
                        0,
                        time,
                        isActivate = true,
                        isMondayActivated = true,
                        comment = "Yorliq kiritish"
                    )
                    lifecycleScope.launchWhenResumed {
                        dao.addAlarm(alarm)
                        showNotification(alarm)
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
                adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }
                    .toMutableList())
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
                        cancelAllAndReset()
                        adapter.submitList(
                            dao.getListOfAlarms().toMutableList().sortedBy { it.time }
                                .toMutableList()
                        )
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

        adapter.setOnCommentClickListener { itemAlarmBinding: ItemAlarmBinding, alarm: Alarm ->
            val dialog = NoteDialog()
            dialog.show(parentFragmentManager, "TTTT")
            dialog.setOnAcceptClickListener { str ->
                if (str.isNotEmpty()) {
                    itemAlarmBinding.tvComment.text = str
                    lifecycleScope.launchWhenResumed {
                        alarm.comment = str
                        dao.updateAlarm(alarm)
                        adapter.submitList(
                            dao.getListOfAlarms().toMutableList().sortedBy { it.time }
                                .toMutableList()
                        )
                    }
                }
                dialog.dismiss()
            }
        }

        adapter.setOnVibrateClickListener {
            val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        5000,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else vibrator.vibrate(5000)
        }
    }

    fun showNotification(alarm: Alarm) {
        val intent = Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + alarm.calculateInterval(),
                pendingIntent
            ), pendingIntent
        )

    }

    fun showNotification() {
        val intent = Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val closeButtonIntent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        val closeButtonPendingIntent =
            PendingIntent.getService(
                context,
                0,
                closeButtonIntent,
                PendingIntent.FLAG_MUTABLE
            )

        val closeButton = NotificationCompat.Action.Builder(
            android.R.drawable.ic_delete,
            "Close",
            closeButtonPendingIntent
        ).build()

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + 5000,
                pendingIntent
            ), pendingIntent
        )
    }

    fun Alarm.calculateInterval(): Int {
        val timeInString = this.time
        val hour = 10 * timeInString[0].toString().toInt() + timeInString[1].toString().toInt()
        val minute = 10 * timeInString[3].toString().toInt() + timeInString[4].toString().toInt()
        val minutes = hour * 60 + minute
        val currentTime = Calendar.getInstance()
        val currentMinutesOfDay =
            currentTime.get(Calendar.HOUR_OF_DAY) * 60 + currentTime.get(Calendar.MINUTE)
        var interval = minutes - currentMinutesOfDay
        while (interval < 0) {
            interval += 1440
        }
        return interval * 60 * 1000
    }

    fun cancelAllAndReset() {
        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.cancelAll()

        lifecycleScope.launchWhenResumed {
            dao.getListOfAlarms().forEach {
                showNotification(it)
            }
        }
    }
}