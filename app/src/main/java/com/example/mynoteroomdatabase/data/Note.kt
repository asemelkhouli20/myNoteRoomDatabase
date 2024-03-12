package com.example.mynoteroomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val description: String,
    val dataAndes: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

)