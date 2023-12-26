package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.repository.FakeNoteRepository
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private lateinit var deleteNote: DeleteNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeRepository)

        // Prepopulate the repository with some notes
        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = "Title $c",
                    content = "Content $c",
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        runTest {
            notesToInsert.forEach { fakeRepository.insertNote(it) }
        }
    }

    @Test
    fun `When Delete ExistingNote Should Remove Note`() = runTest {
        // Choose a note to delete
        val noteToDelete = fakeRepository.getNotes().first().first()

        // Delete the note
        deleteNote.invoke(noteToDelete)

        // Verify the note is no longer in the repository
        val notesAfterDeletion = fakeRepository.getNotes().first()
        assertThat(notesAfterDeletion).doesNotContain(noteToDelete)
    }

    @Test
    fun `When DeleteNote NonExistingNote SNoEffect`() = runTest {
        // Create a note that's not in the repository
        val nonExistingNote = Note(
            title = "Non-existing Title",
            content = "Non-existing Content",
            timestamp = System.currentTimeMillis(),
            color = 0
        )

        // Attempt to delete the non-existing note
        deleteNote.invoke(nonExistingNote)

        // Verify the repository's content remains unchanged
        val notesAfterAttemptedDeletion = fakeRepository.getNotes().first()
        val expectedNotesCount = ('a'..'z').count()
        assertThat(notesAfterAttemptedDeletion.size).isEqualTo(expectedNotesCount)
    }
}
