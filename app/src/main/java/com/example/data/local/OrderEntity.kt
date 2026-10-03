package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val orderType: String, // "Dine-In", "Pickup", "Delivery"
    val itemsSummary: String,
    val itemsCount: Int,
    val subtotal: Double,
    val tip: Double,
    val tax: Double,
    val total: Double,
    val customerName: String,
    val customerPhone: String,
    val tableOrAddress: String,
    val specialNotes: String = "",
    val status: String = "Confirmed", // "Confirmed", "Preparing in Kitchen", "Ready for Pickup", "Completed"
    val createdAt: Long = System.currentTimeMillis()
)
