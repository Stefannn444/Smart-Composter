package com.example.smartcomposter.ui.screens
import android.util.Log // <-- ADD THIS IMPORT
import androidx.compose.ui.res.stringResource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcomposter.R
import com.example.smartcomposter.data.model.Composter
import com.example.smartcomposter.data.network.ComposterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.io.IOException

data class SmartComposterUiState(
    val isLoading:Boolean=false,
    val composter: Composter?=null,
    val error:String?=null
)

private const val TAG = "ComposterAppDebug"


class SmartComposterViewModel(
    private val repository: ComposterRepository
):ViewModel() {
    private val _uiState= MutableStateFlow(SmartComposterUiState())
    val uiState: StateFlow<SmartComposterUiState> = _uiState.asStateFlow()
    fun fetchStats(){
        viewModelScope.launch{
            Log.d(TAG,"launched fetch viewmodel")
            _uiState.update{currentState-> currentState.copy(isLoading=true,error=null)}
            try{
                val composter=repository.getStats()
                _uiState.update{currentState-> currentState.copy(isLoading=false,composter=composter)}
                Log.d(TAG,"GOOD STATS YAYTY ")

            }catch(e:IOException){
                _uiState.update{currentState-> currentState.copy(isLoading=false,error="IO ERROR")}
                Log.e(TAG,"IO ERROR: ${e.message}", e) // Log the specific IO error message and stack trace

            }catch(e:Exception){
                _uiState.update{currentState-> currentState.copy(isLoading = false, error= "ALTA EROAREE")}
                Log.e(TAG,"ANOTHER ERROR: ${e.message}", e) // Log the specific error message and stack trace

            }
        }
        Log.d(TAG,"FETCHED ")

    }
}