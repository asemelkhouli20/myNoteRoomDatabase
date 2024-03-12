package com.example.mynoteroomdatabase.persentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynoteroomdatabase.data.Note
import com.example.mynoteroomdatabase.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val dao: NoteDao
) : ViewModel() {
    private val isSortedByAdded = MutableStateFlow(true)

    private var note = isSortedByAdded.flatMapLatest {
        if (it) {
            dao.getNotesOrderByDateAdded()
        } else {
            dao.getNotesOrderByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(NotesState())
    val state = combine(_state, isSortedByAdded, note) { state, isSortedByAdded, note ->
        state.copy(note = note)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }

            }

            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    description = state.value.descriptor.value,
                    dataAndes = System.currentTimeMillis()
                    )
                viewModelScope.launch {
                    dao.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        descriptor = mutableStateOf("")
                    )
                }

            }

            NotesEvent.SortNotes -> {
                isSortedByAdded.value = !isSortedByAdded.value


            }
        }
    }
}