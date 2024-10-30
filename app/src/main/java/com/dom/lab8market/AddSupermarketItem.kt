package com.dom.lab8market

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddSupermarketItem(
    onAddItem: (String, Int, String?) -> Unit,
    onCaptureImage: () -> Unit,
    imagePath: String? = null
) {
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Nombre del artículo") }
        )

        TextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        if (imagePath != null) {
            // Mostrar imagen si está disponible
        }

        Button(onClick = onCaptureImage) {
            Text("Capturar Imagen")
        }

        Button(onClick = {
            onAddItem(itemName, quantity.toIntOrNull() ?: 0, imagePath)
        }) {
            Text("Agregar Artículo")
        }
    }
}
