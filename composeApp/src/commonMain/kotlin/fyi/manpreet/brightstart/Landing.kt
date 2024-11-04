package fyi.manpreet.brightstart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fyi.manpreet.brightstart.navigation.AddAlarmDestination
import fyi.manpreet.brightstart.navigation.HomeDestination

@Composable
fun Landing(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = HomeDestination,
        modifier = modifier,
    ) {

        composable<HomeDestination> {
            HomeScreen(
                onClick = { navController.navigate(AddAlarmDestination) }
            )
        }
        composable<AddAlarmDestination> {
            AddAlarm()
        }
    }
}