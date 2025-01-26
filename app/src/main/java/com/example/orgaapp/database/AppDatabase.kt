package com.example.orgaapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class, Organization::class],version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

    abstract fun orgaDao(): OrganizationDao
}