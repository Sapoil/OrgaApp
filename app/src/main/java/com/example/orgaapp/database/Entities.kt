package com.example.orgaapp.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = Organization::class,
        parentColumns = ["id"],
        childColumns = ["orgId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["orgId"])]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val title: String,
    val detail: String,
    val importance: Int,
    val deadline: Date,
    val orgId: Int
)

@Entity(tableName = "organizations")
data class Organization(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val name: String,
    val detail: String?
)