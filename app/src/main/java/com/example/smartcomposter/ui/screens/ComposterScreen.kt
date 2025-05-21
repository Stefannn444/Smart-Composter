package com.example.smartcomposter.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcomposter.R
import com.example.smartcomposter.data.model.Plant
import com.example.smartcomposter.data.model.plantNeeds
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposterTopAppBar(
    modifier: Modifier=Modifier
) {
    TopAppBar(
        /*colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),*/
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Smart",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painterResource(R.drawable.compostimage),
                    contentDescription = "Composter Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Composter",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    )
}

@Composable
fun ComposterScreen(
    viewModel: SmartComposterViewModel = koinViewModel(),
    modifier: Modifier=Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            GetStatsButton(
                viewModel,
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }

            }

            uiState.error != null -> {
                ErrorScreen(
                    viewModel,
                    uiState,
                    modifier = Modifier.weight(1f)
                )
            }

            uiState.composter != null -> {
                StatsScreen(viewModel, uiState, modifier = Modifier.weight(1f))
            }

            else -> {
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painterResource(R.drawable.compostimage),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .fillMaxWidth(0.8f)

                    )
                }
            }
        }
    }
}

@Composable
fun GetStatsButton(viewModel:SmartComposterViewModel,modifier: Modifier){
    OutlinedButton(
        onClick = {viewModel.fetchStats()},
        modifier=modifier
    ) {
        Text(stringResource(R.string.get_stats_button))
    }
}

@Composable
fun StatsScreen(
    viewModel: SmartComposterViewModel,
    uiState: SmartComposterUiState,
    modifier: Modifier
) {
    Column(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ){
            StatsTable(uiState, Modifier.weight(0.6f))

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painterResource(R.drawable.compostimage),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PlantCompostLazyRow()
    }
}

@Composable
fun StatsTable(
    uiState: SmartComposterUiState,
    modifier: Modifier
){
    Card(
        modifier=modifier
    ){
        Column(
            modifier=Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                stringResource(R.string.stats_table),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(stringResource(R.string.temperature, uiState.composter!!.temperature))
            Text(stringResource(R.string.humidity, uiState.composter!!.humidity))
            Text(stringResource(R.string.air_quality_percentage, uiState.composter!!.airQualityPercentage))
            Text(stringResource(R.string.methane_percentage, uiState.composter!!.methanePercentage))
        }
    }
}

@Composable
fun PlantCompostLazyRow() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(plantNeeds) { plant ->
            PlantCard(plant)
        }
    }
}

@Composable
fun PlantCard(plant: Plant) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = plant.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = plant.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("🌡 Temp: ${plant.idealCompost.temperature} °C")
            Text("💧 Humidity: ${plant.idealCompost.humidity} %")
            Text("🌫 Air Quality (MQ135): ${plant.idealCompost.airQualityPercentage} %")
            Text("🔥 Methane (MQ4): ${plant.idealCompost.methanePercentage} %")
        }
    }
}

@Composable
fun ErrorScreen(
    viewModel: SmartComposterViewModel,
    uiState: SmartComposterUiState,
    modifier: Modifier
){
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (uiState.error != null) {
            Text(
                text = "Error: ${uiState.error}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            Text("An unknown error occurred.", modifier = Modifier.padding(top = 16.dp))
        }
    }
}