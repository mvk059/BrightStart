package fyi.manpreet.brightstart.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class AlarmTable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "time") val time: String, // Store time in "HH:mm" format
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ringtone_reference") val ringtoneReference: String,
    @ColumnInfo(name = "ringtone_name") val ringtoneName: String,
    @ColumnInfo(name = "volume") val volume: Int,
    @ColumnInfo(name = "vibration_status") val vibrationStatus: Boolean, // True for on, False for off
    @ColumnInfo(name = "is_active") val isActive: Boolean,
    @ColumnInfo(name = "monday") val monday: Boolean = false,
    @ColumnInfo(name = "tuesday") val tuesday: Boolean = false,
    @ColumnInfo(name = "wednesday") val wednesday: Boolean = false,
    @ColumnInfo(name = "thursday") val thursday: Boolean = false,
    @ColumnInfo(name = "friday") val friday: Boolean = false,
    @ColumnInfo(name = "saturday") val saturday: Boolean = false,
    @ColumnInfo(name = "sunday") val sunday: Boolean = false
)
