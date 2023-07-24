package com.leafwise.medapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.leafwise.medapp.framework.db.entity.MedicationEntity

class HomeViewModel : ViewModel() {

    sealed class HomeUiState {
        object Empty : HomeUiState()
        object Loading : HomeUiState()
        class Success(val data: List<MedicationEntity>) : HomeUiState()
        class Error(val message: String) : HomeUiState()
    }
}