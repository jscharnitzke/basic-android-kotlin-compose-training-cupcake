package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.R
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        flavors.forEach {flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, subtotal),
        ).assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    @Test
    fun startScreen_verifyContent() {
        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {}
            )
        }

        composeTestRule.onNodeWithDrawableId(R.drawable.cupcake).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.order_cupcakes).assertIsDisplayed()

        DataSource.quantityOptions.forEach {option ->
            composeTestRule.onNodeWithStringId(option.first).assertIsDisplayed()
        }
    }

    @Test
    fun summaryScreen_verifyContent() {
        val orderUiState: OrderUiState =  OrderUiState(
            6,
            "Chocolate",
            "Jan 1",
            "$100",
        )

        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = orderUiState,
                onCancelButtonClicked = {},
                onSendButtonClicked = {subject: String, content: String ->},
            )
        }

        val resources = composeTestRule.activity.resources
        val numberOfCupcakes = resources.getQuantityString(
            R.plurals.cupcakes,
            orderUiState.quantity,
            orderUiState.quantity
        )

        // each line in the summary list is shown
        composeTestRule.onNodeWithCapitalizedStringId(R.string.quantity).assertIsDisplayed()
        composeTestRule.onNodeWithText(numberOfCupcakes).assertIsDisplayed()
        composeTestRule.onNodeWithCapitalizedStringId(R.string.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithCapitalizedStringId(R.string.pickup_date).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.date).assertIsDisplayed()

        // subtotal price is shown
        composeTestRule.onNodeWithStringId(
            R.string.subtotal_price,
            orderUiState.price
        ).assertIsDisplayed()

        // share and cancel buttons are shown
        composeTestRule.onNodeWithStringId(R.string.send).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cancel).assertIsDisplayed()
    }
}