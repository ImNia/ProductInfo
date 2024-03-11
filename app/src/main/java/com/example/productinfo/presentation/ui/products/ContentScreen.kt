package com.example.productinfo.presentation.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.productinfo.R
import com.example.productinfo.domain.models.Categories
import com.example.productinfo.domain.models.Product
import com.example.productinfo.ui.theme.CategoryColor
import com.example.productinfo.ui.theme.CategorySelectedColor
import com.example.productinfo.ui.theme.Typography

@Composable
fun ProductsScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    products: List<Product>,
    categories: Categories?,
    selectedCategory: String? = null,
    query: String = "",
    onEvent: (ProductsEvent) -> Unit,
    isLoading: Boolean = false,
    existError: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalItemSpacing = 16.dp
        ) {
            item (span = StaggeredGridItemSpan.FullLine) {
                SearchBar(
                    query = query,
                    onEnterValue = { query ->
                        onEvent(ProductsEvent.OnSearch(query.trim()))
                    }
                )
            }

            if(categories != null && query.trim() == "") {
                item (span = StaggeredGridItemSpan.FullLine) {
                    CategoriesRow(
                        modifier = Modifier.padding(vertical = 16.dp),
                        categories = categories,
                        selected = selectedCategory,
                        onClick = { category ->
                            onEvent(ProductsEvent.OnCategorySelect(category))
                        },
                    )
                }
            }

            if (products.isEmpty() && query.trim() != "" && !isLoading) {
                item (span = StaggeredGridItemSpan.FullLine) {
                    EmptyScreen()
                }
            } else {
                items(products.size) { index ->
                    if (index >= products.size - 1 && !isLoading && !existError) {
                        onEvent(ProductsEvent.OnLoading)
                    }
                    ProductItem(
                        product = products[index],
                        onClick = {
                            navController.navigate(
                                "productDetailScreen/${products[index].id}"
                            )
                        }
                    )
                }
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
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.background))
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
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

@Composable
fun CategoriesRow(
    modifier: Modifier = Modifier,
    categories: Categories,
    selected: String?,
    onClick: (String) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        categories.categories.forEach { category ->
            item {
                Text(
                    modifier = modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (category.trim() == selected?.trim()) CategorySelectedColor else CategoryColor)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onClick.invoke(category) },
                    text = category,
                    style = Typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String = "",
    onEnterValue: (String) -> Unit,
) {
    var value by remember { mutableStateOf(query) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CategoryColor)
            .padding(horizontal = 16.dp),
        placeholder = {
            Text(text = "Search")
        },
        value = value,
        onValueChange = {
            value = it
        },
        textStyle = Typography.bodyMedium,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onEnterValue.invoke(value)
            }
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        trailingIcon = {
            if(value.isNotBlank()) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            value = ""
                            onEnterValue.invoke(value)
                        },
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        )
    )
}