package fyi.manpreet.brightstart.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import fyi.manpreet.brightstart.data.model.Alarm
import fyi.manpreet.brightstart.ui.components.empty.EmptyView
import fyi.manpreet.brightstart.ui.home.items.AlarmTopBarItem
import kotlinx.coroutines.flow.StateFlow
import kotlin.Boolean

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    alarms: StateFlow<List<Alarm>>,
    onAlarmClick: (Alarm) -> Unit,
    onAddAlarmClick: () -> Unit,
    onAlarmStatusChange: (HomeEvent) -> Unit,
    onReload: (HomeEvent) -> Unit,
) {

    val shouldReload: State<Boolean>? =
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<Boolean>(
            "reload",
            false
        )?.collectAsStateWithLifecycle()

    LaunchedEffect(shouldReload) {
        if (shouldReload?.value == true) {
            println("OnReload")
            onReload(HomeEvent.FetchAlarms)
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "reload",
                false
            ) // TODO Add constant
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {},
        floatingActionButton = {
            // TODO Check alignment
            Button(
                onClick = { onAddAlarmClick() },
            ) {
                Text("Add Alarm")
            }
        },
    ) {

        Box(
            modifier = modifier.fillMaxSize().background(Color(0xFFf5f5f5))
        ) {
            HomeContent(
                alarms = alarms,
                onAlarmClick = onAlarmClick,
                onAlarmStatusChange = onAlarmStatusChange,
            )
        }
    }

}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    alarms: StateFlow<List<Alarm>>,
    onAlarmClick: (Alarm) -> Unit,
    onAlarmStatusChange: (HomeEvent) -> Unit,
) {

    val alarms = alarms.collectAsStateWithLifecycle()

    Column {
        AlarmTopBarItem()
        if (alarms.value.isEmpty()) {
            EmptyView()
        } else {
            AlarmList(
                alarms = alarms.value,
                onAlarmClick = onAlarmClick,
                onAlarmStatusChange = onAlarmStatusChange,
            )
        }
    }
}
