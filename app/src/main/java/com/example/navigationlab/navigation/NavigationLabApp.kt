package com.example.navigationlab.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationlab.data.repository.DefaultStudentRepository
import com.example.navigationlab.data.source.StudentLocalDataSource
import com.example.navigationlab.ui.detail.DetailViewModel
import com.example.navigationlab.ui.home.HomeViewModel
import com.example.navigationlab.ui.screen.DetailScreen
import com.example.navigationlab.ui.screen.HomeScreen

private object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{studentId}"
    const val PROFILE = "profile"
    const val ABOUT = "about"

    fun detail(studentId: Int) = "detail/$studentId"
}

@Composable
fun NavigationLabApp() {
    val dataSource = remember { StudentLocalDataSource() }
    val repository = remember { DefaultStudentRepository(dataSource) }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.factory(repository)
            )
            HomeScreen(
                uiState = homeViewModel.uiState,
                onStudentClick = { studentId -> navController.navigate(Routes.detail(studentId)) },
                onProfileClick = { navController.navigate(Routes.PROFILE) },
                onAboutClick = { navController.navigate(Routes.ABOUT) }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: -1
            val detailViewModel: DetailViewModel = viewModel(
                key = "detail-$studentId",
                factory = DetailViewModel.factory(repository)
            )
            LaunchedEffect(studentId) {
                detailViewModel.loadStudent(studentId)
            }
            DetailScreen(
                uiState = detailViewModel.uiState,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PROFILE) {
            SimplePage(
                title = "Profile",
                description = "Halaman Profile",
                buttons = listOf("Kembali" to { navController.popBackStack() })
            )
        }

        composable(Routes.ABOUT) {
            SimplePage(
                title = "About",
                description = "NavigationLab - Praktikum Pemrograman Aplikasi Bergerak Modul 06.",
                buttons = listOf("Kembali" to { navController.popBackStack() })
            )
        }
    }
}

@Composable
private fun SimplePage(
    title: String,
    description: String,
    buttons: List<Pair<String, () -> Unit>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Text(description)
        buttons.forEach { (label, action) ->
            Button(onClick = action) {
                Text(label)
            }
        }
    }
}
