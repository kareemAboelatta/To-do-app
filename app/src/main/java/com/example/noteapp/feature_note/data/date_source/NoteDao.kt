package com.example.noteapp.feature_note.data.date_source

import androidx.room.*
import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Query("SELECT * FROM note WHERE title LIKE :searchQuery or content LIKE :searchQuery")
    fun searchForNote(searchQuery : String): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}