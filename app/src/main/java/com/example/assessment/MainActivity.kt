package com.example.assessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assessment.ui.theme.AssessmentTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssessmentTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CountryListScreen()
                }
            }
        }
    }
}

@Composable
fun CountryListScreen() {
    val coroutineScope = rememberCoroutineScope()
    var countries by remember { mutableStateOf<List<Country>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            countries = withContext(Dispatchers.IO) {
                CountryRepository.getCountries()
            }
            println("Fetched ${countries.size} countries") // Debug print
        }
    }

    if (countries.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text(
                text = "No countries found or still loading...",
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(countries) { country ->
                CountryCard(country)
            }
        }
    }
}

@Composable
fun CountryCard(country: Country) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${country.name}, ${country.region}        ${country.code}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = country.capital,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
