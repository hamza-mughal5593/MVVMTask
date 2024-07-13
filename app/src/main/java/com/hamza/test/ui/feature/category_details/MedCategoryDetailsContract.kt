package com.hamza.test.ui.feature.category_details


import com.hamza.test.data.model.data.Drug


class MedCategoryDetailsContract {
    data class State(
        val category: Drug?,
        val categoryMedItems: List<Drug>
    )
}