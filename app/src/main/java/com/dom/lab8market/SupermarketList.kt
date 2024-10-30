package com.dom.lab8market

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import com.dom.lab8market.data.model.SupermarketItem

@Composable
fun SupermarketList(
    supermarketItems: List<SupermarketItem>,
    onDeleteItem: (SupermarketItem) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(supermarketItems.size) { index ->
            val item = supermarketItems[index]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(item.itemName, style = MaterialTheme.typography.titleLarge)
                    Text("Cantidad: ${item.quantity}")
                }
                IconButton(onClick = { onDeleteItem(item) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar artículo")
                }
            }
        }
    }
}

