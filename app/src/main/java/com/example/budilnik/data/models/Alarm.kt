package com.example.budilnik.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var time: String, //22:45
    var isActivate: Boolean, //switch
    var isMondayActivated: Boolean = false,
    var isTuesdayActivated: Boolean = false,
    var isWednesdayActivated: Boolean = false,
    var isThursdayAcivated: Boolean = false,
    var isFridayActivated: Boolean = false,
    var isSaturdayActivated: Boolean = false,
    var isSundayActivated: Boolean = false,
    var comment: String = ""
)
