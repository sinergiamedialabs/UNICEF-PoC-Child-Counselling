package com.west.speechfilter

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = ["question"])
data class DataModel(
    @NotNull @ColumnInfo(name = "question") var question: String,
    @NotNull @ColumnInfo(name = "answer") var answer: String
)