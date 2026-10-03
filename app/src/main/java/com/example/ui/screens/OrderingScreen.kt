package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.OrderEntity
import com.example.data.model.CartItem
import com.example.data.model.GlobalLocations
import com.example.data.model.RestaurantBranch
import com.example.ui.theme.AuraCrimson
import com.example.ui.theme.AuraCrimsonDark
import com.example.ui.theme.AuraCrimsonSoft
import com.example.ui.theme.AuraSurfaceBorder
import com.example.ui.theme.AuraTextMuted
import com.example.ui.theme.AuraTextSecondary
import com.example.ui.theme.AuraWhite
import com.example.ui.theme.DietaryGreen
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderingScreen(
    cartItems: List<CartItem>,
    currentBranch: RestaurantBranch = GlobalLocations.flagshipBranches[0],
    orderType: String,
    onOrderTypeChanged: (String) -> Unit,
    tipPercentage: Int,
    onTipChanged: (Int) -> Unit,
    promoCode: String,
    promoDiscount: Double,
    promoMessage: String?,
    onApplyPromo: (String) -> Unit,
    onUpdateQuantity: (index: Int, newQuantity: Int) -> Unit,
    onRemoveItem: (index: Int) -> Unit,
    onPlaceOrder: (name: String, phone: String, tableOrAddress: String, notes: String) -> Unit,
    placedOrderDialog: OrderEntity?,
    onDismissOrderDialog: () -> Unit,
    pastOrders: List<OrderEntity>,
    onExploreMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableIntStateOf(0) } // 0: Cart & Checkout, 1: Order History
    var showCheckoutDialog by remember { mutableStateOf(false) }

    // Checkout form inputs
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var streetAddress by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(currentBranch.country) }
    var selectedState by remember { mutableStateOf(currentBranch.stateOrRegion) }
    var selectedCity by remember { mutableStateOf(currentBranch.cityName) }
    var tableOrPickupTime by remember { mutableStateOf(if (orderType == "Dine-In") "Table 4" else "7:30 PM") }
    var orderNotes by remember { mutableStateOf("") }
    var promoInput by remember { mutableStateOf("") }

    var countryDropdownExpanded by remember { mutableStateOf(false) }
    var stateDropdownExpanded by remember { mutableStateOf(false) }
    var cityDropdownExpanded by remember { mutableStateOf(false) }

    val rawSubtotal = cartItems.sumOf { it.totalPrice }
    val discount = rawSubtotal * promoDiscount
    val subtotal = (rawSubtotal - discount).coerceAtLeast(0.0)
    val tip = subtotal * (tipPercentage / 100.0)
    val tax = subtotal * 0.08875
    val total = subtotal + tip + tax

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Switcher: Cart / History
        TabRow(
            selectedTabIndex = activeTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = AuraCrimson,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[activeTab]),
                    color = AuraCrimson
                )
            }
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = {
                    Text(
                        text = if (cartItems.isNotEmpty()) "Order Cart (${cartItems.sumOf { it.quantity }})" else "Order Cart",
                        fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = {
                    Text(
                        text = "My Orders (${pastOrders.size})",
                        fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
        }

        if (activeTab == 0) {
            // Cart & Ordering Flow
            if (cartItems.isEmpty()) {
                // Empty Cart State
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(AuraCrimsonSoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = AuraCrimson,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Your Order is Empty",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Explore our Royal Indian specialties, continental mains, or fine wines to begin your order.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AuraTextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onExploreMenu,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraCrimson,
                            contentColor = AuraWhite
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("empty_cart_browse_menu_button")
                    ) {
                        Text(
                            text = "Browse Dynamic Menu",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp)
                ) {
                    // Active Global Branch Banner
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(1.dp, AuraSurfaceBorder, RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = AuraCrimson,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Fulfilling from ${currentBranch.branchName}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${currentBranch.cityName}, ${currentBranch.country} • ${currentBranch.address}",
                                        fontSize = 11.sp,
                                        color = AuraTextSecondary
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // Order Type Toggle (Dine-In, Pickup, Delivery)
                    item {
                        Text(
                            text = "Dining Preference",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Pickup", "Dine-In", "Delivery").forEach { type ->
                                val isSelected = orderType == type
                                Box(
                                    modifier = Modifier
                                        .testTag("order_type_${type.lowercase()}")
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                                        .border(
                                            1.dp,
                                            if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clickable { onOrderTypeChanged(type) }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = type,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Cart Items List
                    item {
                        Text(
                            text = "Selected Creations",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    itemsIndexed(cartItems) { index, item ->
                        CartItemRow(
                            item = item,
                            onIncrement = { onUpdateQuantity(index, item.quantity + 1) },
                            onDecrement = { onUpdateQuantity(index, item.quantity - 1) },
                            onDelete = { onRemoveItem(index) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Promo Code Section
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AuraSurfaceBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Promotional Code",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = promoInput,
                                        onValueChange = { promoInput = it },
                                        placeholder = { Text("e.g. AURA10", fontSize = 12.sp) },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = AuraCrimson,
                                            unfocusedBorderColor = AuraSurfaceBorder
                                        ),
                                        singleLine = true,
                                        modifier = Modifier
                                            .testTag("promo_code_input")
                                            .weight(1f)
                                            .height(48.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { onApplyPromo(promoInput) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = AuraCrimson,
                                            contentColor = AuraWhite
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .testTag("apply_promo_button")
                                            .height(48.dp)
                                    ) {
                                        Text("Apply", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }

                                if (promoMessage != null) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = promoMessage,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (promoDiscount > 0) DietaryGreen else AuraCrimson
                                    )
                                }
                            }
                        }
                    }

                    // Tip Selection
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Culinary & Service Gratuity",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(15, 18, 20, 0).forEach { tipOption ->
                                val isSelected = tipPercentage == tipOption
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                                        .border(
                                            1.dp,
                                            if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { onTipChanged(tipOption) }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (tipOption == 0) "Custom" else "$tipOption%",
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    // Bill Summary Card
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AuraSurfaceBorder)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Order Summary",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                BillRow(label = "Items Subtotal", amount = rawSubtotal)
                                if (promoDiscount > 0) {
                                    BillRow(
                                        label = "VIP Discount (${(promoDiscount * 100).toInt()}%)",
                                        amount = -discount,
                                        isDiscount = true
                                    )
                                }
                                BillRow(label = "Hospitality Gratuity", amount = tip)
                                BillRow(label = "City Dining Tax (8.875%)", amount = tax)

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    color = AuraSurfaceBorder
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Total Amount",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = String.format(Locale.US, "$%.2f", total),
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = AuraCrimson
                                    )
                                }
                            }
                        }
                    }

                    // Checkout Button
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { showCheckoutDialog = true },
                            modifier = Modifier
                                .testTag("proceed_to_checkout_button")
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AuraCrimson,
                                contentColor = AuraWhite
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Proceed to Checkout • " + String.format(Locale.US, "$%.2f", total),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            // Past Orders History
            if (pastOrders.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Previous Orders",
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Serif),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Once you place an order, its live kitchen status will appear here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AuraTextSecondary
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    itemsIndexed(pastOrders) { _, order ->
                        OrderHistoryCard(order = order)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    // Checkout Details Dialog with Comprehensive Global Country, State, and City Selectors
    if (showCheckoutDialog) {
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            title = {
                Text(
                    text = "Complete Your Order",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    ),
                    color = AuraCrimson
                )
            },
            text = {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Text(
                            text = "Fulfilling branch: ${currentBranch.branchName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = AuraCrimson,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Guest Name *") },
                            placeholder = { Text("e.g. Julian Vance") },
                            singleLine = true,
                            modifier = Modifier
                                .testTag("checkout_guest_name_input")
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = customerPhone,
                            onValueChange = { customerPhone = it },
                            label = { Text("Contact Phone *") },
                            placeholder = { Text("+1 (212) 555-0199 / +91 98765 43210") },
                            singleLine = true,
                            modifier = Modifier
                                .testTag("checkout_guest_phone_input")
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (orderType == "Delivery") {
                            // Country Selection Dropdown
                            ExposedDropdownMenuBox(
                                expanded = countryDropdownExpanded,
                                onExpandedChange = { countryDropdownExpanded = !countryDropdownExpanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedCountry,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Delivery Country *") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = countryDropdownExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = countryDropdownExpanded,
                                    onDismissRequest = { countryDropdownExpanded = false }
                                ) {
                                    GlobalLocations.allCountries.forEach { country ->
                                        DropdownMenuItem(
                                            text = { Text(country) },
                                            onClick = {
                                                selectedCountry = country
                                                selectedState = GlobalLocations.getStatesForCountry(country).firstOrNull() ?: ""
                                                countryDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // State / Province Selection Dropdown
                            val statesList = GlobalLocations.getStatesForCountry(selectedCountry)
                            ExposedDropdownMenuBox(
                                expanded = stateDropdownExpanded,
                                onExpandedChange = { stateDropdownExpanded = !stateDropdownExpanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedState,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("State / Province / Region *") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = stateDropdownExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = stateDropdownExpanded,
                                    onDismissRequest = { stateDropdownExpanded = false }
                                ) {
                                    statesList.forEach { state ->
                                        DropdownMenuItem(
                                            text = { Text(state) },
                                            onClick = {
                                                selectedState = state
                                                stateDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Global City Selection / Input
                            ExposedDropdownMenuBox(
                                expanded = cityDropdownExpanded,
                                onExpandedChange = { cityDropdownExpanded = !cityDropdownExpanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedCity,
                                    onValueChange = { selectedCity = it },
                                    label = { Text("City *") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityDropdownExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = cityDropdownExpanded,
                                    onDismissRequest = { cityDropdownExpanded = false }
                                ) {
                                    GlobalLocations.majorGlobalCities.take(25).forEach { city ->
                                        DropdownMenuItem(
                                            text = { Text(city) },
                                            onClick = {
                                                selectedCity = city
                                                cityDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = streetAddress,
                                onValueChange = { streetAddress = it },
                                label = { Text("Street Address & Apt/Suite *") },
                                placeholder = { Text("e.g. 120 Ocean Avenue, Apt 4B") },
                                singleLine = true,
                                modifier = Modifier
                                    .testTag("checkout_street_address_input")
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                            )
                        } else {
                            OutlinedTextField(
                                value = tableOrPickupTime,
                                onValueChange = { tableOrPickupTime = it },
                                label = {
                                    Text(if (orderType == "Dine-In") "Table Number (e.g. Table 4)" else "Pick-Up Time (e.g. 7:45 PM)")
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .testTag("checkout_location_input")
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = orderNotes,
                            onValueChange = { orderNotes = it },
                            label = { Text("Allergy alert or kitchen instructions") },
                            placeholder = { Text("e.g. Extra napkins, peanut allergy, Jain prep") },
                            singleLine = false,
                            maxLines = 2,
                            modifier = Modifier
                                .testTag("checkout_order_notes_input")
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val formattedDestination = if (orderType == "Delivery") {
                            "${streetAddress.ifBlank { "Delivery Address" }}, $selectedCity, $selectedState, $selectedCountry"
                        } else {
                            "$tableOrPickupTime • ${currentBranch.branchName}"
                        }
                        onPlaceOrder(customerName, customerPhone, formattedDestination, orderNotes)
                        showCheckoutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AuraCrimson,
                        contentColor = AuraWhite
                    ),
                    modifier = Modifier.testTag("confirm_place_order_button")
                ) {
                    Text("Place Order (${String.format(Locale.US, "$%.2f", total)})", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showCheckoutDialog = false }) {
                    Text("Cancel", color = AuraCrimson)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    // Order Placed Success Confirmation Dialog
    if (placedOrderDialog != null) {
        AlertDialog(
            onDismissRequest = onDismissOrderDialog,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = DietaryGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Order Received!",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Order Reference: ${placedOrderDialog.orderNumber}",
                        fontWeight = FontWeight.Bold,
                        color = AuraCrimson,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Thank you, ${placedOrderDialog.customerName}. Your order is currently being prepared with royal culinary precision in our kitchen.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AuraTextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(
                                text = "Estimated Kitchen Time: 20-30 minutes",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Type: ${placedOrderDialog.orderType} • ${placedOrderDialog.tableOrAddress}",
                                fontSize = 11.sp,
                                color = AuraTextSecondary
                            )
                            Text(
                                text = "Total: " + String.format(Locale.US, "$%.2f", placedOrderDialog.total),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AuraCrimson
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismissOrderDialog,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AuraCrimson,
                        contentColor = AuraWhite
                    )
                ) {
                    Text("Understood")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AuraSurfaceBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.menuItem.imageRes),
                contentDescription = item.menuItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.menuItem.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (item.selectedCustomization.isNotBlank()) {
                    Text(
                        text = item.selectedCustomization,
                        fontSize = 11.sp,
                        color = AuraCrimson
                    )
                }
                if (item.specialNotes.isNotBlank()) {
                    Text(
                        text = "Note: ${item.specialNotes}",
                        fontSize = 10.sp,
                        color = AuraTextMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = String.format(Locale.US, "$%.2f", item.totalPrice),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AuraCrimson
                )
            }

            // Stepper
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(2.dp)
            ) {
                IconButton(onClick = onDecrement, modifier = Modifier.size(30.dp)) {
                    Icon(
                        imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
                        contentDescription = "Decrease",
                        tint = if (item.quantity == 1) AuraCrimson else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = item.quantity.toString(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = onIncrement, modifier = Modifier.size(30.dp)) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BillRow(label: String, amount: Double, isDiscount: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isDiscount) DietaryGreen else AuraTextSecondary
        )
        Text(
            text = String.format(Locale.US, "$%.2f", amount),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = if (isDiscount) DietaryGreen else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun OrderHistoryCard(order: OrderEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AuraSurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.orderNumber,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = AuraCrimson
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(AuraCrimsonSoft)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = order.status,
                        color = AuraCrimsonDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = order.itemsSummary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${order.orderType} • ${order.tableOrAddress}",
                    fontSize = 11.sp,
                    color = AuraTextMuted
                )

                Text(
                    text = String.format(Locale.US, "$%.2f", order.total),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
