package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.repository.FakeNoteRepository
import com.example.noteapp.feature_note.domain.model.Note
import com.google.common.truth.Truth.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test



class GetNoteTest {

    private lateinit var getNote: GetNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNote = GetNote(fakeRepository)

        // Prepopulate the repository with some notes
        ('a'..'z').forEachIndexed { index, c ->
            runTest {
                fakeRepository.insertNote(
                    Note(
                        id = index,
                        title = "Title $c",
                        content = "Content $c",
                        timestamp = index.toLong(),
                        color = index
                    )
                )
            }

        }
    }

    @Test
    fun `When Get ExistingId Should Returns Correct Note`() = runTest {
        val existingId = fakeRepository.getNotes().first().first().id ?: 0

        val retrievedNote = getNote.invoke(existingId)

        assertThat(retrievedNote).isEqualTo(fakeRepository.getNotes().first().first())
    }

    @Test
    fun `When  Note NonExistingId Should ReturnsNull`() = runTest {
        // Choose an ID that we know doesn't exist
        val nonExistingId = 100 // Example ID, ensure this ID doesn't exist in your setup

        // Retrieve the note by this non-existing ID
        val retrievedNote = getNote.invoke(nonExistingId)

        // Verify that no note is returned
        assertThat(retrievedNote).isNull()
    }
}
