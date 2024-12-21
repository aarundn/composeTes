/*
 * Copyright 2020 The Android Open Source Project
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

package com.example.challenge6_.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challenge6_.base.CraneDrawer
import com.example.challenge6_.base.CraneTabBar
import com.example.challenge6_.base.CraneTabs
import com.example.challenge6_.base.ExploreSection
import com.example.challenge6_.data.ExploreModel
import kotlinx.coroutines.launch

typealias OnExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen {
    Fly, Sleep, Eat
}

@Composable
fun CraneHome(
    onExploreItemClicked: OnExploreItemClicked,
    modifier: Modifier = Modifier,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            CraneDrawer()
        },
        modifier = Modifier.statusBarsPadding()
    ) {
        Scaffold(
            modifier = Modifier,
        ) { padding ->
            CraneHomeContent(
                modifier = Modifier.padding(padding),
                onExploreItemClicked = onExploreItemClicked,
                openDrawer = {
                    scope.launch {
                        // Open the navigation drawer
                        drawerState.open()
                    }
                }
            )
        }
    }
}

@Composable
fun CraneHomeContent(
    onExploreItemClicked: OnExploreItemClicked,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val suggestedDestinations: List<ExploreModel> = remember { emptyList() }
    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }
    var tabSelected by remember { mutableStateOf(CraneScreen.Fly) }

    val backLayerVisible = remember { mutableStateOf(true) } // Tracks back/front layer visibility

    Scaffold(
        topBar = {
            HomeTabBar(
                openDrawer = openDrawer,
                tabSelected = tabSelected,
                onTabSelected = { tabSelected = it }
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (backLayerVisible.value) {
                // Back layer content
                SearchContent(
                    tabSelected = tabSelected,
                    viewModel = viewModel,
                    onPeopleChanged = onPeopleChanged
                )
            }

            // Front layer content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (tabSelected) {
                    CraneScreen.Fly -> {
                        ExploreSection(
                            title = "Explore Flights by Destination",
                            exploreList = suggestedDestinations,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                    CraneScreen.Sleep -> {
                        ExploreSection(
                            title = "Explore Properties by Destination",
                            exploreList = viewModel.hotels,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                    CraneScreen.Eat -> {
                        ExploreSection(
                            title = "Explore Restaurants by Destination",
                            exploreList = viewModel.restaurants,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun HomeTabBar(
    openDrawer: () -> Unit,
    tabSelected: CraneScreen,
    onTabSelected: (CraneScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    CraneTabBar(
        modifier = modifier,
        onMenuClicked = openDrawer
    ) { tabBarModifier ->
        CraneTabs(
            modifier = tabBarModifier,
            titles = CraneScreen.entries.map { it.name },
            tabSelected = tabSelected,
            onTabSelected = { newTab: CraneScreen ->
                onTabSelected(CraneScreen.entries[newTab.ordinal])
            }
        )
    }
}

@Composable
private fun SearchContent(
    tabSelected: CraneScreen,
    viewModel: MainViewModel,
    onPeopleChanged: (Int) -> Unit
) {
    when (tabSelected) {
        CraneScreen.Fly -> FlySearchContent(
            onPeopleChanged = onPeopleChanged,
            onToDestinationChanged = { viewModel.toDestinationChanged(it) }
        )
        CraneScreen.Sleep -> SleepSearchContent(
            onPeopleChanged = onPeopleChanged
        )
        CraneScreen.Eat -> EatSearchContent(
            onPeopleChanged = onPeopleChanged
        )
    }
}
