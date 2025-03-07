package com.gnua_aruht.weather_compose.presentation.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import java.lang.Exception


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Home Screen") })
        },
        content = {
            val contentModifier = Modifier.padding(it)
            if (state.data != null) {
                DataContent(modifier = contentModifier)
            } else {
                if (state.exception != null) {
                    ErrorContent(
                        exception = state.exception!!,
                        modifier = contentModifier
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
            }
        }
    )
}

@Composable
fun DataContent(modifier: Modifier = Modifier) {
    Text(
        "Success content",
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
    )
}


@Composable
fun ErrorContent(
    exception: Exception,
    modifier: Modifier = Modifier) {

    Text(
        "Error content",
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
    )
}


