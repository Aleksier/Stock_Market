package com.example.stockmarket
/*
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesCompaniesDao {
    @Query("SELECT * FROM favorites ORDER BY id ASC")
    fun getAll(): LiveData<List<FavoritesCompanies>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(company: FavoritesCompanies): Long

    @Update
    fun update(company: FavoritesCompanies)

    @Query("DELETE FROM favorites WHERE id = :companyId")
    fun deleteById(companyId: Long)

    @Query("DELETE FROM favorites")
    fun deleteAll()
}
 */