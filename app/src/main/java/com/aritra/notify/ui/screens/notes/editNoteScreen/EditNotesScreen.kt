package com.aritra.notify.ui.screens.notes.editNoteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aritra.notify.R
import com.aritra.notify.components.topbar.EditNoteTopBar
import com.aritra.notify.utils.Const
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun EditNotesScreen(
    noteId: Int,
    navigateBack: () -> Unit
) {
    val editViewModel = hiltViewModel<EditScreenViewModel>()
    val title = editViewModel.noteModel.title
    val description = editViewModel.noteModel.note
    val dateTime = editViewModel.noteModel.dateTime
    val focus = LocalFocusManager.current
    val characterCount = remember { mutableIntStateOf(title.length + description.length) }
    val formattedDateTime = SimpleDateFormat(Const.DATE_TIME_FORMAT, Locale.getDefault()).format(dateTime ?: 0)
    val formattedCharacterCount = "${characterCount.value} characters"

    LaunchedEffect(Unit) {
        editViewModel.getNoteById(noteId)
    }
    Scaffold(
        topBar = {
            if (dateTime != null) {
                EditNoteTopBar(editViewModel, noteId, navigateBack, title, description, dateTime)
            }
        }
    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = title,
                    onValueChange = { newTitle ->
                        editViewModel.updateTitle(newTitle)
                        characterCount.value = newTitle.length + description.length
                    },
                    placeholder = {
                        Text(
                            stringResource(R.string.title),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W700,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_medium))
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    ),
                    maxLines = Int.MAX_VALUE,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focus.moveFocus(FocusDirection.Down)
                    }
                    ),
                )
                TextField(
                    value = "$formattedDateTime  |  $formattedCharacterCount",
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_light))
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                    )
                )
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = description,
                    onValueChange = { newDescription ->
                        editViewModel.updateDescription(newDescription)
                        characterCount.value = title.length + newDescription.length
                    },
                    placeholder = {
                        Text(
                            stringResource(R.string.notes),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_light))
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_light)),
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                    ),
                )
            }
        }
    }
}