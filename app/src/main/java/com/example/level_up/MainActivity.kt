package com.example.level_up

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.level_up.ui.theme.LevelUPTheme
import com.example.level_up.viewmodel.CartViewModel
import com.example.level_up.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LevelUPTheme {
                LevelUpApp()
            }
        }
    }
}

@Composable
fun LevelUpApp() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AppNavHost(
                navController = navController,
                cartViewModel = cartViewModel,
                productViewModel = productViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    LevelUPTheme {
        LevelUpApp()
    }
}
