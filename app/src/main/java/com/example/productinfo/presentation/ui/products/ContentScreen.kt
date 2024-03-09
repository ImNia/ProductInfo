package com.example.productinfo.presentation.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.productinfo.R
import com.example.productinfo.domain.models.Product
import com.example.productinfo.ui.theme.Typography

@Composable
fun ProductsScreenContent(
    innerPadding: PaddingValues,
    products: List<Product>,
    onEvent: (ProductsEvent) -> Unit,
    isLoading: Boolean = false,
) {
    Column {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background))
                .padding(innerPadding)
                .padding(bottom = 16.dp),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalItemSpacing = 16.dp
        ) {
            items(products.size) { index ->
                if (index >= products.size - 1 && !isLoading) {
                    onEvent(ProductsEvent.OnLoading)
                }
                ProductItem(
                    product = products[index]
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.white))
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            placeholder = painterResource(id = R.drawable.ic_image),
            model = product.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = product.title,
            maxLines = 2,
            textAlign = TextAlign.Start,
            style = Typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = product.description,
            maxLines = 4,
            textAlign = TextAlign.Justify,
            style = Typography.bodySmall
        )
    }
}