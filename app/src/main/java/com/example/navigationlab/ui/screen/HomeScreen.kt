package com.example.navigationlab.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navigationlab.data.model.Student
import com.example.navigationlab.ui.home.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onStudentClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("NavigationLab", style = MaterialTheme.typography.headlineMedium)
        Text("Daftar Mahasiswa", style = MaterialTheme.typography.titleLarge)

        when {
            uiState.isLoading -> Text("Memuat daftar mahasiswa...")
            uiState.errorMessage != null -> Text(uiState.errorMessage)
            uiState.students.isEmpty() -> Text("Belum ada data mahasiswa.")
            else -> LazyColumn(
                modifier = Modifier.weight(1f, fill = true),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.students, key = { it.id }) { student ->
                    StudentRow(student = student, onClick = { onStudentClick(student.id) })
                }
            }
        }

        Button(onClick = onProfileClick, modifier = Modifier.fillMaxWidth()) {
            Text("Profile")
        }
        Button(onClick = onAboutClick, modifier = Modifier.fillMaxWidth()) {
            Text("About")
        }
    }
}

@Composable
private fun StudentRow(student: Student, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("ID: ${student.id}", style = MaterialTheme.typography.labelLarge)
            Text("Nama: ${student.name}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
