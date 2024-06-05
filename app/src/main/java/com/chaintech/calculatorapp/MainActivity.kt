package com.chaintech.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaintech.calculatorapp.ui.theme.CalculatorAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxSize()
                    ) {
                        NumberListApp()
                    }
                }
            }
        }
    }
}

@Composable
fun NumberListApp() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun parseNumbers(text: String): List<Int> {
        return text.split(",").mapNotNull { it.trim().toIntOrNull() }
    }

    fun calculateResults() {
        val list1 = parseNumbers(text1)
        val list2 = parseNumbers(text2)
        val list3 = parseNumbers(text3)

        val intersection = list1.intersect(list2).intersect(list3)
        val union = (list1 + list2 + list3).toSet()
        val highest = union.maxOrNull()

        result = """
            Intersection: ${intersection.joinToString(", ")}
            Union: ${union.joinToString(", ")}
            Highest Number: $highest
        """.trimIndent()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Enter comma-separated numbers:", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        var text1Error by remember { mutableStateOf(false) }
        TextField(
            value = text1,
            onValueChange = {
                text1 = it
            },
            isError = text1Error,
            label = { Text("TextBox 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        var text2Error by remember { mutableStateOf(false) }
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            isError = text2Error,
            label = { Text("TextBox 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        var text3Error by remember { mutableStateOf(false) }
        TextField(
            value = text3,
            onValueChange = { text3 = it },
            isError = text3Error,
            label = { Text("TextBox 3") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                text1Error = text1.isEmpty()
                text2Error = text2.isEmpty()
                text3Error = text3.isEmpty()

                if (!text1Error && !text2Error && !text3Error) {
                    calculateResults()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result, fontSize = 16.sp)
    }
}