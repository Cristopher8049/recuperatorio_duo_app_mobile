package com.example.recuperatorio_app_mobile  // <-- o el paquete real de tu app

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen() {

    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val locationPermission =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val pendingPermissions = remember { mutableStateListOf<String>() }

    val multiplePermissionsState = rememberMultiplePermissionsState(pendingPermissions)

    LaunchedEffect(cameraPermission.status, locationPermission.status) {

        fun updatePending(permission: String, status: PermissionStatus) {
            if (status is PermissionStatus.Granted) {
                pendingPermissions.remove(permission)
            } else {
                if (!pendingPermissions.contains(permission)) {
                    pendingPermissions.add(permission)
                }
            }
        }

        updatePending(Manifest.permission.CAMERA, cameraPermission.status)
        updatePending(
            Manifest.permission.ACCESS_FINE_LOCATION,
            locationPermission.status
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { cameraPermission.launchPermissionRequest() }) {
                Text(text = "CAMERA")
            }
            Button(onClick = { locationPermission.launchPermissionRequest() }) {
                Text(text = "LOCATION")
            }
        }

        Spacer(Modifier.height(16.dp))

        PermissionStatusText("Camera", cameraPermission.status)
        Spacer(Modifier.height(8.dp))
        PermissionStatusText("Location", locationPermission.status)

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Permisos pendientes (batch): " +
                    if (pendingPermissions.isEmpty()) "ninguno"
                    else pendingPermissions.joinToString()
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionStatusText(
    label: String,
    status: PermissionStatus
) {
    val context = LocalContext.current

    when (status) {
        is PermissionStatus.Granted -> {
            Text(text = "$label: Granted")
        }

        is PermissionStatus.Denied -> {
            Column {
                Text(
                    text = if (status.shouldShowRationale) {
                        "$label: Denied (puedes volver a intentar)."
                    } else {
                        "$label: Denied (Don't ask again)."
                    }
                )

                if (!status.shouldShowRationale) {
                    Text(
                        text = "Abrir configuración",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            ).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                            context.startActivity(intent)
                        }
                    )

                    Text(
                        text = "(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
