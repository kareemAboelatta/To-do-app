package com.example.noteapp.feature_note.presentation.notes


import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.common.util.TagsForTest
import com.example.noteapp.feature_note.presentation.notes.components.NoteItem
import com.example.noteapp.feature_note.presentation.notes.components.OrderSection
import com.example.noteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()


    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {it->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "This is your notes",
                    style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.primary),
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Sort",
                        tint= MaterialTheme.colorScheme.primary ,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag(TagsForTest.ORDER_SECTION),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Column(modifier = Modifier.fillMaxSize()) {

                LazyVerticalGrid(
                    state = rememberLazyGridState(),
                    columns = GridCells.Fixed(2)
                    , content ={
                        items(state.notes){ note->
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .heightIn(140.dp, 180.dp),
                                ){
                                NoteItem(
                                    note = note,
                                    modifier = Modifier
                                        .clickable {
                                            navController.navigate(
                                                Screen.AddEditNoteScreen.route +
                                                        "?noteId=${note.id}&noteColor=${note.color}"
                                            )
                                        },
                                    onDeleteClick = {
                                        viewModel.onEvent(NotesEvent.DeleteNote(note))
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar(
                                                message = "Note deleted",
                                                actionLabel = "Undo"
                                            )
                                            if(result == SnackbarResult.ActionPerformed) {
                                                viewModel.onEvent(NotesEvent.RestoreNote)
                                            }
                                        }
                                    }
                                )

                            }

                        }
                    }
                )
            }
















/*            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }*/
        }
    }


}


@Composable
fun OutlinedTextFieldBackground(
        color: Color,
        content: @Composable () -> Unit
) {
        // This box just wraps the background and the OutlinedTextField
        Box (
        ){
            // This box works as background
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(vertical = 5.dp) // adding some space to the label
                    .padding(horizontal = 2.dp) // adding some space to the label
                    .background(
                        color,
                        // rounded corner to match with the OutlinedTextField
                        shape = RoundedCornerShape(20.dp)
                    )
            )
            // OutlineTextField will be the content...
            content()
        }
}
