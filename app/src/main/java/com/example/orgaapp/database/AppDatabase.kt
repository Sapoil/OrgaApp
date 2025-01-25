package com.example.orgaapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.organizationApp.database.Organization
import com.organizationApp.database.OrganizationDao
import com.organizationApp.database.Task
import com.organizationApp.database.TaskDao

@Database(entities = [Task::class, Organization::class],version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

    abstract fun orgaDao(): OrganizationDao
}