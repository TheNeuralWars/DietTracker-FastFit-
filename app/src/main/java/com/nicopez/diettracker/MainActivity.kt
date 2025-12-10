package com.nicopez.diettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicopez.diettracker.ui.screens.FastingScreen
import com.nicopez.diettracker.ui.screens.MealScreen
import com.nicopez.diettracker.ui.screens.TrendsScreen
import com.nicopez.diettracker.ui.screens.WeightScreen
import com.nicopez.diettracker.ui.theme.DietTrackerTheme
import com.nicopez.diettracker.ui.viewmodel.FastingViewModel
import com.nicopez.diettracker.ui.viewmodel.MealViewModel
import com.nicopez.diettracker.ui.viewmodel.WeightViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietTrackerTheme {
                DietTrackerApp()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Meals : Screen("meals", "Meals", Icons.Default.Restaurant)
    object Fasting : Screen("fasting", "Fasting", Icons.Default.Timer)
    object Weight : Screen("weight", "Weight", Icons.Default.MonitorWeight)
    object Trends : Screen("trends", "Trends", Icons.Default.TrendingUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietTrackerApp() {
    val navController = rememberNavController()
    val repository = (LocalContext.current.applicationContext as DietTrackerApplication).repository
    
    val mealViewModel: MealViewModel = viewModel(
        factory = MealViewModel.provideFactory(repository)
    )
    val fastingViewModel: FastingViewModel = viewModel(
        factory = FastingViewModel.provideFactory(repository)
    )
    val weightViewModel: WeightViewModel = viewModel(
        factory = WeightViewModel.provideFactory(repository)
    )
    
    val screens = listOf(
        Screen.Meals,
        Screen.Fasting,
        Screen.Weight,
        Screen.Trends
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FastFit") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Meals.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Meals.route) {
                MealScreen(mealViewModel)
            }
            composable(Screen.Fasting.route) {
                FastingScreen(fastingViewModel)
            }
            composable(Screen.Weight.route) {
                WeightScreen(weightViewModel)
            }
            composable(Screen.Trends.route) {
                TrendsScreen(mealViewModel, fastingViewModel, weightViewModel)
            }
        }
    }
}
