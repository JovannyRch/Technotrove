package com.example.technotrove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.technotrove.ui.theme.TechnotroveTheme
import coil.compose.rememberImagePainter

data class Product(
    val title: String,
    val imageUrl: String,
    val description: String,
    val category: String
)

val allProducts = listOf(
    Product("Laptop HP 240 G9", "https://upload.wikimedia.org/wikipedia/commons/c/cc/Apple-laptop-notebook-notes_%2823699694403%29.jpg", "Plateada 14'', Intel Celeron N4500 8GB de RAM 256GB SSD", "Electrónica"),
    Product("Mouse Gamer", "https://upload.wikimedia.org/wikipedia/commons/0/08/A_computer_mouse.jpg", "Mouse gamer ergonómico y con iluminación RGB", "Accesorios"),
    Product("Teclado Mecánico", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGH3gLV2MF3WEJN-uqJWIousQYWDANIb1VM5UV7lpLfw&s", "Teclado mecánico con switches azules", "Accesorios"),
    Product("Monitor 4K", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmZMAJ7QzplOac6PyFDxmUSBpStFOppnFg0Qf-8NJPbw&s", "Monitor 4k UHD con HDR", "Electrónica")
)

val categories = allProducts.map { it.category }.distinct()
class AppColors{
    companion object {
        fun textColor() : Color = Color(0xFF732025)
        fun backgroundColor(): Color = Color(0xFF547c98)
    }
}

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechnotroveTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(onNavigateToSearchScreen = { navController.navigate("search") }, onNavigateToCategoriesScreen = { navController.navigate("categories") }) }
        composable("search") { SearchScreen() }
        composable("categories") { CategoriesScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToSearchScreen: () -> Unit, onNavigateToCategoriesScreen: () -> Unit) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Lista de Productos", color = AppColors.textColor()) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppColors.backgroundColor(),
                    ),
                    actions = {
                        IconButton(onClick = {
                            onNavigateToSearchScreen()
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = "Buscar")
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onNavigateToCategoriesScreen()
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = AppColors.textColor())
                        }
                    }
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.backgroundColor())
                ) {
                    ProductGrid(modifier = Modifier.padding(innerPadding))
                }
            }
        )
    }
}



@Composable
fun CategoriesScreen() {
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull() ?: "") }

    Column(modifier = Modifier.fillMaxSize().background(AppColors.backgroundColor())) {
        CategorySelector(selectedCategory, onCategorySelected = { selectedCategory = it })
        ProductList(products = allProducts.filter { it.category == selectedCategory })
    }
}


@Composable
fun CategorySelector(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        categories.forEach { category ->
            Button(
                onClick = { onCategorySelected(category) },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category == selectedCategory) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(category)
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {

    LazyVerticalGrid( columns = GridCells.Fixed(2)) {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(product.imageUrl),
                contentDescription = product.title,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Text(product.title, style = MaterialTheme.typography.titleSmall)
            Text(product.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.backgroundColor()) // Usar el color de fondo apropiado
    ) {
        SearchBar(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Buscar", color = AppColors.textColor())
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = { /* Realizar búsqueda */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Buscar...", tint = Color.Black)
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val searchText = remember { mutableStateOf("") }

    Box(
        modifier = modifier.height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            placeholder = { Text("Buscar...") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = MaterialTheme.shapes.small)
        )
    }
}





@Composable
fun ProductGrid(modifier: Modifier = Modifier) {
    val products = listOf(
        Product("Laptop HP 240 G9", "https://upload.wikimedia.org/wikipedia/commons/c/cc/Apple-laptop-notebook-notes_%2823699694403%29.jpg", "Plateada 14'', Intel Celeron N4500 8GB de RAM 256GB SSD", ""),
        Product("Mouse", "https://upload.wikimedia.org/wikipedia/commons/0/08/A_computer_mouse.jpg", "Mouse gamer", ""),
        Product("Teclado", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGH3gLV2MF3WEJN-uqJWIousQYWDANIb1VM5UV7lpLfw&s", "Teclado mecánico", ""),
        Product("Monitor", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmZMAJ7QzplOac6PyFDxmUSBpStFOppnFg0Qf-8NJPbw&s", "Monitor 4k", ""),
        Product("Tablet Android", "https://www.trustedreviews.com/wp-content/uploads/sites/54/2023/06/X1006947-920x610.jpeg", "Tablet de 10 pulgadas con Android 11", ""),
        Product("Smartphone Pro", "https://c.pxhere.com/photos/37/4d/apps_blurred_background_cellphone_cellular_telephone_device_display_electronics_hand-1515639.jpg!d", "Smartphone con 5G y cámara de 108MP", ""),
        Product("Smartwatch Fit", "https://www.trustedreviews.com/wp-content/uploads/sites/54/2022/06/Huawei-Watch-Fit-2-24-920x518.jpg", "Reloj inteligente con seguimiento de actividad y salud", ""),
        Product("Cámara DSLR", "https://c.pxhere.com/photos/97/dd/camera_canon_photography_dslr_equipment_shooting_picture_production-484063.jpg!d", "Cámara profesional con sensor de 24MP y lentes intercambiables", ""),
        Product("Auriculares Inalámbricos", "https://images.pexels.com/photos/3394650/pexels-photo-3394650.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", "Auriculares con cancelación de ruido y 12 horas de batería", ""),
        Product("Consola de Juegos", "https://c.pxhere.com/photos/30/1d/3ds_control_display_game_hand_nintendo_person_portable-1530945.jpg!d", "Consola de última generación con 8K y SSD ultra rápida", "")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberImagePainter(
                            data = product.imageUrl,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = product.title,
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TechnotroveTheme {
        AppNavigation()
    }
}