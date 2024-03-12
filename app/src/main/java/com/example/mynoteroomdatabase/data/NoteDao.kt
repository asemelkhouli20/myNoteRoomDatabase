package com.example.mynoteroomdatabase.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Note)
    @Delete
    suspend fun deleteNote(note: Note)
    @Query("SELECT * FROM note ORDER BY dataAndes")
    fun getNotesOrderByDateAdded(): Flow<List<Note>>
    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getNotesOrderByTitle(): Flow<List<Note>>

}