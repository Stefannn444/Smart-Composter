package com.example.smartcomposter.ui.screens
import android.content.ContentValues.TAG
import android.util.Log // <-- ADD THIS IMPORT

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcomposter.R
import com.example.smartcomposter.data.model.Plant
import com.example.smartcomposter.data.model.plantNeeds
import org.koin.androidx.compose.koinViewModel


private const val TAG = "ComposterAppDebug"

@Composable
fun ComposterScreen(
    viewModel: SmartComposterViewModel= koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.error != null -> {
                Log.d(TAG,"Error Screen!")
                ErrorScreen(
                    viewModel,
                    uiState,
                    modifier = Modifier
                )
            }

            uiState.composter != null -> {
                Log.d(TAG,"Stats Screen!")

                StatsScreen(viewModel, uiState, modifier = Modifier)
            }

            else -> {
                Log.d(TAG,"default Screen!")

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        GetStatsButton(
                            viewModel,
                            modifier = Modifier
                        )
                    }
                    Image(painterResource(R.drawable.compostimage), contentDescription = null)
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
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            GetStatsButton(
                viewModel,
                modifier = Modifier
            )
        }
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .weight(1f),
        ){
            StatsTable(uiState,modifier)
            Image(
                painterResource(R.drawable.compostimage),
                contentDescription=null
                )
        }
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
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                stringResource(R.string.stats_table),
                fontSize = 24.sp
                )
            Text(stringResource(R.string.temperature,uiState.composter!!.temperature))
            Text(stringResource(R.string.humidity,uiState.composter!!.humidity))
            Text(stringResource(R.string.air_quality_percentage,uiState.composter!!.airQualityPercentage))
            Text(stringResource(R.string.methane_percentage,uiState.composter!!.methanePercentage))
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
    Column{
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            GetStatsButton(
                viewModel,
                modifier = Modifier
            )
        }
    }
}