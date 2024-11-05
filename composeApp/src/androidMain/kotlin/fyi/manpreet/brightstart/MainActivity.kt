package fyi.manpreet.brightstart

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import fyi.manpreet.brightstart.di.initKoin
import fyi.manpreet.brightstart.platform.RingtoneContract

class MainActivity : ComponentActivity() {

    val ringtoneContract = registerForActivityResult(
        contract = RingtoneContract(this),
        callback = { result ->
            println("Ringtone in secondActivity: $result")
            ringtoneCallback(result)
        },
    )

    lateinit var ringtoneCallback: (Pair<Uri?, String?>?) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        initKoin(this)

        setContent {
            App()
        }
    }

    fun launchRingtoneActivity() {
        ringtoneContract.launch(RingtoneManager.TYPE_RINGTONE)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}