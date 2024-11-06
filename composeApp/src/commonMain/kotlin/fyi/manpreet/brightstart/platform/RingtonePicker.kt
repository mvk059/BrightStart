package fyi.manpreet.brightstart.platform

expect class RingtonePicker {

    fun openRingtonePicker(onRingtoneSelected: (Pair<String?, String?>) -> Unit)

    fun getDefaultRingtone(defaultRingtone: (Pair<String?, String?>) -> Unit)
}