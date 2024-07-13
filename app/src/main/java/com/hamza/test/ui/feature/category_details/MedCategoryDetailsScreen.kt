package com.hamza.test.ui.feature.category_details


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamza.test.ui.feature.categories.MedItemDetails
import com.hamza.test.data.model.data.Drug
import com.hamza.test.ui.feature.categories.MedContract
import com.hamza.test.ui.feature.categories.MainCategoriesScreen
import com.hamza.test.ui.theme.ComposeSampleTheme
import kotlin.math.min


@Composable
fun MedCategoryDetailsScreen(state: MedCategoryDetailsContract.State) {
    val scrollState = rememberLazyListState()
    val scrollOffset: Float = min(
        1f,
        1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex)
    )
    Surface(color = MaterialTheme.colors.background) {
        Column {
            Surface(elevation = 4.dp) {
                CategoryDetailsCollapsingToolbar(state.category, scrollOffset)
            }
            Spacer(modifier = Modifier.height(2.dp))

        }
    }
}

@Composable
private fun CategoryDetailsCollapsingToolbar(
    category: Drug?,
    scrollOffset: Float,
) {
    Row {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = CircleShape,
            border = BorderStroke(
                width = 2.dp,
                color = Color.Black
            ),
            elevation = 4.dp
        ) {

        }
        MedItemDetails(
            item = category,
            expandedLines = (kotlin.math.max(3f, scrollOffset * 6)).toInt(),
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        MainCategoriesScreen(MedContract.State(), null, { })
    }
}