// MainActivity.kt
package com.dom.lab8market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dom.lab8market.data.database.AppDatabase
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: SupermarketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos y el ViewModel
        val database = AppDatabase.getDatabase(this)
        val supermarketItemDao = database.supermarketItemDao()
        viewModel = ViewModelProvider(this, SupermarketViewModelFactory(supermarketItemDao))
            .get(SupermarketViewModel::class.java)

        setContent {
            MaterialTheme {
                val items = viewModel.items.collectAsState(initial = emptyList())

                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    AddSupermarketItem(
                        onAddItem = { name, quantity, imagePath ->
                            viewModel.addItem(name, quantity, imagePath)
                        },
                        onCaptureImage = {
                            // Implementar captura de imagen si es necesario
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SupermarketList(
                        supermarketItems = items.value,
                        onDeleteItem = { item -> viewModel.deleteItem(item) }
                    )
                }
            }
        }
    }
}
