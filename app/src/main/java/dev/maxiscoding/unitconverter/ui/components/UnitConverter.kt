package dev.maxiscoding.unitconverter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    val units = arrayOf("Centimeters", "Meters", "Feet", "Millimeters")
    val conversionFactors = mapOf(
        "Centimeters to Meters" to 0.01,
        "Centimeters to Feet" to 1 / 30.48,
        "Centimeters to Millimeters" to 10.0,
        "Meters to Centimeters" to 100.0,
        "Meters to Feet" to 1 / 0.3048,
        "Meters to Millimeters" to 1000.0,
        "Feet to Centimeters" to 30.48,
        "Feet to Meters" to 0.3048,
        "Feet to Millimeters" to 304.8,
        "Millimeters to Centimeters" to 0.1,
        "Millimeters to Meters" to 0.001,
        "Millimeters to Feet" to 1 / 304.8
    )
    var fromUnit by remember { mutableStateOf(units[0]) }
    var toUnit by remember { mutableStateOf(units[1]) }
    var fromDropdownOpen by remember { mutableStateOf(false) }
    var toDropdownOpen by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }
    val result by remember {
        derivedStateOf {
            if (input.isNotEmpty()) {
                val inputValue = input.toFloatOrNull() ?: return@derivedStateOf ""
                if (fromUnit == toUnit) return@derivedStateOf input
                val key = "$fromUnit to $toUnit"
                val factor = conversionFactors[key] ?: return@derivedStateOf input
                (inputValue * factor).toString()
            } else {
                ""
            }
        }
    }

    fun changeUnits(from: Boolean = true, unit: String) {
        if (from) {
            fromUnit = unit
            fromDropdownOpen = false
        } else {
            toUnit = unit
            toDropdownOpen = false
        }
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "Unit Converter")
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = input, onValueChange = { newValue -> input = newValue })
            Spacer(Modifier.height(16.dp))
            Row {
                Box {
                    Button(onClick = { fromDropdownOpen = !fromDropdownOpen }) {
                        Text("From $fromUnit")
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = fromDropdownOpen,
                        onDismissRequest = { fromDropdownOpen = false }
                    ) {
                        for (unit in units) {
                            DropdownMenuItem(
                                text = { Text(unit) },
                                onClick = { changeUnits(from = true, unit = unit)  }
                            )
                        }
                    }
                }
                Spacer(Modifier.width(16.dp))
                Box {
                    Button(onClick = { toDropdownOpen = !toDropdownOpen }) {
                        Text("To $toUnit")
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = toDropdownOpen,
                        onDismissRequest = { toDropdownOpen = false }
                    ) {
                        for (unit in units) {
                            DropdownMenuItem(
                                text = { Text(unit) },
                                onClick = { changeUnits(from = false, unit = unit)  }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.width(16.dp))
            if (result.isNotEmpty()) {
                Text("Result: $result")
            }
        }
    }

}

@Preview(showBackground = false)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}