package com.example.noteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType


@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {

    var selectedOrder by remember {
        mutableStateOf(noteOrder)
    }


    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onOrderChange(NoteOrder.Title(noteOrder.orderType))
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (noteOrder is NoteOrder.Title) {
                            colorResource(id = R.color.purple_700)
                        } else {
                            Color.Gray
                        }
                    )
                    .padding(15.dp)
                    .weight(1f)
            ){
                Text(text = "Title")
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onOrderChange(NoteOrder.Date(noteOrder.orderType))
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (noteOrder is NoteOrder.Date) {
                            colorResource(id = R.color.purple_700)
                        } else {
                            Color.Gray
                        }
                    )
                    .padding(15.dp)
                    .weight(1f)
            ){
                Text(text = "Date")
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onOrderChange(NoteOrder.Color(noteOrder.orderType))
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (noteOrder is NoteOrder.Color) {
                            colorResource(id = R.color.purple_700)
                        } else {
                            Color.Gray
                        }
                    )
                    .padding(15.dp)
                    .weight(1f)
            ){
                Text(text = "Color")
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}

