package fyi.manpreet.brightstart.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onClick: () -> Unit,
) {

    val alarms = viewModel.alarms.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        alarms.value.forEach { alarm ->
            Text(
                modifier = Modifier.clickable { onClick() },
                text = "${alarm.time} : ${alarm.name}\n${alarm.alarmDays}"
            )

            Spacer(modifier = Modifier.padding(16.dp))

        }

        Text(
            modifier = Modifier.clickable { viewModel.onEvent(HomeEvent.AddAlarm) },
            text = "Add Alarm"
        )
    }
}
