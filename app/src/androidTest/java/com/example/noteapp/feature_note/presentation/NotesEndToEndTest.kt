package com.example.noteapp.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.common.theme.NoteAppTheme
import com.example.noteapp.common.di.AppModule
import com.example.noteapp.common.util.TagsForTest
import com.example.noteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.noteapp.feature_note.presentation.notes.NotesScreen
import com.example.noteapp.feature_note.presentation.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()



    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }
    @Test
    fun saveNewNote_editAfterwards() {
        // Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Enter texts in title and content text fields
        composeRule
            .onNodeWithTag(TagsForTest.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TagsForTest.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")
        // Save the new
        composeRule.onNodeWithContentDescription("Save").performClick()


        // Make sure there is a note in the list with our title and content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        // Click on note to edit it
        composeRule.onNodeWithText("test-title").performClick()

        // Make sure title and content text fields contain note title and content
        composeRule
            .onNodeWithTag(TagsForTest.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule
            .onNodeWithTag(TagsForTest.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")
        // Add the text "2" to the title text field
        composeRule
            .onNodeWithTag(TagsForTest.TITLE_TEXT_FIELD)
            .performTextInput("2") // this will add 2 in front of the actual text "test-title" will be "2test-title"
        // Update the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure the update was applied to the list
        composeRule.onNodeWithText("2test-title").assertIsDisplayed()
    }


    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for(i in 1..5) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TagsForTest.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TagsForTest.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()
        composeRule.onNodeWithText("4").assertIsDisplayed()
        composeRule.onNodeWithText("5").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()

        composeRule
            .onNodeWithText("Title")
            .performClick()

        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()


        composeRule.onAllNodesWithTag(TagsForTest.NOTE_ITEM)[0]
            .assertTextContains("5")
        composeRule.onAllNodesWithTag(TagsForTest.NOTE_ITEM)[1]
            .assertTextContains("4")
        composeRule.onAllNodesWithTag(TagsForTest.NOTE_ITEM)[2]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TagsForTest.NOTE_ITEM)[3]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TagsForTest.NOTE_ITEM)[4]
            .assertTextContains("1")
    }



}