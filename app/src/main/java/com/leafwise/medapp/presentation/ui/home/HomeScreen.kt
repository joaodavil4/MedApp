package com.leafwise.medapp.presentation.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.framework.db.entity.MedicationEntity
import com.leafwise.medapp.presentation.ui.components.LoadingIndicator
import com.leafwise.medapp.presentation.ui.components.MedItem


@Suppress("UnusedParameter")
@Composable
fun HomeScreen(
    uiState: HomeViewModel.HomeUiState,
    onNavigateClick: (source: String) -> Unit
) {
    Scaffold(
        floatingActionButton = { MedicationAddButton() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {

                HomeHeader()
                when (uiState) {
                    is HomeViewModel.HomeUiState.Loading -> {
                        LoadingIndicator(modifier = Modifier.fillMaxSize())
                    }

                    is HomeViewModel.HomeUiState.Success -> {
                        HomeContent(listOf())
                    }

                    is HomeViewModel.HomeUiState.Empty -> {
                        EmptyView()
                    }

                    is HomeViewModel.HomeUiState.Error -> {

                    }
                }

            }

        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_pill),
            contentDescription = "appIcon",
        )

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HomeContent(content: List<MedicationEntity>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ){
        items(content.size){
            MedItem()
        }
    }
}


@Composable
fun EmptyView() {
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(
            text = stringResource(id = R.string.home_empty_view),
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
    }
}

@Preview
@Composable
fun HomeEmpty() {
    HomeScreen(
        HomeViewModel.HomeUiState.Empty,
        {}
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkHome() {
    HomeScreen(
        HomeViewModel.HomeUiState.Empty,
        {}
    )
}

@Preview
@Composable
fun HomeSuccess() {
    HomeScreen(
        HomeViewModel.HomeUiState.Success(
            listOf(MedicationEntity(1), MedicationEntity(2))
        ),
        {}
    )
}

@Preview
@Composable
fun HomeLoading() {
    HomeScreen(
        HomeViewModel.HomeUiState.Loading,
        {}
    )
}