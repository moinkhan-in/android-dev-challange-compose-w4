/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
