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
    var fromUnit by remember { mutableStateOf(units[0]) }
    var toUnit by remember { mutableStateOf(units[1]) }
    var fromDropdownOpen by remember { mutableStateOf(false) }
    var toDropdownOpen by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }
    val result by remember { derivedStateOf {
        if (input.isNotEmpty()) {
            val inputValue = input.toFloatOrNull() ?: return@derivedStateOf ""
            return@derivedStateOf when {
                fromUnit == "Centimeters" && toUnit == "Meters" -> (inputValue / 100).toString()
                fromUnit == "Centimeters" && toUnit == "Feet" -> (inputValue / 30.48).toString()
                fromUnit == "Centimeters" && toUnit == "Millimeters" -> (inputValue * 10).toString()
                fromUnit == "Meters" && toUnit == "Centimeters" -> (inputValue * 100).toString()
                fromUnit == "Meters" && toUnit == "Feet" -> (inputValue / 0.3048).toString()
                fromUnit == "Meters" && toUnit == "Millimeters" -> (inputValue * 1000).toString()
                fromUnit == "Feet" && toUnit == "Centimeters" -> (inputValue * 30.48).toString()
                fromUnit == "Feet" && toUnit == "Meters" -> (inputValue * 0.3048).toString()
                fromUnit == "Feet" && toUnit == "Millimeters" -> (inputValue * 304.8).toString()
                fromUnit == "Millimeters" && toUnit == "Centimeters" -> (inputValue / 10).toString()
                fromUnit == "Millimeters" && toUnit == "Meters" -> (inputValue / 1000).toString()
                fromUnit == "Millimeters" && toUnit == "Feet" -> (inputValue / 304.8).toString()
                fromUnit == toUnit -> input
                else -> input
            }
        } else {
            return@derivedStateOf ""
        }
    } }

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