package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val confirmationCode: String,
    val guestName: String,
    val guestPhone: String,
    val guestEmail: String,
    val partySize: Int,
    val dateText: String,
    val timeSlot: String,
    val seatingArea: String,
    val specialRequests: String = "",
    val status: String = "CONFIRMED",
    val createdAt: Long = System.currentTimeMillis()
)
