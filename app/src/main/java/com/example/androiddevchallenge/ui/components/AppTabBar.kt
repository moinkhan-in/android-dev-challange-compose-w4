package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.androiddevchallenge.AppScene

@Composable
fun AppTabBar(
    modifier: Modifier = Modifier,
    onTabItemSelected: (AppScene) -> Unit,
    buttonColor: Color
) {
    val tabItems = arrayListOf(AppScene.Temperature, AppScene.Wind, AppScene.Precipitation)

    var tabSelectedIndex by remember { mutableStateOf(0) }
    TabRow(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 8.dp),
        selectedTabIndex = tabSelectedIndex,
        backgroundColor = MaterialTheme.colors.surface,
        divider = { TabRowDefaults.Divider(thickness = 0.dp) },
        indicator = { tabPositions ->
            Box(
                modifier
                    .tabIndicatorOffset(tabPositions[tabSelectedIndex])
                    .zIndex(0f)
                    .background(
                        color = buttonColor,
                        shape = MaterialTheme.shapes.large
                    )
                    .height(34.dp)
                    .fillMaxWidth()
            )
        },
    ) {
        tabItems.forEachIndexed { index, item ->

            val isSelected = index == tabSelectedIndex
            Tab(
                modifier = Modifier.zIndex(1f),
                selected = isSelected,
                onClick = {
                    tabSelectedIndex = index
                    onTabItemSelected.invoke(item)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black.copy(alpha = 0.5f)
            ) {

                Text(
                    text = stringResource(item.toDisplay()),
                    modifier = modifier.padding(8.dp),
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

@Composable
@Preview
private fun TabBarPreview() {
    AppTabBar(onTabItemSelected = { /*TODO*/ }, buttonColor = Color.Blue)
}