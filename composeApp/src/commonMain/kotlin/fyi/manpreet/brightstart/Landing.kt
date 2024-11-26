package fyi.manpreet.brightstart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fyi.manpreet.brightstart.navigation.AddAlarmDestination
import fyi.manpreet.brightstart.navigation.HomeDestination
import fyi.manpreet.brightstart.platform.permission.PermissionState
import fyi.manpreet.brightstart.ui.addalarm.AddAlarm
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmEvent
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmViewModel
import fyi.manpreet.brightstart.ui.components.permission.PermissionDeniedDialog
import fyi.manpreet.brightstart.ui.home.HomeScreen
import fyi.manpreet.brightstart.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Landing(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val ringtoneData = homeViewModel.ringtonePickerData.collectAsStateWithLifecycle()

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
                    navController.navigate(AddAlarmDestination())
                },
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

            val onAlarmAdded = viewModel.onAlarmAdded.collectAsStateWithLifecycle()
            val permissionStatus = viewModel.permissionStatus.collectAsStateWithLifecycle()

            LaunchedEffect(ringtoneData.value) {
                val data = ringtoneData.value
                if (data != null) {
                    viewModel.onEvent(AddAlarmEvent.SoundUpdate(data))
                }
            }

            LaunchedEffect(onAlarmAdded.value) {
                if (onAlarmAdded.value) {
                    navController.previousBackStackEntry?.savedStateHandle?.set("reload", true)
                    navController.popBackStack()
                }
            }

            AddAlarm(
                alarm = viewModel.currentAlarm,
                alarmTimeSelector = viewModel.timeSelector,
                onHourIndexUpdate = viewModel::onHourIndexUpdate,
                onMinuteIndexUpdate = viewModel::onMinuteIndexUpdate,
                onTimePeriodIndexUpdate = viewModel::onTimePeriodIndexUpdate,
                onTimeScrollingUpdate = viewModel::onTimeScrollingUpdate,
                onVolumeUpdate = viewModel::onEvent,
                onVibrateUpdate = viewModel::onEvent,
                onNameUpdate = viewModel::onEvent,
                onRepeatUpdate = viewModel::onEvent,
                openRingtonePicker = { homeViewModel.updateRingtoneState(true) },
                onAddClick = {
                    viewModel.onEvent(AddAlarmEvent.AddAlarm)
                },
                onCloseClick = { navController.popBackStack() }, // TODO Check what's the issue with this
            )

            if (permissionStatus.value == PermissionState.DENIED) {
                PermissionDeniedDialog(
                    onSettingsClick = viewModel::onEvent,
                    onDismissRequest = viewModel::onEvent,
                )
            }

        }
    }
}
