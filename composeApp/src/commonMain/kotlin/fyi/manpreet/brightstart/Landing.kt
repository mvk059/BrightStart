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
            AddAlarm(
                alarmDaysItem = viewModel.alarmDaysItem,
                repeatDays = viewModel.repeatDays,
                onRepeatItemClick = viewModel::onRepeatItemClick
            )
        }
    }
}
