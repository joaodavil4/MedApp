package com.leafwise.medapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.leafwise.medapp.framework.db.entity.MedicationEntity

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medication")
    fun getAll(): List<MedicationEntity>

    @Insert
    fun insertAll(vararg medications: MedicationEntity)

    @Delete
    fun delete(medication: MedicationEntity)
}