package fyi.manpreet.brightstart

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val sharedViewModel: HomeViewModel by viewModel()

    val ringtoneContract = registerForActivityResult(
        contract = RingtoneContract(this),
        callback = { result ->
            sharedViewModel.updateRingtoneData(result?.first.toString() to result?.second)
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val ringtoneState = sharedViewModel.ringtonePickerState.collectAsStateWithLifecycle()

            LaunchedEffect(ringtoneState.value) {
                println("LaunchedEffect Ringtone state: $ringtoneState")
                if (ringtoneState.value)
                    launchRingtoneActivity()
                if (sharedViewModel.ringtonePickerData.value == null) {
                    val defaultData = getDefaultAlarmRingtone(this@MainActivity)
                    sharedViewModel.updateRingtoneData(defaultData)
                }
            }

            App()

//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFF1e1e26))
//            ) {
//                Weight(modifier = Modifier.fillMaxSize()) { println(it) }
//            }

//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFF1e1e26)),
//                contentAlignment = Alignment.Center
//            ) {
////                Clock(modifier = Modifier.fillMaxSize())
//                NewCircle(modifier = Modifier.fillMaxSize())
////                MuneerCircularProgressBar {
////                    println(it)
////                }
//            }
        }
    }

    fun launchRingtoneActivity() {
        println("launchRingtoneActivity")
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
            if (resultCode != Activity.RESULT_OK) return null

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