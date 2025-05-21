package com.example.smartcomposter.ui.screens

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

class SmartComposterViewModel(
    private val repository: ComposterRepository
):ViewModel() {
    private val _uiState= MutableStateFlow(SmartComposterUiState())
    val uiState: StateFlow<SmartComposterUiState> = _uiState.asStateFlow()

    fun fetchStats(){
        viewModelScope.launch{
            _uiState.update{currentState-> currentState.copy(isLoading=true,error=null)}
            try{
                val composter=repository.getStats()
                _uiState.update{currentState-> currentState.copy(isLoading=false,composter=composter)}
            }catch(e:IOException){
                _uiState.update{currentState-> currentState.copy(isLoading=false,error=R.string.io_error.toString())}
            }catch(e:Exception){
                _uiState.update{currentState-> currentState.copy(isLoading = false, error= R.string.unexpected_error.toString())}
            }
        }
    }
}