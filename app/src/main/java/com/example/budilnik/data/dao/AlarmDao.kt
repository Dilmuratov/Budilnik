package com.example.budilnik.data.dao

import androidx.room.*
import com.example.budilnik.data.models.Alarm
import com.example.budilnik.data.models.WorldClock

@Dao
interface AlarmDao {

    //Alarm table

    @Query("SELECT * FROM alarms")
    suspend fun getListOfAlarms(): List<Alarm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

}