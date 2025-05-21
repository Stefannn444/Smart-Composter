package com.example.smartcomposter.ui.screens

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun ComposterScreen(
    viewModel: SmartComposterViewModel= koinViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier=Modifier
                .fillMaxSize()
                .padding(8.dp)
        ){
            when{
                uiState.isLoading->{
                    CircularProgressIndicator()
                }
                uiState.error!=null->{

                }
                uiState.composter!=null->{

                }
                else->{

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
        LazyRow() {

        }
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