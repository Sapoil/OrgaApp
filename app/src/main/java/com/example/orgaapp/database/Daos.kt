package com.organizationApp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    fun insertAll(vararg tasks: Task)

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Delete
    fun deleteMult(vararg tasks: Task)

    @Query("SELECT * FROM Task")
    fun getAll(): List <Task>
}

@Dao
interface OrganizationDao {
    @Insert
    fun insert(orga: Organization)

    @Update
    fun update(orga: Organization)

    @Delete
    fun delete(orga: Organization)

    @Query("SELECT * FROM Organization")
    fun getAll(): List <Organization>
}