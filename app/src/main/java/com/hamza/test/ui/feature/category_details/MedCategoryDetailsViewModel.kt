package com.hamza.test.ui.feature.category_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

import com.hamza.test.data.model.data.MedApiRemoteSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MedCategoryDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val repository: MedApiRemoteSource
) : ViewModel() {

    var state by mutableStateOf(
        MedCategoryDetailsContract.State(
            null, listOf(
            )
        )
    )
}
