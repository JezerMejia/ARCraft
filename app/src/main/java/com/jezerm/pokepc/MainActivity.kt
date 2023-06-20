package com.jezerm.pokepc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jezerm.pokepc.data.ItemDtoRoomDatabase
import com.jezerm.pokepc.data.RoomRepository
import com.jezerm.pokepc.navigation.AppNavHost
import com.jezerm.pokepc.ui.theme.PokePCTheme

class MainActivity : ComponentActivity() {
    // Get the database
    val database by lazy { ItemDtoRoomDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the Room repository based in the ItemDto DAO
        RoomRepository.init(database.itemDtoDao())

        setContent {
            PokePCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokePCTheme {
        AppNavHost()
    }
}