package com.chaeros.lemonade.data.lemonade

import com.chaeros.lemonade.R

data class LemonStep(
    val imageResource: Int,
    val contentResource: Int,
    val imageDescriptionResource: Int
)

val lemonSteps = listOf(
    LemonStep(
        R.drawable.lemon_tree,
        R.string.lemon_tree_content,
        R.string.lemon_tree_description
    ),
    LemonStep(
        R.drawable.lemon_squeeze,
        R.string.lemon_content,
        R.string.lemon_description
    ),
    LemonStep(
        R.drawable.lemon_drink,
        R.string.glass_of_lemonade_content,
        R.string.glass_of_lemonade_description
    ),
    LemonStep(
        R.drawable.lemon_restart,
        R.string.empty_glass_content,
        R.string.empty_glass_description
    )
)