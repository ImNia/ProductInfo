package com.example.productinfo.presentation.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.productinfo.R
import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.ui.theme.Typography

@Composable
fun ProductsScreen(
    navController: NavController,
    viewModel: ProductsScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(colorResource(id = R.color.white))
                    .padding(vertical = 10.dp),
                text = stringResource(id = R.string.products_title),
                style = Typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
    ) { innerPadding ->
        if (state.value.error != null) {
            AlertDialog(
                title = {
                    Text(
                        text = stringResource(id = R.string.error_title),
                        style = Typography.bodyMedium
                    )
                },
                text = {
                    Text(
                        text = when (state.value.error!!) {
                            ErrorType.NOT_CONNECT -> {
                                stringResource(id = R.string.error_not_connect)
                            }

                            ErrorType.SERVER_ERROR -> {
                                stringResource(id = R.string.error_server)
                            }

                            ErrorType.REQUEST_TIMEOUT -> {
                                stringResource(id = R.string.error_not_connect)
                            }

                            ErrorType.UNKNOWN -> {
                                stringResource(id = R.string.error_unknown)
                            }
                        },
                        style = Typography.bodyMedium
                    )
                },
                onDismissRequest = {
                    viewModel.onEvent(ProductsEvent.OnHideAlert)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.onEvent(ProductsEvent.OnLoading)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_button),
                            style = Typography.bodyMedium
                        )
                    }
                }
            )
        }
        ProductsScreenContent(
            modifier = Modifier
                .padding(innerPadding),
            navController = navController,
            products = state.value.products,
            categories = state.value.categories,
            selectedCategory = state.value.selectedCategories,
            onEvent = viewModel::onEvent,
            isLoading = state.value.isLoading,
            existError = state.value.existError
        )
    }
}