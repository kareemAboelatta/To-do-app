package com.example.noteapp.feature_note.presentation.notes.components

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.noteapp.common.util.TagsForTest
import com.example.noteapp.feature_note.domain.model.Note
import java.util.*

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 30.dp,
    cutCornerSize: Dp = 0.dp,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
            .testTag(TagsForTest.NOTE_ITEM)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 5.dp),
                    text = convertTimestampToDate(note.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note",
                        tint = Color.White,
                    )
                }
            }


        }

    }





}

@RequiresApi(Build.VERSION_CODES.N)
fun convertTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp)
    val dateString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat("dd/MM/yyyy").format(date)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    val timeString = SimpleDateFormat("HH:mm").format(date)
    val dateTimeString = "$dateString"
    return dateTimeString
}
