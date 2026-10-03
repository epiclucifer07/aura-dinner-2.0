package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservations ORDER BY createdAt DESC")
    fun getAllReservations(): Flow<List<ReservationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(reservation: ReservationEntity): Long

    @Query("UPDATE reservations SET status = 'CANCELLED' WHERE id = :id")
    suspend fun cancelReservation(id: Long)

    @Query("DELETE FROM reservations WHERE id = :id")
    suspend fun deleteReservation(id: Long)
}
