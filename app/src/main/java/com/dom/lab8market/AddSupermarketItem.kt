package com.dom.lab8market

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun AddSupermarketItem(
    onAddItem: (name: String, quantity: String, imagePath: String) -> Unit,
    onCaptureImage: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var currentImagePath by remember { mutableStateOf<String?>(null) }
    var showCamera by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = quantity,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    quantity = it
                    isError = false
                } else {
                    isError = true
                }
            },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        if (isError) {
            Text(
                text = "Por favor, ingrese solo números",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display captured image if available
        currentImagePath?.let { path ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(File(path)),
                    contentDescription = "Captured image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { showCamera = true }
            ) {
                Text("Tomar Foto")
            }

            Button(
                onClick = {
                    if (name.isNotBlank() && quantity.isNotBlank() && !isError) {
                        onAddItem(name, quantity, currentImagePath ?: "")
                        name = ""
                        quantity = ""
                        currentImagePath = null
                    }
                }
            ) {
                Text("Agregar Item")
            }
        }
    }

    if (showCamera) {
        CameraCaptureDialog(
            onImageCaptured = { path ->
                currentImagePath = path
                showCamera = false
            }
        )
    }
}

@Composable
fun CameraCaptureDialog(onImageCaptured: (String?) -> Unit) {
    Dialog(onDismissRequest = { onImageCaptured(null) }) {
        CameraCapture(onImageCaptured = onImageCaptured)
    }
}