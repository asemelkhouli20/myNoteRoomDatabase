package com.example.mynoteroomdatabase.persentation

import com.example.mynoteroomdatabase.data.Note

sealed interface NotesEvent {
    object SortNotes: NotesEvent
    data class DeleteNote(val note: Note):NotesEvent
    data class SaveNote(
        val tile: String,
        val descriptor: String
    ):NotesEvent

}