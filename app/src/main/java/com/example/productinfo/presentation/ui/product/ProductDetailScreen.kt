package com.example.productinfo.presentation.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.productinfo.R
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
                    .background(colorResource(id = R.color.background))
                    .padding(top = 16.dp)
                    .height(40.dp),
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
                        .align(Alignment.CenterVertically),
                    text = state.value.product?.title ?: "",
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { innerPadding ->

        state.value.product?.let { product ->
            ProductDetail(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(colorResource(id = R.color.background)),
                product = product
            )
        }

    }
    
}