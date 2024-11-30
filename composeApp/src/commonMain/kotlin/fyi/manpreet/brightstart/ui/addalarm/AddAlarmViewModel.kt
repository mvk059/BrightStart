package fyi.manpreet.brightstart.ui.addalarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import fyi.manpreet.brightstart.data.mapper.calculateTimeBetweenWithText
import fyi.manpreet.brightstart.data.mapper.constructRepeatDays
import fyi.manpreet.brightstart.data.mapper.formatLocalDateTimeToHHMM
import fyi.manpreet.brightstart.data.mapper.getIconForTime
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.data.model.AlarmActive
import fyi.manpreet.brightstart.data.model.AlarmDaySelected
import fyi.manpreet.brightstart.data.model.AlarmDayTitle
import fyi.manpreet.brightstart.data.model.AlarmName
import fyi.manpreet.brightstart.data.model.AlarmTime
import fyi.manpreet.brightstart.data.model.DaysEnum
import fyi.manpreet.brightstart.data.model.RingtoneName
import fyi.manpreet.brightstart.data.model.RingtoneReference
import fyi.manpreet.brightstart.data.model.VibrationStatus
import fyi.manpreet.brightstart.data.model.Volume
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import fyi.manpreet.brightstart.platform.permission.Permission
import fyi.manpreet.brightstart.platform.permission.PermissionState
import fyi.manpreet.brightstart.platform.permission.service.PermissionService
import fyi.manpreet.brightstart.platform.scheduler.AlarmScheduler
import fyi.manpreet.brightstart.ui.model.AlarmConstants
import fyi.manpreet.brightstart.ui.model.Hour
import fyi.manpreet.brightstart.ui.model.Minute
import fyi.manpreet.brightstart.ui.model.TimePeriod
import fyi.manpreet.brightstart.ui.model.TimePeriodValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class AddAlarmViewModel(
    private val alarmScheduler: AlarmScheduler,
    private val permissionService: PermissionService,
    private val repository: AlarmRepository,
) : ViewModel() {

    private val _currentAlarm = MutableStateFlow(initCurrentAlarm())
    val currentAlarm: StateFlow<Alarm> = _currentAlarm.asStateFlow()

    private val _onAlarmAdded = MutableStateFlow(false)
    val onAlarmAdded: StateFlow<Boolean> = _onAlarmAdded.asStateFlow()

    private val _permissionStatus = MutableStateFlow(PermissionState.NOT_DETERMINED)
    val permissionStatus: StateFlow<PermissionState> = _permissionStatus.asStateFlow()

    private var toUpdateAlarm: Alarm? = null

    override fun onCleared() {
        super.onCleared()
        _currentAlarm.update { initCurrentAlarm() }
        toUpdateAlarm = null
        _onAlarmAdded.update { false }
    }

    // TODO Check update alarm. values are not set correctly
    fun updateCurrentAlarm(alarmId: Long?) {
        if (alarmId == null) return
        if (toUpdateAlarm != null) return
        viewModelScope.launch {
            val alarm = repository.fetchAlarmById(alarmId) ?: return@launch
            val timeLeftForAlarm = calculateTimeBetweenWithText(
                selectedDateTime = alarm.localTime.toInstant(TimeZone.currentSystemDefault()),
                alarmDays = alarm.alarmDays,
            )
            toUpdateAlarm = alarm.copy(timeLeftForAlarm = timeLeftForAlarm)
            _currentAlarm.update { alarm.copy(timeLeftForAlarm = timeLeftForAlarm) }
        }
    }

    fun onEvent(event: AddAlarmEvent) {
        when (event) {
            AddAlarmEvent.AddAlarm -> { viewModelScope.launch { checkPermission() } }
            is AddAlarmEvent.SoundUpdate -> onSoundUpdate(event.data)
            is AddAlarmEvent.VolumeUpdate -> onVolumeUpdate(event.volume)
            is AddAlarmEvent.VibrateUpdate -> onVibrateUpdate(event.vibrationStatus)
            is AddAlarmEvent.NameUpdate -> onAlarmNameUpdate(event.name)
            is AddAlarmEvent.RepeatUpdate -> onRepeatItemClick(event.item)
            is AddAlarmEvent.OpenSettings -> openSettingsPage(event.type)
            is AddAlarmEvent.DismissPermissionDialog -> onPermissionDialogDismiss()
        }
    }

    fun onHourIndexUpdate(hour: Int) {
        val time = _currentAlarm.value.time.copy(hour = Hour(hour))
        _currentAlarm.update { it.copy(time = time) }
        onTimeScrollingUpdate()
    }

    fun onMinuteIndexUpdate(minute: Int) {
        val time = _currentAlarm.value.time.copy(minute = Minute(minute))
        _currentAlarm.update { it.copy(time = time) }
        onTimeScrollingUpdate()
    }

    fun onTimePeriodIndexUpdate(timePeriod: TimePeriodValue) {
        _currentAlarm.update { it.copy(timePeriod = TimePeriod(timePeriod)) }
        onTimeScrollingUpdate()
    }

    private fun onTimeScrollingUpdate() {
        onTimeSelectionUpdate(
            hour = _currentAlarm.value.time.hour,
            minutes = _currentAlarm.value.time.minute,
            timePeriod = _currentAlarm.value.timePeriod
        )
    }

    private fun initCurrentAlarm(): Alarm {
        val hour = 3
        val minute = 45
        val time = "0$hour:$minute"
        val localDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val localTime = LocalTime(hour = hour, minute = minute)
        val localDateTime = LocalDateTime(localDate.date, localTime)
        val alarmDays = initAlarmDays()

        return Alarm(
            localTime = localDateTime,
            time = AlarmTime(time, Hour(hour), Minute(minute)), // TODO Default time
            name = AlarmName("Alarm"), // TODO Get text from strings or use default while adding alarm
            timePeriod = TimePeriod(TimePeriodValue.AM),
            ringtoneReference = RingtoneReference(""),
            ringtoneName = RingtoneName(""),
            volume = Volume(AlarmConstants.VOLUME), // TODO Should set default volume of phone
            vibrationStatus = VibrationStatus(AlarmConstants.VIBRATION_STATUS),  // TODO Should set default vibration status of phone
            alarmDays = alarmDays,
            repeatDays = alarmDays.constructRepeatDays(localDateTime),
            isActive = AlarmActive(AlarmConstants.IS_ACTIVE),
            timeLeftForAlarm = calculateTimeBetweenWithText(
                selectedDateTime = localDateTime.toInstant(TimeZone.currentSystemDefault()),
                systemDateTime = Clock.System.now(),
                alarmDays = alarmDays,
            ),
            icon = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                .getIconForTime()
        )
    }

    // TODO Get text from strings
    private fun initAlarmDays() =
        buildList {
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.MONDAY,
                    day = AlarmDayTitle("Mon"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.TUESDAY,
                    day = AlarmDayTitle("Tue"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.WEDNESDAY,
                    day = AlarmDayTitle("Wed"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.THURSDAY,
                    day = AlarmDayTitle("Thu"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.FRIDAY,
                    day = AlarmDayTitle("Fri"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.SATURDAY,
                    day = AlarmDayTitle("Sat"),
                    isSelected = AlarmDaySelected(false)
                )
            )
            add(
                Alarm.AlarmDays(
                    id = DaysEnum.SUNDAY,
                    day = AlarmDayTitle("Sun"),
                    isSelected = AlarmDaySelected(false)
                )
            )
        }

    private suspend fun checkPermission() {
        val permissionState = permissionService.checkPermission(Permission.NOTIFICATION)
        Logger.i(PERMISSION_TAG) { "Permission state: $permissionState" }

        when (permissionState) {
            PermissionState.NOT_DETERMINED -> provideNotificationsPermission()
            PermissionState.DENIED -> _permissionStatus.update { PermissionState.DENIED }
            PermissionState.GRANTED -> addAlarm()
        }
    }

    private suspend fun provideNotificationsPermission() {
        permissionService.providePermission(Permission.NOTIFICATION)
        checkPermission()
    }

    private fun openSettingsPage(permission: Permission) {
        permissionService.openSettingsPage(permission)
    }

    private fun addAlarm() {
        Logger.d("Alarm addAlarm")
        val currentAlarm = _currentAlarm.value

        val time = AlarmTime(
            value = currentAlarm.localTime.formatLocalDateTimeToHHMM(),
            hour = Hour(currentAlarm.localTime.hour),
            minute = Minute(currentAlarm.localTime.minute)
        )
        val name = AlarmName(currentAlarm.name.value.ifEmpty { AlarmName("New Alarm") }
            .toString()) // TODO Get text from strings
        val ringtoneReference = currentAlarm.ringtoneReference
        val ringtoneName = currentAlarm.ringtoneName

        require(ringtoneReference.value.isNotEmpty()) { "Ringtone Reference is empty" }
        require(ringtoneName.value.isNotEmpty()) { "Ringtone Reference is empty" }

        val alarm = Alarm(
            id = currentAlarm.id,
            localTime = currentAlarm.localTime,
            time = time,
            name = name,
            timePeriod = currentAlarm.timePeriod,
            ringtoneReference = ringtoneReference,
            ringtoneName = ringtoneName,
            volume = currentAlarm.volume,
            vibrationStatus = currentAlarm.vibrationStatus,
            alarmDays = currentAlarm.alarmDays,
            repeatDays = currentAlarm.alarmDays.constructRepeatDays(currentAlarm.localTime),
            isActive = AlarmActive(true),
            timeLeftForAlarm = "",
            icon = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                .getIconForTime()
        )

        Logger.d("Alarm schedule start")
        viewModelScope.launch {
            if (_currentAlarm.value.id == Alarm.INVALID_ID) {
                // Add Alarm
                val id = repository.insertAlarm(alarm)
                println("Add Alarm id: $id")
                alarmScheduler.schedule(alarm.copy(id = id))
            } else {
                // Update Alarm
                repository.updateAlarm(alarm)
                println("Update Alarm id: ${alarm.id}, ${toUpdateAlarm?.id}")
                alarmScheduler.cancel(toUpdateAlarm!!)
                alarmScheduler.schedule(alarm)
            }

        }

        _onAlarmAdded.update { true }
    }

    private fun onSoundUpdate(data: Pair<String?, String?>) {
        val ringtoneReference = data.first
        val ringtoneName = data.second
        if (ringtoneReference == null || ringtoneName == null) return

        _currentAlarm.update {
            it.copy(
                ringtoneReference = RingtoneReference(ringtoneReference),
                ringtoneName = RingtoneName(ringtoneName)
            )
        }
    }

    private fun onVolumeUpdate(volume: Int) {
        _currentAlarm.update { it.copy(volume = Volume(volume)) }
    }

    private fun onVibrateUpdate(vibrate: Boolean) {
        _currentAlarm.update { it.copy(vibrationStatus = VibrationStatus(vibrate)) }
    }

    private fun onAlarmNameUpdate(name: String) {
        _currentAlarm.update { it.copy(name = AlarmName(name)) }
    }

    private fun onRepeatItemClick(newItem: Alarm.AlarmDays) {
        val alarmDays = _currentAlarm.value.alarmDays.map { oldItem ->
            if (oldItem == newItem) oldItem.copy(isSelected = AlarmDaySelected(!oldItem.isSelected.value))
            else oldItem
        }
        _currentAlarm.update {
            it.copy(
                alarmDays = alarmDays,
                repeatDays = alarmDays.constructRepeatDays(_currentAlarm.value.localTime)
            )
        }

        val timeLeftForAlarm = calculateTimeBetweenWithText(
            selectedDateTime = _currentAlarm.value.localTime.toInstant(TimeZone.currentSystemDefault()),
            systemDateTime = Clock.System.now(),
            alarmDays = _currentAlarm.value.alarmDays
        )
        _currentAlarm.update { it.copy(timeLeftForAlarm = timeLeftForAlarm) }
    }

    private fun onPermissionDialogDismiss() {
        _permissionStatus.update { PermissionState.NOT_DETERMINED }
    }

    private fun onTimeSelectionUpdate(hour: Hour, minutes: Minute, timePeriod: TimePeriod) {
        // Get system date time
        val systemLocalDateTime =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        // Convert to instant with current time zone
        val systemDateTimeInstant: Instant =
            systemLocalDateTime.toInstant(TimeZone.currentSystemDefault())

        // Get today's date
        val todayLocalDate = systemLocalDateTime.date
        // Create selected LocalTime
        val selectedHour = when {
            hour.value == 12 && timePeriod.value == TimePeriodValue.AM -> 0 // 12 AM
            hour.value == 12 && timePeriod.value == TimePeriodValue.PM -> 12 // 12 PM
            timePeriod.value == TimePeriodValue.PM -> hour.value + 12
            else -> hour.value
        }
        val selectedLocalTime = LocalTime(hour = selectedHour, minute = minutes.value)
        // Create selected LocalDateTime
        var selectedLocalDateTime = todayLocalDate.atTime(selectedLocalTime)
        // Create selected Instant with default time zone. This will be converted to UTC time zone
        var selectedLocalDateTimeInstant =
            selectedLocalDateTime.toInstant(TimeZone.currentSystemDefault())

        // Compare to see if selected time is less than current time. If so, add a day to schedule the alarm for the next day
        if (selectedLocalTime < systemLocalDateTime.time) {
            // If selected time is less than current time, set it for next day
            val updatedLocalDate = todayLocalDate.plus(DatePeriod(days = 1))
            selectedLocalDateTime = updatedLocalDate.atTime(selectedLocalTime)
            selectedLocalDateTimeInstant =
                selectedLocalDateTime.toInstant(TimeZone.currentSystemDefault())
        }

        // Create formatted string for time left for alarm
        val timeLeftForAlarm = calculateTimeBetweenWithText(
            selectedDateTime = selectedLocalDateTimeInstant,
            systemDateTime = systemDateTimeInstant,
            alarmDays = _currentAlarm.value.alarmDays
        )
        val timeValue = _currentAlarm.value.time.copy(
            value = selectedLocalDateTime.formatLocalDateTimeToHHMM()
        )
        _currentAlarm.update {
            it.copy(
                localTime = selectedLocalDateTime,
                timePeriod = timePeriod,
                timeLeftForAlarm = timeLeftForAlarm,
                time = timeValue,
            )
        }
    }

    companion object {
        private const val PERMISSION_TAG = "Permissions"
    }
}
