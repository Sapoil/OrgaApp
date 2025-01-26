package com.example.orgaapp.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [ForeignKey(
        entity = Organization::class,
        parentColumns = ["id"],
        childColumns = ["orgId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,

    val title: String,
    val detail: String,
    val importance: Int,
    val deadline: Date,
    val orgId: Int?
)
{
    init{
        require(importance in 0..10)
        require(deadline.after(Date()))
    }
}

@Entity
data class Organization(
    @PrimaryKey(autoGenerate = true) val id: Int,

    val name: String,
    val detail: String?
)