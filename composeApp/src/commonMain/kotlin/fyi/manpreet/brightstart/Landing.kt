package fyi.manpreet.brightstart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fyi.manpreet.brightstart.navigation.AddAlarmDestination
import fyi.manpreet.brightstart.navigation.HomeDestination
import fyi.manpreet.brightstart.ui.addalarm.AddAlarm
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmViewModel
import fyi.manpreet.brightstart.ui.home.HomeScreen
import fyi.manpreet.brightstart.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Landing(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = koinViewModel(),
) {

    NavHost(
        navController = navController,
        startDestination = HomeDestination,
        modifier = modifier,
    ) {

        composable<HomeDestination> {
            HomeScreen(
                alarms = homeViewModel.alarms,
                navController = navController,
                onAddAlarmClick = {
                    navController.navigate(AddAlarmDestination()) },
                onAlarmClick = {
                    navController.navigate(AddAlarmDestination(it.id)) // TODO Pass current alarm to edit
                },
                onAlarmStatusChange = homeViewModel::onEvent,
                onReload = homeViewModel::onEvent,
            )
        }
        composable<AddAlarmDestination> {
            // TODO Setup autostart to keep the scheduled alarms running after restart

            val viewModel = koinViewModel<AddAlarmViewModel>()
            val args = it.toRoute<AddAlarmDestination>()
            viewModel.updateCurrentAlarm(args.alarmId)

            AddAlarm(
                alarm = viewModel.currentAlarm,
                repeatDays = viewModel.repeatDays,
                onSoundUpdate = viewModel::onEvent,
                onVolumeUpdate = viewModel::onEvent,
                onVibrateUpdate = viewModel::onEvent,
                onNameUpdate = viewModel::onEvent,
                onRepeatUpdate = viewModel::onEvent,
                onAddClick = {
                    viewModel.onEvent(AddAlarmEvent.AddAlarm)
                    navController.previousBackStackEntry?.savedStateHandle?.set("reload", true)
                    navController.popBackStack()
                },
                onCloseClick = { navController.popBackStack() }, // TODO Check what's the issue with this
            )
        }
    }
}
