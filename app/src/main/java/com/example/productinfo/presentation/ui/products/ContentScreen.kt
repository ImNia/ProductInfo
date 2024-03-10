package com.example.productinfo.presentation.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.productinfo.R
import com.example.productinfo.domain.models.Product
import com.example.productinfo.ui.theme.Typography

@Composable
fun ProductsScreenContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onEvent: (ProductsEvent) -> Unit,
    isLoading: Boolean = false,
    existError: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(bottom = 16.dp),
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(colorResource(id = R.color.background)),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalItemSpacing = 16.dp
        ) {
            items(products.size) { index ->
                if (index >= products.size - 1 && !isLoading && !existError) {
                    onEvent(ProductsEvent.OnLoading)
                }
                ProductItem(
                    product = products[index]
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp, 32.dp)
                        )
                    }
                }
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                if (existError) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            onEvent(ProductsEvent.OnLoading)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_button),
                            style = Typography.bodyMedium
                        )
                    }
                }
            }
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