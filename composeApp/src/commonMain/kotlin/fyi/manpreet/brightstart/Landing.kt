package fyi.manpreet.brightstart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fyi.manpreet.brightstart.navigation.AddAlarmDestination
import fyi.manpreet.brightstart.navigation.HomeDestination
import fyi.manpreet.brightstart.ui.addalarm.AddAlarm
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmViewModel
import fyi.manpreet.brightstart.ui.home.HomeEvent
import fyi.manpreet.brightstart.ui.home.HomeScreen
import fyi.manpreet.brightstart.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Landing(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = koinViewModel(),
) {

    NavHost(
        navController = navController,
        startDestination = HomeDestination,
        modifier = modifier,
    ) {

        composable<HomeDestination> {
            HomeScreen(
                alarms = viewModel.alarms,
                onClick = { navController.navigate(AddAlarmDestination) },
                onAddAlarmClick = viewModel::onEvent
            )
        }
        composable<AddAlarmDestination> {
            // TODO Tie viewmodel to the scope of AddAlarm

            val viewModel = koinViewModel<AddAlarmViewModel>()

            AddAlarm(
                alarmDaysItem = viewModel.alarmDaysItem,
                repeatDays = viewModel.repeatDays,
                onSoundUpdate = viewModel::onEvent,
                onVolumeUpdate = viewModel::onEvent,
                onVibrateUpdate = viewModel::onEvent,
                onNameUpdate = viewModel::onEvent,
                onRepeatUpdate = viewModel::onEvent,
                onAddClick = {
                    viewModel.onEvent(AddAlarmEvent.AddAlarm)
                    navController.popBackStack()
                },
                onCloseClick = { navController.popBackStack() }, // TODO Check what's the issue with this
            )
        }
    }
}
