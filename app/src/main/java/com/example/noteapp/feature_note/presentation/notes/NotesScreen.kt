package com.example.noteapp.feature_note.presentation.notes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.R
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.presentation.notes.components.NoteItem
import com.example.noteapp.feature_note.presentation.notes.components.OrderSection
import com.example.noteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()





    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.purple_500))
                .padding(2.dp)
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
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = Color.White,
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
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Column(modifier = Modifier.fillMaxSize()) {

                LazyVerticalGrid(
                    cells = GridCells.Fixed(2)
                    , content ={
                        items(state.notes){ note->
                            Box(
                                modifier = Modifier
                                    .padding(5.dp),
                                Alignment.Center,
                                ){
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
