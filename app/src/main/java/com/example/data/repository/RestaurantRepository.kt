package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.OrderEntity
import com.example.data.local.ReservationEntity
import com.example.data.model.MenuItem
import kotlinx.coroutines.flow.Flow

class RestaurantRepository(private val database: AppDatabase) {

    val reservations: Flow<List<ReservationEntity>> =
        database.reservationDao().getAllReservations()

    val orders: Flow<List<OrderEntity>> =
        database.orderDao().getAllOrders()

    fun getMenu(): List<MenuItem> = MenuData.sampleDishes

    suspend fun saveReservation(reservation: ReservationEntity): Long {
        return database.reservationDao().insertReservation(reservation)
    }

    suspend fun cancelReservation(id: Long) {
        database.reservationDao().cancelReservation(id)
    }

    suspend fun createOrder(order: OrderEntity): Long {
        return database.orderDao().insertOrder(order)
    }

    suspend fun updateOrderStatus(id: Long, status: String) {
        database.orderDao().updateOrderStatus(id, status)
    }
}
