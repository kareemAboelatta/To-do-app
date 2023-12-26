package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.repository.FakeNoteRepository
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddNoteTest{

    private lateinit var  addNote: AddNote
    private lateinit var  fakeRepository: FakeNoteRepository
    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runTest {
            notesToInsert.forEach { fakeRepository.insertNote(it) }
        }
    }


    @Test(expected = InvalidNoteException::class)
    fun `When enter emptyTitle should throws InvalidNoteException`() = runTest {
        val invalidNote = Note(
            title = "",
            content = "Sample Content",
            timestamp = System.currentTimeMillis(),
            color = 0
        )

        addNote.invoke(invalidNote)
    }


    @Test(expected = InvalidNoteException::class)
    fun `When AddNote with EmptyContent Should Throws InvalidNoteException`() = runTest {
        val invalidNote = Note(
            title = "Sample Title",
            content = "",
            timestamp = System.currentTimeMillis(),
            color = 0
        )

        addNote.invoke(invalidNote)
    }

    @Test
    fun `When Add Valid Note Should Inserts Note`() = runTest {
        val validNote = Note(
            id = 1,
            title = "Sample Title",
            content = "Sample Content",
            timestamp = System.currentTimeMillis(),
            color = 0
        )

        addNote.invoke(validNote)

        val insertedNote = fakeRepository.getNoteById(validNote.id!!)
        Truth.assertThat(insertedNote).isEqualTo(validNote)
    }


}