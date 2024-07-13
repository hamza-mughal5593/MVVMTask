package com.hamza.test.ui.feature.categories

import com.hamza.test.data.model.data.Drug


class MedContract {

    data class State(
        val categories: List<Drug> = listOf(),
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}