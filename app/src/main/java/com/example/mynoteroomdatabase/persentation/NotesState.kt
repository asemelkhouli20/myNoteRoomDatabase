package com.example.mynoteroomdatabase.persentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mynoteroomdatabase.data.Note

data class NotesState(
    val note: List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val descriptor: MutableState<String> = mutableStateOf("")


)