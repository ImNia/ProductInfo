package com.example.productinfo.presentation.ui.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.productinfo.R
import com.example.productinfo.domain.models.ErrorType
import com.example.productinfo.ui.theme.Typography

@Composable
fun ErrorScreen(
    errorType: ErrorType,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = when (errorType) {
                ErrorType.NOT_CONNECT -> {
                    painterResource(id = R.drawable.ic_not_connect)
                }

                ErrorType.SERVER_ERROR -> {
                    painterResource(id = R.drawable.ic_error)
                }

                ErrorType.UNKNOWN -> {
                    painterResource(id = R.drawable.ic_error)
                }
            },
            contentDescription = null
        )
        Text(
            text = when (errorType) {
                ErrorType.NOT_CONNECT -> {
                    stringResource(id = R.string.error_not_connect)
                }

                ErrorType.SERVER_ERROR -> {
                    stringResource(id = R.string.error_server)
                }

                ErrorType.UNKNOWN -> {
                    stringResource(id = R.string.error_unknown)
                }
            },
            style = Typography.bodyMedium
        )
    }

    Button(onClick = { onClick.invoke() }) {
        Text(
            text = stringResource(id = R.string.error_button),
            style = Typography.bodyMedium
        )
    }
}