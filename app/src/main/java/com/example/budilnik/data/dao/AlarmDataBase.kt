package com.example.budilnik.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.budilnik.data.models.Alarm

@Database(entities = [Alarm::class], version = 5)
abstract class AlarmDataBase : RoomDatabase() {

    abstract fun getAlarmDao(): AlarmDao

    companion object {
        const val DATABASE_NAME = "db_name"

        fun getInstance(context: Context): AlarmDataBase {
            return Room.databaseBuilder(
                context,
                AlarmDataBase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}