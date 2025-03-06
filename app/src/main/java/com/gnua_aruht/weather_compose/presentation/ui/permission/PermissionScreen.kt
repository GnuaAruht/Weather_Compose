package com.gnua_aruht.weather_compose.presentation.ui.permission

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gnua_aruht.weather_compose.R
import com.gnua_aruht.weather_compose.data.utils.PermissionManager


@Composable
fun PermissionScreen(
    modifier: Modifier = Modifier,
    viewModel: PermissionViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val requestLocationPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { perms ->
        if (PermissionManager.APP_PERMISSIONS.all { permission -> perms[permission] == true }) {
            viewModel.onPermissionChange()
        } else {
            viewModel.updateShouldShowRationale(PermissionManager.shouldShowRationale(context.activity()))
        }
    }

    if (state.shouldShowRationale == true) {
        LocationPermissionExplanationDialog(
            onConfirm = { viewModel.updateShouldShowRationale(null) },
            onDismiss = { viewModel.updateShouldShowRationale(false) }
        )
    }

    LaunchedEffect(state.shouldShowRationale) {
        if (state.shouldShowRationale == null) {
            requestLocationPermission.launch(PermissionManager.APP_PERMISSIONS)
        }
    }

    Scaffold(
        modifier = modifier,
        content = {

            val contentModifier = Modifier
                .padding(it)
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)

            if (state.shouldShowRationale == false) {
                OpenApSettingContent(
                    modifier = contentModifier,
                    onOpenSettingClicked = {
                        context.startActivity(viewModel.getSettingIntent())
                    }
                )
            } else {
                CircularProgressIndicator(modifier = contentModifier)
            }

        }
    )

}


@Composable
fun OpenApSettingContent(
    onOpenSettingClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.location))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(140.dp)
        )

        Text(
            stringResource(R.string.permission_explanation),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 14.dp)
        )

        ElevatedButton(onClick = onOpenSettingClicked) {
            Text(stringResource(R.string.open_app_settings))
        }
    }

}


fun Context.activity(): Activity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

@Composable
fun LocationPermissionExplanationDialog(onConfirm: () -> Unit,onDismiss : () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.location_permission)) },
        text = { Text(stringResource(R.string.permission_explanation)) },
        icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Continue")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    )
}