package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.MenuItem
import com.example.ui.components.AuraBottomNavigation
import com.example.ui.components.DishDetailSheet
import com.example.ui.components.GlobalLocationSheet
import com.example.ui.components.WebsiteHeader
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MenuScreen
import com.example.ui.screens.OrderingScreen
import com.example.ui.screens.ReservationScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppNavDestination
import com.example.ui.viewmodel.RestaurantViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AuraDiningApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuraDiningApp(
    viewModel: RestaurantViewModel = viewModel()
) {
    val currentDestination by viewModel.currentDestination.collectAsStateWithLifecycle()
    val selectedBranch by viewModel.selectedBranch.collectAsStateWithLifecycle()
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()
    val selectedDish by viewModel.selectedDishForDetail.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val orderType by viewModel.orderType.collectAsStateWithLifecycle()
    val tipPercentage by viewModel.tipPercentage.collectAsStateWithLifecycle()
    val promoCode by viewModel.promoCode.collectAsStateWithLifecycle()
    val promoDiscount by viewModel.promoDiscount.collectAsStateWithLifecycle()
    val promoMessage by viewModel.promoMessage.collectAsStateWithLifecycle()
    val placedOrderDialog by viewModel.placedOrderDialog.collectAsStateWithLifecycle()
    val reservationConfirmation by viewModel.reservationConfirmation.collectAsStateWithLifecycle()
    val reservationsList by viewModel.reservationsList.collectAsStateWithLifecycle()
    val ordersList by viewModel.ordersList.collectAsStateWithLifecycle()

    var showLocationSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val locationSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val totalCartCount = cartItems.sumOf { it.quantity }

    // Back button handling: return to HOME if on sub-screen
    BackHandler(enabled = currentDestination != AppNavDestination.HOME) {
        viewModel.navigateTo(AppNavDestination.HOME)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            WebsiteHeader(
                currentDestination = currentDestination,
                onNavigate = { dest -> viewModel.navigateTo(dest) },
                currentCity = selectedBranch.cityName,
                onLocationClick = { showLocationSheet = true },
                cartCount = totalCartCount,
                onCartClick = { viewModel.navigateTo(AppNavDestination.ORDER) }
            )
        },
        bottomBar = {
            AuraBottomNavigation(
                currentDestination = currentDestination,
                onNavigate = { dest -> viewModel.navigateTo(dest) },
                cartCount = totalCartCount
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentDestination) {
                AppNavDestination.HOME -> {
                    HomeScreen(
                        featuredDishes = viewModel.allDishes,
                        branch = selectedBranch,
                        onSwitchBranch = { showLocationSheet = true },
                        onNavigate = { dest -> viewModel.navigateTo(dest) },
                        onDishClick = { dish -> viewModel.openDishDetail(dish) },
                        onQuickAdd = { dish ->
                            viewModel.addToCart(dish)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Added ${dish.name} to order")
                            }
                        }
                    )
                }

                AppNavDestination.MENU -> {
                    MenuScreen(
                        allDishes = viewModel.allDishes,
                        filterState = filterState,
                        onCategorySelected = { cat -> viewModel.setCategory(cat) },
                        onDietaryTagToggled = { tag -> viewModel.toggleDietaryTag(tag) },
                        onClearDietaryFilters = { viewModel.clearDietaryFilters() },
                        onSearchQueryChanged = { query -> viewModel.setSearchQuery(query) },
                        onDishClick = { dish -> viewModel.openDishDetail(dish) },
                        onQuickAdd = { dish ->
                            viewModel.addToCart(dish)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Added ${dish.name} to order")
                            }
                        },
                        onNavigate = { dest -> viewModel.navigateTo(dest) },
                        onOpenLocations = { showLocationSheet = true }
                    )
                }

                AppNavDestination.ORDER -> {
                    OrderingScreen(
                        cartItems = cartItems,
                        currentBranch = selectedBranch,
                        orderType = orderType,
                        onOrderTypeChanged = { type -> viewModel.setOrderType(type) },
                        tipPercentage = tipPercentage,
                        onTipChanged = { tip -> viewModel.setTipPercentage(tip) },
                        promoCode = promoCode,
                        promoDiscount = promoDiscount,
                        promoMessage = promoMessage,
                        onApplyPromo = { code -> viewModel.applyPromoCode(code) },
                        onUpdateQuantity = { idx, qty -> viewModel.updateCartQuantity(idx, qty) },
                        onRemoveItem = { idx -> viewModel.removeCartItem(idx) },
                        onPlaceOrder = { name, phone, address, notes ->
                            viewModel.placeOrder(name, phone, address, notes)
                        },
                        placedOrderDialog = placedOrderDialog,
                        onDismissOrderDialog = { viewModel.dismissOrderDialog() },
                        pastOrders = ordersList,
                        onExploreMenu = { viewModel.navigateTo(AppNavDestination.MENU) }
                    )
                }

                AppNavDestination.RESERVATIONS -> {
                    ReservationScreen(
                        currentBranch = selectedBranch,
                        onSelectBranch = { branch -> viewModel.selectBranch(branch) },
                        onMakeReservation = { name, phone, email, size, date, time, area, notes ->
                            viewModel.makeReservation(name, phone, email, size, date, time, area, notes)
                        },
                        confirmationDialog = reservationConfirmation,
                        onDismissDialog = { viewModel.dismissReservationDialog() },
                        reservationsList = reservationsList,
                        onCancelReservation = { id -> viewModel.cancelReservation(id) }
                    )
                }
            }

            // Global Location / City Selector Sheet
            if (showLocationSheet) {
                GlobalLocationSheet(
                    sheetState = locationSheetState,
                    selectedBranch = selectedBranch,
                    onSelectBranch = { branch -> viewModel.selectBranch(branch) },
                    onDismiss = { showLocationSheet = false }
                )
            }

            // Dish Detail Sheet (Allergen info, ingredients, customization & Add to Order)
            if (selectedDish != null) {
                DishDetailSheet(
                    item = selectedDish!!,
                    sheetState = sheetState,
                    onDismiss = { viewModel.closeDishDetail() },
                    onAddToCart = { qty, customization, notes ->
                        viewModel.addToCart(selectedDish!!, qty, customization, notes)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Added $qty x ${selectedDish!!.name} to order")
                        }
                    }
                )
            }
        }
    }
}
