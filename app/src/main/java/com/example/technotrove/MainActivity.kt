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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
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
    Product("Apple Macbook Air", "https://http2.mlstatic.com/D_NQ_NP_801112-MLA46516512347_062021-O.webp", "13 pulgadas, 2020, Chip M1, 256 GB de SSD, 8 GB de RAM", "Laptops"),
    Product("Laptop Lenovo Ideapad 3", "https://http2.mlstatic.com/D_NQ_NP_604103-MLA53561300460_022023-O.webp", "15.6'' Ryzen 7 16gb Ram 512gb Ssd Color Sand", "Laptops"),
    Product("Laptop HP Victus", "https://http2.mlstatic.com/D_NQ_NP_935455-MLA73565691895_122023-O.webp", "15.6'' Ryzen 7 16gb Ram 512gb Ssd Color Sand", "Laptops"),
    Product("Apple Macbook Air", "https://http2.mlstatic.com/D_NQ_NP_801112-MLA46516512347_062021-O.webp", "15.6'' AMD Ryzen 5 7535hs 8 Ram 512 Ssd Rtx 2050 - Color Mica Silver", "Laptops"),
    Product("Rechargeable Ultra-thin", "https://http2.mlstatic.com/D_NQ_NP_781661-MLM52779396059_122022-O.webp", "Usb + 2.4 Wireless", "Mouses"),
    Product("Mouse Silencioso", "https://http2.mlstatic.com/D_NQ_NP_899880-MLU72566183392_112023-O.webp", "Receptor Usb Para Laptop, Pc", "Mouses"),
    Product("Mouse con Cable HP", "https://http2.mlstatic.com/D_NQ_NP_767390-MLA51439543986_092022-O.webp", "Con cable", "Mouses"),
    Product("Ratón Ergonómico", "https://http2.mlstatic.com/D_NQ_NP_846884-CBT73292746766_122023-O.webp", "2.4 Ghz Con 3 Dpis", "Mouses"),
    Product("Free Wolf K3 Teclado Gamer Mecánico", "https://http2.mlstatic.com/D_NQ_NP_698228-MLA73562095985_122023-O.webp", "Teclado mecánico", "Teclados"),
    Product("Set Inalámbrico De Teclado Español Y Mouse Portátil", "https://http2.mlstatic.com/D_NQ_NP_900890-CBT73964077461_012024-O.webp", "Teclado mecánico", "Teclados"),
    Product("Teclado Inalambrico Español,recargable Bluetooth Wireless", "https://http2.mlstatic.com/D_NQ_NP_981472-MLM69590756318_052023-O.webp", "Teclado mecánico", "Teclados"),
    Product("Vorago KM-500 Kit Gamer De Teclado y Mouse", "http://http2.mlstatic.com/D_NQ_NP_656080-MLU72832009547_112023-O.webp", "Teclado mecánico", "Teclados"),
    Product("Monitor gamer curvo Samsung F390 27\" ", "https://http2.mlstatic.com/D_NQ_NP_908044-MLU72575476474_112023-O.webp", "Voltaje: 100V/240V", "Monitores"),
    Product("Monitor gamer Crua G220A led 22\" negro", "https://http2.mlstatic.com/D_NQ_NP_684434-MLA69389814031_052023-O.webp", "Voltaje: 100V/240V", "Monitores"),
    Product("Monitor Gamer Curvo Crua Cr240cm 24", "https://http2.mlstatic.com/D_NQ_NP_720301-CBT72561704612_112023-O.webp", "Voltaje: 100V/240V", "Monitores"),
    Product("Monitor De Computadora 19 Pulgadas", "https://http2.mlstatic.com/D_NQ_NP_839865-MLM72212067392_102023-O.webp", "Voltaje: 100V/240V", "Monitores"),
    Product("Tablet Samsung Galaxy Tab S9", "https://http2.mlstatic.com/D_NQ_NP_834583-MLM75911493222_042024-O.webp", "Gris 256gb", "Tabletas"),
    Product("Tablet Lenovo Xiaoxin Pad 2022", "https://http2.mlstatic.com/D_NQ_NP_745695-MLC51803089024_102022-O.webp", "6gb 128gb Pantalla Lcd 2k", "Tabletas"),
    Product("Tablet Apple iPad 9na", "https://http2.mlstatic.com/D_NQ_NP_912069-MLA74807972777_022024-O.webp", "Wifi 64gb Gris Espacial", "Tabletas"),
    Product("Tablet Huawei Matepad 11.5", "https://http2.mlstatic.com/D_NQ_NP_744618-MLA75618099625_042024-O.webp", "8+256 Gb Papermatte Edition Gris", "Tabletas"),
    Product("Smartwatch 1.8''", "https://http2.mlstatic.com/D_NQ_NP_873857-CBT76063440439_042024-O.webp", "eloj Inteligente Bluetooth Llamada Alexa", "Smartwatchs"),
    Product("Smart Watch Reloj Samsung Galaxy", "https://http2.mlstatic.com/D_NQ_NP_860438-MLA74805960019_022024-O.webp", "Color Del Bisel Negro Diseño De La Malla Silicona", "Smartwatchs"),
    Product("Smartwatch Redmi Watch 3", "https://http2.mlstatic.com/D_NQ_NP_678515-MLU73735804333_122023-O.webp", "Negro", "Smartwatchs"),
    Product("Apple Watch Series 7", "https://http2.mlstatic.com/D_NQ_NP_621695-MLA48090381016_112021-O.webp", "Caja de aluminio color verde", "Smartwatchs"),
)

val categories = allProducts.map { it.category }.distinct()
class AppColors{
    companion object {
        fun textColor() : Color = Color(0xFF732025)
        fun backgroundColor(): Color = Color(0xFF4a8596)
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

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(onLoadingFinished = { navController.navigate("home") })
        }
        composable("home") { HomeScreen(onNavigateToSearchScreen = { navController.navigate("search") }, onNavigateToCategoriesScreen = { navController.navigate("categories") }) }
        composable("search") { SearchScreen() }
        composable("categories") { CategoriesScreen() }
    }
}

@Composable
fun SplashScreen(onLoadingFinished: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(AppColors.backgroundColor())
    ) {
        // Reemplazar "R.drawable.logo" con tu recurso de logo real
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )
    }

    LaunchedEffect(true) {
        kotlinx.coroutines.delay(3000)
        onLoadingFinished()
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
    var searchText by remember { mutableStateOf("") }
    val filteredProducts = allProducts.filter {
        it.title.contains(searchText, ignoreCase = true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.backgroundColor())
    ) {
        SearchBar(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            onSearch =  { text ->
                searchText = text
            }
        )

        LazyVerticalGrid( columns = GridCells.Fixed(2)) {
            items(filteredProducts) { product ->
                ProductItem(product)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (text: String) -> Unit) {
    val searchText = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column{
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
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Buscar", color = AppColors.textColor())
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = {
                onSearch(searchText.value)
                keyboardController?.hide()
            }) {
                Icon(Icons.Filled.Search, contentDescription = "Buscar...", tint = Color.Black)
            }
        }
    }
}





@Composable
fun ProductGrid(modifier: Modifier = Modifier) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(allProducts) { product ->
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