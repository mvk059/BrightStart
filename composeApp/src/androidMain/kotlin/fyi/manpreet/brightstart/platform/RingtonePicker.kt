package fyi.manpreet.brightstart.platform

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import fyi.manpreet.brightstart.MainActivity

actual class RingtonePicker(
    private val activity: Activity
) {

    actual fun openRingtonePicker(onRingtoneSelected: (Pair<String?, String?>) -> Unit) {

        fun callback(ringtoneDetails: Pair<Uri?, String?>?) {
            println("Ringtone in hof: $ringtoneDetails")
            if (ringtoneDetails == null) {
                onRingtoneSelected("" to "")
                return
            }
            onRingtoneSelected(ringtoneDetails.first.toString() to ringtoneDetails.second)
        }

        (activity as MainActivity).ringtoneCallback = ::callback
        activity.launchRingtoneActivity()
    }
}

class RingtoneContract(private val context: Context) : ActivityResultContract<Int, Pair<Uri?, String?>?>() {

    override fun createIntent(context: Context, input: Int): Intent =
        Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, input)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
        }

    override fun parseResult(resultCode: Int, result: Intent?): Pair<Uri?, String?>? {
        if (resultCode != Activity.RESULT_OK) return null

        val uri = result?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
        val ringtoneName = uri?.let {
            RingtoneManager.getRingtone(context, it)?.getTitle(context)
        }

        return Pair(uri, ringtoneName)
    }
}
