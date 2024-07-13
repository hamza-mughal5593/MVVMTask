package com.hamza.test.ui.feature.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.test.data.model.data.MedApiRemoteSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedCategoriesViewModel @Inject constructor(private val remoteSource: MedApiRemoteSource) :
    ViewModel() {

    var state by mutableStateOf(
        MedContract.State(
            categories = listOf(),
            isLoading = true
        )
    )
        private set

    var effects = Channel<MedContract.Effect>(UNLIMITED)
        private set

    init {
        viewModelScope.launch { getMedCategories() }
    }

    private suspend fun getMedCategories() {
        val categories = remoteSource.getMedCategories()
        viewModelScope.launch {

            state = state.copy(categories = categories, isLoading = false)
            effects.send(MedContract.Effect.DataWasLoaded)
        }
    }
}



