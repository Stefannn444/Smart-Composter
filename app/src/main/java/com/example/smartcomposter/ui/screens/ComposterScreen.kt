package com.example.smartcomposter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Text(R.string.get_stats_button.toString())
    }
}

@Composable
fun StatsScreen(
    viewModel:SmartComposterViewModel,
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
            modifier=Modifier.fillMaxWidth(),
        ){

        }
    }
}

@Composable
fun StatsTable(
    uiState: SmartComposterUiState,
    modifier: Modifier
){

}