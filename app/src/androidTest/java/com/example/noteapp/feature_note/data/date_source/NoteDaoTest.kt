package com.example.noteapp.feature_note.data.date_source

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.noteapp.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDaoTest {

    private lateinit var database: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()
        noteDao = database.noteDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun when_insertNote_should_savesNote() = runTest {
        val note = Note(id = 1, title = "Test Note", content = "This is a test", timestamp = System.currentTimeMillis(), color = 0)
        noteDao.insertNote(note)

        val notes = noteDao.getNotes().first()
        assertThat(notes).contains(note)
    }

    @Test
    fun when_getNoteById_should_retrievesCorrectNote() = runTest {
        val note = Note(id = 2, title = "Another Test Note", content = "Content here", timestamp = System.currentTimeMillis(), color = 0)
        noteDao.insertNote(note)

        val retrievedNote = noteDao.getNoteById(2)


        assertThat(note).isEqualTo(retrievedNote)
    }

    @Test
    fun when_deleteNote_should_removesNote() = runTest {
        val note = Note(id = 3, title = "Test Note for Deletion", content = "This note will be deleted", timestamp = System.currentTimeMillis(), color = 0)
        noteDao.insertNote(note)
        noteDao.deleteNote(note)

        val notes = noteDao.getNotes().first()

        assertThat(notes).doesNotContain(note)
    }


    @Test
    fun when_searchForNote_should_returnsMatchingNotes() = runTest {
        // Insert some notes
        val note1 = Note("Search Note 1", "Content 1", System.currentTimeMillis(), 0, 1)
        val note2 = Note("Search Note 2", "Content 2", System.currentTimeMillis(), 0, 2)
        noteDao.insertNote(note1)
        noteDao.insertNote(note2)

        val allNotes = noteDao.searchForNote("Search").first() // Use first() to get the current list emitted by Flow


        // Assert that the retrieved notes match the inserted ones
        assertThat(allNotes.containsAll(listOf(note1, note2))).isTrue()
    }




}
