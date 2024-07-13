package com.hamza.test.ui.feature.categories

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.hamza.test.R
import com.hamza.test.data.model.data.Drug
import com.hamza.test.utill.noRippleClickable
import com.hamza.test.ui.theme.ComposeSampleTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@ExperimentalCoilApi
@Composable
fun MainCategoriesScreen(
    state: MedContract.State,
    effectFlow: Flow<MedContract.Effect>?,
    onNavigationRequested: (itemId: String) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    // Listen for side effects from the VM
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is MedContract.Effect.DataWasLoaded)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Meds are loaded.",
                    duration = SnackbarDuration.Short
                )
        }?.collect()
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CategoriesAppBar()
        },
    ) {
        Box {
            MedCategoriesList(MedItems = state.categories) { itemId ->
                onNavigationRequested(itemId)
            }
            if (state.isLoading)
                LoadingBar()
        }
    }

}

@Composable
private fun CategoriesAppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                modifier = Modifier.padding(horizontal = 12.dp),
                contentDescription = "Action icon"
            )
        },
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun MedCategoriesList(
    MedItems: List<Drug>,
    onItemClicked: (id: String) -> Unit = { }
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(MedItems) { item ->
            MedItemRow(item = item, itemShouldExpand = true, onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun MedItemRow(
    item: Drug,
    itemShouldExpand: Boolean = false,
    onItemClicked: (id: String) -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onItemClicked(item.name) }
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        Row(modifier = Modifier.animateContentSize()) {
            MedItemDetails(
                item = item,
                expandedLines = if (expanded) 10 else 2,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 24.dp,
                        bottom = 24.dp
                    )
                    .fillMaxWidth(0.80f)
                    .align(Alignment.CenterVertically)
            )
            if (itemShouldExpand)
                Box(
                    modifier = Modifier
                        .align(if (expanded) Alignment.Bottom else Alignment.CenterVertically)
                        .noRippleClickable { expanded = !expanded }
                ) {
                    ExpandableContentIcon(expanded)
                }
        }
    }
}

@Composable
private fun ExpandableContentIcon(expanded: Boolean) {
    Icon(
        imageVector = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown,
        contentDescription = "Expand row icon",
        modifier = Modifier
            .padding(all = 16.dp)
    )
}

@Composable
fun MedItemDetails(
    item: Drug?,
    expandedLines: Int,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = item?.name ?: "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (item?.dose?.trim()?.isNotEmpty() == true)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = item.dose.trim(),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption,
                    maxLines = expandedLines
                )
            }
    }
}


@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        MainCategoriesScreen(MedContract.State(), null, { })
    }
}