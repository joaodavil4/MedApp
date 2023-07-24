package com.leafwise.medapp.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leafwise.medapp.framework.db.dao.MedicationDao
import com.leafwise.medapp.framework.db.entity.MedicationEntity

@Database(entities = [MedicationEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
}