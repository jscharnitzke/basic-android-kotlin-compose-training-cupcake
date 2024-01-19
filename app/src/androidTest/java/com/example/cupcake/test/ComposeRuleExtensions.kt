package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithCapitalizedStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id).uppercase())

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int,
    vararg formatArgs: Any?
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id, *formatArgs))

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithDrawableId(
    @DrawableRes id: Int
): SemanticsNodeInteraction = onNodeWithTag(id.toString())
