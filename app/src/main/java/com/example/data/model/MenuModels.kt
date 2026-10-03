package com.example.data.model

import androidx.annotation.DrawableRes

enum class MenuCategory(val displayName: String) {
    ALL("All Dishes"),
    INDIAN("Royal Indian"),
    STARTERS("Starters"),
    MAINS("Mains"),
    GRILL("From the Grill"),
    DESSERTS("Desserts"),
    COCKTAILS("Cocktails & Wine")
}

enum class Allergen(val displayName: String, val shortLabel: String) {
    GLUTEN("Gluten", "GLU"),
    DAIRY("Dairy", "MILK"),
    SHELLFISH("Shellfish", "SHELL"),
    FISH("Fish", "FISH"),
    TREE_NUTS("Tree Nuts", "NUTS"),
    PEANUTS("Peanuts", "PNT"),
    EGGS("Eggs", "EGG"),
    SOY("Soy", "SOY"),
    SESAME("Sesame", "SES")
}

enum class DietaryTag(val displayName: String) {
    GLUTEN_FREE("Gluten-Free"),
    DAIRY_FREE("Dairy-Free"),
    NUT_FREE("Nut-Free"),
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    HALAL("Halal-Friendly")
}

data class MenuItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val description: String,
    val price: Double,
    val category: MenuCategory,
    val allergens: List<Allergen> = emptyList(),
    val dietaryTags: List<DietaryTag> = emptyList(),
    @DrawableRes val imageRes: Int,
    val calories: Int,
    val ingredients: List<String>,
    val isChefSpecial: Boolean = false,
    val customizationOptions: List<String> = emptyList()
)

data class CartItem(
    val menuItem: MenuItem,
    val quantity: Int = 1,
    val selectedCustomization: String = "",
    val specialNotes: String = ""
) {
    val totalPrice: Double get() = menuItem.price * quantity
}
