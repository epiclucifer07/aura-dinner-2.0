package com.example.data.repository

import com.example.R
import com.example.data.model.Allergen
import com.example.data.model.DietaryTag
import com.example.data.model.MenuCategory
import com.example.data.model.MenuItem

object MenuData {
    val sampleDishes: List<MenuItem> = listOf(
        MenuItem(
            id = "starter_1",
            name = "Pugliese Burrata & Heirloom",
            subtitle = "Aged Balsamic & Emerald Basil",
            description = "Silky artisanal pugliese burrata surrounded by vine-ripened heirloom tomatoes, cold-pressed basil oil, 25-year aged Modena balsamic, and sea salt focaccia crisp.",
            price = 24.00,
            category = MenuCategory.STARTERS,
            allergens = listOf(Allergen.DAIRY, Allergen.GLUTEN),
            dietaryTags = listOf(DietaryTag.VEGETARIAN),
            imageRes = R.drawable.img_dish_burrata,
            calories = 420,
            ingredients = listOf("Pugliese Burrata", "Organic Heirloom Tomatoes", "Aged Modena Balsamic", "Cold-Pressed Basil Oil", "Focaccia Crisp", "Maldon Sea Salt"),
            isChefSpecial = true,
            customizationOptions = listOf("Standard", "Gluten-Free Cracker", "Extra Basil Emulsion", "Nut-Free Kitchen Prep")
        ),
        MenuItem(
            id = "starter_2",
            name = "Hokkaido Pan-Seared Scallops",
            subtitle = "Saffron Emulsion & Royal Caviar",
            description = "Caramelized wild Hokkaido sea scallops served over a velvety saffron velouté, finished with imperial Ossetra caviar and fresh edible marigold blossoms.",
            price = 32.00,
            category = MenuCategory.STARTERS,
            allergens = listOf(Allergen.SHELLFISH, Allergen.DAIRY),
            dietaryTags = listOf(DietaryTag.GLUTEN_FREE, DietaryTag.HALAL),
            imageRes = R.drawable.img_dish_scallops,
            calories = 360,
            ingredients = listOf("Wild Hokkaido Scallops", "Spanish Saffron", "Sweet Butter Emulsion", "Ossetra Caviar", "Chive Oil", "Micro Marigold"),
            isChefSpecial = true,
            customizationOptions = listOf("Standard", "Dairy-Free Olive Oil Emulsion", "Extra Caviar (+ $12)", "No Chives")
        ),
        MenuItem(
            id = "starter_3",
            name = "Wild Truffle Arancini",
            subtitle = "Crisp Risotto & Parmigiano Fonduta",
            description = "Crisp golden saffron carnaroli risotto spheres stuffed with wild winter truffles and smoked scamorza, perched on a 24-month Parmigiano-Reggiano fondue.",
            price = 22.00,
            category = MenuCategory.STARTERS,
            allergens = listOf(Allergen.GLUTEN, Allergen.DAIRY, Allergen.EGGS),
            dietaryTags = listOf(DietaryTag.VEGETARIAN),
            imageRes = R.drawable.img_hero_restaurant,
            calories = 480,
            ingredients = listOf("Carnaroli Rice", "Black Winter Truffle", "Smoked Scamorza", "Parmigiano-Reggiano", "Herb Panko", "Organic Eggs"),
            isChefSpecial = false,
            customizationOptions = listOf("Standard", "Extra Truffle Fonduta", "Light Salt")
        ),
        MenuItem(
            id = "main_1",
            name = "Miyazaki A5 Wagyu Medallion",
            subtitle = "Perigord Truffle & Morel Velvet",
            description = "Prime Japanese A5 Wagyu striploin seared to tender perfection over Japanese binchotan charcoal. Accompanied by silk potato mousseline and glazed morel mushroom jus.",
            price = 92.00,
            category = MenuCategory.GRILL,
            allergens = listOf(Allergen.DAIRY),
            dietaryTags = listOf(DietaryTag.GLUTEN_FREE, DietaryTag.HALAL),
            imageRes = R.drawable.img_dish_wagyu,
            calories = 690,
            ingredients = listOf("Miyazaki A5 Wagyu", "French Morel Mushrooms", "Ratte Potato Puree", "Black Perigord Truffle", "Bordelaise Reduction", "Fleur de Sel"),
            isChefSpecial = true,
            customizationOptions = listOf("Medium Rare (Recommended)", "Rare", "Medium", "Truffle Jus on the Side", "Dairy-Free Potato Puree")
        ),
        MenuItem(
            id = "main_2",
            name = "Chilean Glacier Sea Bass",
            subtitle = "Sweet White Miso & Dashi Fumet",
            description = "Pan-roasted sustainably sourced Glacier 51 toothfish brushed with Kyoto saikyo miso glaze, paired with braised baby bok choy and aromatic ginger-scented dashi fumet.",
            price = 58.00,
            category = MenuCategory.MAINS,
            allergens = listOf(Allergen.FISH, Allergen.SOY),
            dietaryTags = listOf(DietaryTag.GLUTEN_FREE, DietaryTag.DAIRY_FREE, DietaryTag.HALAL),
            imageRes = R.drawable.img_dish_scallops,
            calories = 510,
            ingredients = listOf("Glacier 51 Chilean Sea Bass", "Kyoto White Miso", "Charred Baby Bok Choy", "Shiitake Dashi Fumet", "Young Ginger Oil"),
            isChefSpecial = false,
            customizationOptions = listOf("Standard", "Mild Glaze", "Extra Bok Choy", "Low Sodium")
        ),
        MenuItem(
            id = "main_3",
            name = "Perigord Morel Risotto",
            subtitle = "Aged Carnaroli & Winter Truffle",
            description = "Slowly simmered seven-year aged Acquerello carnaroli rice with mountain butter, wild morel mushrooms, shallots, fresh thyme, and shaved fresh black winter truffle.",
            price = 38.00,
            category = MenuCategory.MAINS,
            allergens = listOf(Allergen.DAIRY),
            dietaryTags = listOf(DietaryTag.GLUTEN_FREE, DietaryTag.VEGETARIAN),
            imageRes = R.drawable.img_dish_burrata,
            calories = 540,
            ingredients = listOf("Acquerello Rice", "French Morel Mushrooms", "Black Winter Truffle", "Fontina Cheese", "Shallots", "Fresh Thyme"),
            isChefSpecial = false,
            customizationOptions = listOf("Standard", "Dairy-Free Vegan Preparation", "Extra Shaved Truffle (+ $14)")
        ),
        MenuItem(
            id = "main_4",
            name = "Dry-Aged Tomahawk Ribeye",
            subtitle = "45-Day Dry Aged prime cut (For Two)",
            description = "Smoked over oak and finished on high fire, served with roasted bone marrow butter, chimichurri sauce, and hand-cut truffle frites.",
            price = 145.00,
            category = MenuCategory.GRILL,
            allergens = listOf(Allergen.DAIRY),
            dietaryTags = listOf(DietaryTag.GLUTEN_FREE, DietaryTag.NUT_FREE),
            imageRes = R.drawable.img_dish_wagyu,
            calories = 1180,
            ingredients = listOf("Prime Beef Tomahawk", "Roasted Bone Marrow", "Garlic Butter", "Herb Chimichurri", "Sea Salt"),
            isChefSpecial = false,
            customizationOptions = listOf("Medium Rare", "Medium", "Charred Crust", "Sauces on the Side")
        ),
        MenuItem(
            id = "dessert_1",
            name = "Valrhona Noir Truffle Dome",
            subtitle = "70% Guanaja & Salted Caramel",
            description = "A mirror-glazed dome of single-origin 70% dark chocolate mousse, molten fleur de sel caramel core, crunchy hazelnut feuilletine, and edible 24k gold leaf.",
            price = 20.00,
            category = MenuCategory.DESSERTS,
            allergens = listOf(Allergen.DAIRY, Allergen.EGGS, Allergen.TREE_NUTS, Allergen.GLUTEN),
            dietaryTags = listOf(DietaryTag.VEGETARIAN),
            imageRes = R.drawable.img_hero_restaurant,
            calories = 490,
            ingredients = listOf("Valrhona 70% Guanaja Chocolate", "Guérande Sea Salt Caramel", "Piedmont Hazelnut Praline", "Feuilletine", "24k Gold Leaf"),
            isChefSpecial = true,
            customizationOptions = listOf("Standard", "Nut-Free Variant", "Extra Caramel Sauce")
        ),
        MenuItem(
            id = "dessert_2",
            name = "Yuzu & Citrus Sorbet Trio",
            subtitle = "Shiso Infusion & Candied Peel",
            description = "Chilled trio of Japanese yuzu, Meyer lemon, and Sicilian blood orange sorbets resting on crushed candied citrus tuile and fresh micro shiso leaves.",
            price = 16.00,
            category = MenuCategory.DESSERTS,
            allergens = emptyList(),
            dietaryTags = listOf(DietaryTag.VEGAN, DietaryTag.GLUTEN_FREE, DietaryTag.DAIRY_FREE, DietaryTag.NUT_FREE, DietaryTag.HALAL),
            imageRes = R.drawable.img_dish_burrata,
            calories = 210,
            ingredients = listOf("Japanese Yuzu Juice", "Meyer Lemon Puree", "Sicilian Blood Orange", "Organic Agave", "Fresh Green Shiso"),
            isChefSpecial = false,
            customizationOptions = listOf("Standard", "All Yuzu", "No Shiso")
        ),
        MenuItem(
            id = "cocktail_1",
            name = "Aura Smoked Old Fashioned",
            subtitle = "Cherrywood Smoke & Artisanal Bourbon",
            description = "Kentucky small-batch bourbon infused with Madagascar vanilla, Angostura & orange bitters, flamed peel, cold-smoked tableside under a glass cloche with cherrywood.",
            price = 22.00,
            category = MenuCategory.COCKTAILS,
            allergens = emptyList(),
            dietaryTags = listOf(DietaryTag.VEGAN, DietaryTag.GLUTEN_FREE, DietaryTag.NUT_FREE),
            imageRes = R.drawable.img_hero_restaurant,
            calories = 190,
            ingredients = listOf("Small Batch Bourbon", "Angostura Bitters", "Blood Orange Peel", "Demerara Syrup", "Cherrywood Smoke"),
            isChefSpecial = true,
            customizationOptions = listOf("Smoked", "Unsmoked", "Light Bitters")
        ),
        MenuItem(
            id = "cocktail_2",
            name = "Sparkling Rosemary Pear (Zero-Proof)",
            subtitle = "Charred Rosemary & Mountain Tonic",
            description = "Hand-pressed Bartlett pear nectar, artisanal elderflower tonic, charred rosemary sprig, and wildflower honey syrup. Exquisite non-alcoholic refreshment.",
            price = 15.00,
            category = MenuCategory.COCKTAILS,
            allergens = emptyList(),
            dietaryTags = listOf(DietaryTag.VEGAN, DietaryTag.GLUTEN_FREE, DietaryTag.NUT_FREE, DietaryTag.HALAL),
            imageRes = R.drawable.img_dish_scallops,
            calories = 120,
            ingredients = listOf("Bartlett Pear Nectar", "Fever-Tree Tonic", "Charred Rosemary", "Wildflower Honey", "Meyer Lemon Spritz"),
            isChefSpecial = false,
            customizationOptions = listOf("Standard", "Less Sweet", "Extra Soda")
        )
    )
}
