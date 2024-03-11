package com.example.productinfo.presentation.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.productinfo.R
import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.ui.theme.Typography

@Composable
fun ProductDetailScreen(
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(colorResource(id = R.color.white))
                    .padding(vertical = 10.dp),
            ) {
                Image(
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_back) ,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 32.dp)
                        .align(Alignment.CenterVertically),
                    text = state.value.product?.title ?: "",
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
                    viewModel.onEvent(ProductDetailEvent.OnHideAlert)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.onEvent(ProductDetailEvent.OnLoading)
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
        state.value.product?.let { product ->
            ProductDetail(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white)),
                product = product,
                isLoading = state.value.isLoading
            )
        }
    }
}