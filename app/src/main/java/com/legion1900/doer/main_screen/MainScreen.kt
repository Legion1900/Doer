package com.legion1900.doer.main_screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.legion1900.doer.R
import com.legion1900.doer.feature_list.NotesListScreen
import com.legion1900.doer.feature_list.NotesListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainScreen(
    startDestination: MainScreenDestination = MainScreenDestination.ToDoScreen,
    onNoteClick: (noteId: String) -> Unit,
    onAddNoteClick: () -> Unit
) {
    var currentDestination by remember { mutableStateOf(startDestination) }
    val todoViewModel = koinViewModel<NotesListViewModel>(key = "todo")
    val doneViewModel = koinViewModel<NotesListViewModel>(key = "done")

    MainScreenScaffold(
        currentDestination,
        onNavigateTodoClick = { currentDestination = MainScreenDestination.ToDoScreen },
        onNavigateDoneClick = { currentDestination = MainScreenDestination.DoneScreen }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        // FIXME: hoist viewmodel to this level, handle all stuff here
        NotesListScreen(
            currentDestination.mode,
            viewModel = if (currentDestination is MainScreenDestination.ToDoScreen) todoViewModel else doneViewModel,
            modifier = modifier
        )
    }
}

@Composable
private fun MainScreenScaffold(
    destination: MainScreenDestination = MainScreenDestination.ToDoScreen,
    onNavigateTodoClick: () -> Unit = {},
    onNavigateDoneClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { MainScreenTopAppBar() },
        bottomBar = {
            MainScreenBottomAppBar(
                destination,
                onTodoIconClick = onNavigateTodoClick,
                onDoneIconClick = onNavigateDoneClick
            )
        },
        content = content
    )
}

@Composable
private fun MainScreenBottomAppBar(
    destination: MainScreenDestination,
    onTodoIconClick: () -> Unit = {},
    onDoneIconClick: () -> Unit = {},
) {
    NavigationBar {

        DoerNavBarItem(
            isSelected = destination is MainScreenDestination.ToDoScreen,
            R.drawable.ic_todo,
            R.string.main_screen_todo_list_title,
            R.string.main_screen_todo_list_nav_description,
            modifier = Modifier.weight(1f),
            onTodoIconClick
        )

        DoerNavBarItem(
            isSelected = destination is MainScreenDestination.DoneScreen,
            R.drawable.ic_done,
            R.string.main_screen_done_list_title,
            R.string.main_screen_done_list_nav_description,
            modifier = Modifier.weight(1f),
            onDoneIconClick
        )
    }
}

@Composable
private fun RowScope.DoerNavBarItem(
    isSelected: Boolean,
    @DrawableRes iconId: Int,
    @StringRes titleId: Int,
    @StringRes contentDescriptionId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    NavigationBarItem(
        selected = isSelected,
        label = { Text(stringResource(titleId)) },
        icon = {
            Icon(
                painterResource(iconId),
                contentDescription = stringResource(contentDescriptionId),
            )
        },
        onClick = onClick,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun MainScreenScaffoldPreview() {
    MainScreenScaffold { }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenTopAppBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.main_screen_todo_list_title)) },
    )
}
