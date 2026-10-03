package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.OrderEntity
import com.example.data.local.ReservationEntity
import com.example.data.model.CartItem
import com.example.data.model.DietaryTag
import com.example.data.model.GlobalLocations
import com.example.data.model.MenuCategory
import com.example.data.model.MenuItem
import com.example.data.model.RestaurantBranch
import com.example.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppNavDestination(val label: String) {
    HOME("Home"),
    MENU("Menu"),
    ORDER("Order"),
    RESERVATIONS("Reservations")
}

data class MenuFilterState(
    val category: MenuCategory = MenuCategory.ALL,
    val selectedDietary: Set<DietaryTag> = emptySet(),
    val searchQuery: String = ""
)

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RestaurantRepository(AppDatabase.getDatabase(application))

    // Navigation
    private val _currentDestination = MutableStateFlow(AppNavDestination.HOME)
    val currentDestination: StateFlow<AppNavDestination> = _currentDestination.asStateFlow()

    fun navigateTo(destination: AppNavDestination) {
        _currentDestination.value = destination
    }

    // Global Flagship Branch / Location Management
    val flagshipBranches = GlobalLocations.flagshipBranches
    val allCountries = GlobalLocations.allCountries
    val majorGlobalCities = GlobalLocations.majorGlobalCities

    private val _selectedBranch = MutableStateFlow(GlobalLocations.flagshipBranches[0])
    val selectedBranch: StateFlow<RestaurantBranch> = _selectedBranch.asStateFlow()

    fun selectBranch(branch: RestaurantBranch) {
        _selectedBranch.value = branch
    }

    fun getStatesForCountry(country: String): List<String> {
        return GlobalLocations.getStatesForCountry(country)
    }

    // Global Delivery Address Selection
    private val _deliveryCountry = MutableStateFlow("United States")
    val deliveryCountry: StateFlow<String> = _deliveryCountry.asStateFlow()

    private val _deliveryState = MutableStateFlow("New York")
    val deliveryState: StateFlow<String> = _deliveryState.asStateFlow()

    private val _deliveryCity = MutableStateFlow("New York")
    val deliveryCity: StateFlow<String> = _deliveryCity.asStateFlow()

    fun setDeliveryCountry(country: String) {
        _deliveryCountry.value = country
        val availableStates = getStatesForCountry(country)
        _deliveryState.value = availableStates.firstOrNull() ?: ""
    }

    fun setDeliveryState(state: String) {
        _deliveryState.value = state
    }

    fun setDeliveryCity(city: String) {
        _deliveryCity.value = city
    }

    // Full Menu
    val allDishes: List<MenuItem> = repository.getMenu()

    // Menu Filters
    private val _filterState = MutableStateFlow(MenuFilterState())
    val filterState: StateFlow<MenuFilterState> = _filterState.asStateFlow()

    private val _selectedDishForDetail = MutableStateFlow<MenuItem?>(null)
    val selectedDishForDetail: StateFlow<MenuItem?> = _selectedDishForDetail.asStateFlow()

    fun setCategory(category: MenuCategory) {
        _filterState.update { it.copy(category = category) }
    }

    fun toggleDietaryTag(tag: DietaryTag) {
        _filterState.update { current ->
            val set = current.selectedDietary.toMutableSet()
            if (set.contains(tag)) set.remove(tag) else set.add(tag)
            current.copy(selectedDietary = set)
        }
    }

    fun clearDietaryFilters() {
        _filterState.update { it.copy(selectedDietary = emptySet()) }
    }

    fun setSearchQuery(query: String) {
        _filterState.update { it.copy(searchQuery = query) }
    }

    fun openDishDetail(item: MenuItem) {
        _selectedDishForDetail.value = item
    }

    fun closeDishDetail() {
        _selectedDishForDetail.value = null
    }

    // Cart Management
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _orderType = MutableStateFlow("Pickup") // "Dine-In", "Pickup", "Delivery"
    val orderType: StateFlow<String> = _orderType.asStateFlow()

    private val _tipPercentage = MutableStateFlow(18) // 15, 18, 20, 0
    val tipPercentage: StateFlow<Int> = _tipPercentage.asStateFlow()

    private val _promoCode = MutableStateFlow("")
    val promoCode: StateFlow<String> = _promoCode.asStateFlow()

    private val _promoDiscount = MutableStateFlow(0.0)
    val promoDiscount: StateFlow<Double> = _promoDiscount.asStateFlow()

    private val _promoMessage = MutableStateFlow<String?>(null)
    val promoMessage: StateFlow<String?> = _promoMessage.asStateFlow()

    private val _placedOrderDialog = MutableStateFlow<OrderEntity?>(null)
    val placedOrderDialog: StateFlow<OrderEntity?> = _placedOrderDialog.asStateFlow()

    fun setOrderType(type: String) {
        _orderType.value = type
    }

    fun setTipPercentage(percentage: Int) {
        _tipPercentage.value = percentage
    }

    fun addToCart(item: MenuItem, quantity: Int = 1, customization: String = "", notes: String = "") {
        _cartItems.update { current ->
            val existingIndex = current.indexOfFirst {
                it.menuItem.id == item.id && it.selectedCustomization == customization
            }
            if (existingIndex >= 0) {
                current.mapIndexed { index, cartItem ->
                    if (index == existingIndex) {
                        cartItem.copy(quantity = cartItem.quantity + quantity)
                    } else cartItem
                }
            } else {
                current + CartItem(
                    menuItem = item,
                    quantity = quantity,
                    selectedCustomization = customization,
                    specialNotes = notes
                )
            }
        }
    }

    fun updateCartQuantity(index: Int, newQuantity: Int) {
        _cartItems.update { current ->
            if (newQuantity <= 0) {
                current.filterIndexed { i, _ -> i != index }
            } else {
                current.mapIndexed { i, item ->
                    if (i == index) item.copy(quantity = newQuantity) else item
                }
            }
        }
    }

    fun removeCartItem(index: Int) {
        _cartItems.update { current -> current.filterIndexed { i, _ -> i != index } }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        _promoCode.value = ""
        _promoDiscount.value = 0.0
        _promoMessage.value = null
    }

    fun applyPromoCode(code: String) {
        val trimmed = code.trim().uppercase(Locale.ROOT)
        _promoCode.value = trimmed
        if (trimmed == "AURA10" || trimmed == "WELCOME10") {
            _promoDiscount.value = 0.10
            _promoMessage.value = "10% VIP Dining Discount Applied!"
        } else if (trimmed == "CHEF20") {
            _promoDiscount.value = 0.20
            _promoMessage.value = "20% Tasting Discount Applied!"
        } else {
            _promoDiscount.value = 0.0
            _promoMessage.value = "Invalid promotional code"
        }
    }

    fun placeOrder(
        customerName: String,
        customerPhone: String,
        tableOrAddress: String,
        notes: String
    ) {
        val currentItems = _cartItems.value
        if (currentItems.isEmpty()) return

        val sub = currentItems.sumOf { it.totalPrice }
        val discount = sub * _promoDiscount.value
        val discountedSub = (sub - discount).coerceAtLeast(0.0)
        val tip = discountedSub * (_tipPercentage.value / 100.0)
        val tax = discountedSub * 0.08875 // 8.875% standard city dining tax
        val finalTotal = discountedSub + tip + tax

        val itemsSummary = currentItems.joinToString(", ") {
            "${it.quantity}x ${it.menuItem.name}"
        }

        val codeNum = (1000..9999).random()
        val order = OrderEntity(
            orderNumber = "AUR-$codeNum",
            orderType = _orderType.value,
            itemsSummary = itemsSummary,
            itemsCount = currentItems.sumOf { it.quantity },
            subtotal = discountedSub,
            tip = tip,
            tax = tax,
            total = finalTotal,
            customerName = customerName.ifBlank { "Aura Patron" },
            customerPhone = customerPhone.ifBlank { "+1 (555) 019-2834" },
            tableOrAddress = tableOrAddress.ifBlank {
                if (_orderType.value == "Dine-In") "Table 7 • ${_selectedBranch.value.branchName}"
                else "${_deliveryCity.value}, ${_deliveryState.value}, ${_deliveryCountry.value}"
            },
            specialNotes = notes,
            status = "Confirmed"
        )

        viewModelScope.launch {
            repository.createOrder(order)
            _placedOrderDialog.value = order
            clearCart()
        }
    }

    fun dismissOrderDialog() {
        _placedOrderDialog.value = null
    }

    // Reservation Management
    private val _reservationConfirmation = MutableStateFlow<ReservationEntity?>(null)
    val reservationConfirmation: StateFlow<ReservationEntity?> = _reservationConfirmation.asStateFlow()

    fun makeReservation(
        name: String,
        phone: String,
        email: String,
        partySize: Int,
        dateText: String,
        timeSlot: String,
        seatingArea: String,
        specialRequests: String
    ) {
        val randomSuffix = (100..999).random()
        val code = "RES-$randomSuffix"
        val fullSeatingArea = "${_selectedBranch.value.cityName} • $seatingArea"

        val reservation = ReservationEntity(
            confirmationCode = code,
            guestName = name.ifBlank { "Aura Guest" },
            guestPhone = phone.ifBlank { "+1 (555) 345-9821" },
            guestEmail = email.ifBlank { "guest@auradining.com" },
            partySize = partySize,
            dateText = dateText,
            timeSlot = timeSlot,
            seatingArea = fullSeatingArea,
            specialRequests = specialRequests,
            status = "CONFIRMED"
        )

        viewModelScope.launch {
            repository.saveReservation(reservation)
            _reservationConfirmation.value = reservation
        }
    }

    fun dismissReservationDialog() {
        _reservationConfirmation.value = null
    }

    fun cancelReservation(id: Long) {
        viewModelScope.launch {
            repository.cancelReservation(id)
        }
    }

    // Persistent Room Lists
    val reservationsList: StateFlow<List<ReservationEntity>> = repository.reservations
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val ordersList: StateFlow<List<OrderEntity>> = repository.orders
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
