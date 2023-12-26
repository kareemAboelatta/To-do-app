package com.example.noteapp.feature_note.presentation.notes

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.noteapp.common.theme.NoteAppTheme
import com.example.noteapp.common.util.TagsForTest
import com.example.noteapp.common.di.AppModule
import com.example.noteapp.feature_note.presentation.MainActivity
import com.example.noteapp.feature_note.presentation.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun click_SortIcon_should_ToggleOrderSection_Visible() {
//        val context =   ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(TagsForTest.ORDER_SECTION).assertDoesNotExist()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TagsForTest.ORDER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TagsForTest.ORDER_SECTION).assertDoesNotExist()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TagsForTest.ORDER_SECTION).assertIsDisplayed()



    }
}