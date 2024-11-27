package fyi.manpreet.brightstart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fyi.manpreet.brightstart.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MainActivity : ComponentActivity(), KoinComponent {

    private val sharedViewModel: HomeViewModel by viewModel()

    private val ringtoneContract = registerForActivityResult(
        contract = RingtoneContract(this),
        callback = { result ->
            sharedViewModel.updateRingtoneData(result?.first.toString() to result?.second)
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        get<MainActivityUseCase>().setActivity(this)

        window.apply {
            // Set the status bar color
            statusBarColor = Color.parseColor("#FF1E1E26")

        }
        installSplashScreen()
        setContent {

            val ringtoneState = sharedViewModel.ringtonePickerState.collectAsStateWithLifecycle()

            LaunchedEffect(ringtoneState.value) {
                if (ringtoneState.value)
                    launchRingtoneActivity()
                if (sharedViewModel.ringtonePickerData.value == null) {
                    val defaultData = getDefaultAlarmRingtone(this@MainActivity)
                    sharedViewModel.updateRingtoneData(defaultData)
                }
            }

            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        get<MainActivityUseCase>().setActivity(null)
    }


    fun launchRingtoneActivity() {
        ringtoneContract.launch(RingtoneManager.TYPE_RINGTONE)
    }

    private fun getDefaultAlarmRingtone(context: Context): Pair<String?, String?> {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val name = uri?.let {
            RingtoneManager.getRingtone(context, it)?.getTitle(context)
        }
        return Pair(uri.toString(), name)
    }

    class RingtoneContract(private val context: Context) :
        ActivityResultContract<Int, Pair<Uri?, String?>?>() {

        override fun createIntent(context: Context, input: Int): Intent =
            Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
                putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, input)
                putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
                putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
            }

        override fun parseResult(resultCode: Int, result: Intent?): Pair<Uri?, String?>? {
            if (resultCode != RESULT_OK) return null

            val uri = result?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            val ringtoneName = uri?.let {
                RingtoneManager.getRingtone(context, it)?.getTitle(context)
            }

            return Pair(uri, ringtoneName)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}