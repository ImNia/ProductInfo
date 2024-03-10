package com.example.productinfo.presentation.ui.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.productinfo.R
import com.example.productinfo.domain.models.Product
import com.example.productinfo.ui.theme.RatingColor
import com.example.productinfo.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetail(
    modifier: Modifier = Modifier,
    product: Product,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            product.images.size
        }
    )
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    placeholder = painterResource(id = R.drawable.ic_image),
                    model = product.images[page],
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )
            }

            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = product.title,
                style = Typography.titleMedium,
                color = Color.Black
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(id = R.string.price_text, product.price.toString()),
                style = Typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(RatingColor)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(id = R.string.rating_text, product.rating.toString()),
                style = Typography.bodySmall,
                color = Color.Black,
            )
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.description_text),
                style = Typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = product.description,
                style = Typography.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Justify,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}