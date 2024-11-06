package fyi.manpreet.brightstart.platform

actual class RingtonePicker {
    actual fun openRingtonePicker(onRingtoneSelected: (Pair<String?, String?>) -> Unit) {}
    actual fun getDefaultRingtone(defaultRingtone: (Pair<String?, String?>) -> Unit) {}
}
