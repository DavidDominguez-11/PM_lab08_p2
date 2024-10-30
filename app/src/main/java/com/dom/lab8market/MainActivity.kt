package com.dom.lab8market

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dom.lab8market.data.database.AppDatabase

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: SupermarketViewModel
    private var capturedImagePath by mutableStateOf<String?>(null)

    // Registrar el launcher para solicitar permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permiso concedido, puedes proceder con la funcionalidad de la cámara
            Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_SHORT).show()
        } else {
            // Permiso denegado, muestra un mensaje al usuario
            Toast.makeText(
                this,
                "Se necesita permiso de cámara para esta funcionalidad",
                Toast.LENGTH_LONG
            ).show()
        }
    }

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
                var showCamera by remember { mutableStateOf(false) }

                if (showCamera) {
                    CameraCapture { imagePath ->
                        capturedImagePath = imagePath
                        showCamera = false
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        AddSupermarketItem(
                            onAddItem = { name, quantity, imagePath ->
                                viewModel.addItem(name, quantity, capturedImagePath ?: "")
                                capturedImagePath = null // Restablecer la ruta después de agregar el ítem
                            },
                            onCaptureImage = {
                                checkAndRequestCameraPermission {
                                    showCamera = true // Abrir el diálogo de cámara
                                }
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

    private fun checkAndRequestCameraPermission(onPermissionGranted: () -> Unit) {
        when {
            // Verifica si ya tenemos el permiso
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Ya tenemos el permiso, proceder con la funcionalidad
                onPermissionGranted()
            }
            // Deberíamos mostrar una explicación del permiso
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(
                    this,
                    "La cámara es necesaria para tomar fotos de los productos",
                    Toast.LENGTH_LONG
                ).show()
                // Solicitar el permiso después de mostrar la explicación
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                // Solicitar el permiso directamente
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}
