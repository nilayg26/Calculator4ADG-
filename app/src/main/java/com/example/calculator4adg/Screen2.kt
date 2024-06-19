package com.example.calculator4adg

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.calculator4adg.ui.theme.ACcolor
import com.example.calculator4adg.ui.theme.Background
import com.example.calculator4adg.ui.theme.DisplayColor
import kotlin.math.ceil

@Composable
fun Screen2(navController: NavHostController) {
    val context= LocalContext.current
    val state= rememberScrollState()
    val length= mutableListOf("meter","inches","feet")
    val mass= mutableListOf("grams","pound")
    val time= mutableListOf("seconds","hour","minutes")
    val first= mutableListOf("Length","Mass","Time")
    val second= mutableListOf(length,mass,time)
    var index1 by remember {
        mutableIntStateOf(0)
    }
    var index2 by remember {
        mutableIntStateOf(0)
    }
    var index3 by remember {
        mutableIntStateOf(0)
    }
    var opt1 by remember {
        mutableStateOf(first[0])
    }
    var opt2 by remember {
        mutableStateOf("---")
    }
    var opt3 by remember {
        mutableStateOf("---")
    }
    var stringResult by remember { mutableStateOf("") }
    var calResult by remember {
        mutableDoubleStateOf(0.0)
    }
    Column(modifier = Modifier
        .background(Background)
        .fillMaxSize()
        .verticalScroll(state)) {
        Card (modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally),
            elevation = CardDefaults.cardElevation(16.dp),
            colors = CardDefaults.cardColors(containerColor = Background)){
        Card( modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)) {

            Text(text = "Convert from: ", style = MaterialTheme.typography.bodySmall)
            DropdownBox(
                selectedItem = opt1,
                onItemSelected = { newItem, index ->
                    opt1 = newItem;index1 = index;opt2 = second[index1][0];opt2 = "---";opt3 = "---"
                },
                items = first
            )
            DropdownBox(
                selectedItem = opt2,
                onItemSelected = { newItem, index -> opt2 = newItem;index2 = index },
                items = second[index1]
            )

            TextField(
                value = stringResult,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 50.sp),
                onValueChange = { newVal ->
                    if (newVal.toDoubleOrNull() != null || newVal == "") {
                        stringResult = newVal
                    } else {
                        context.createToastMessage("Enter a valid number only", 0)
                    }
                },
                label = { Text(text = "Enter Value in $opt2", fontSize = 15.sp, color = Color.Black) })
        }
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Text(text = "Convert to: ", style = MaterialTheme.typography.bodySmall)
                DropdownBox(
                    selectedItem = opt3,
                    onItemSelected = { newItem, index -> opt3 = newItem;index3 = index },
                    items = second[index1]
                )
                calResult = convertUnit(
                    category = opt1,
                    fromUnit = opt2,
                    toUnit = opt3,
                    value = if (stringResult.toDoubleOrNull() == null) {
                        0.0
                    } else {
                        stringResult.toDouble()
                    }
                )
            }

            DisplayScreen(result = calResult, text=if(opt3!="---"){opt3}else{""})
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CreateButton(stringText = "AC", onClick = {opt1=first[0];opt2="---";opt3="---";index1=0;index2=0;index3=0;stringResult=""}, color = ACcolor)
            }
        }



        Spacer(modifier = Modifier.weight(1F))
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.weight(1F))
            CreateButton(stringText = "Switch", onClick = {navController.popBackStack()}, textSize = 30)
        }
    }
}
private fun convertUnit(
    category: String,
    value: Double,
    fromUnit: String,
    toUnit: String
): Double {
    if (fromUnit=="---"||toUnit=="---"){
        return 0.0
    }
    val conversionMap = mapOf(
        "Length" to mapOf(
            "meter" to mapOf("meter" to 1.0, "inches" to 39.37, "feet" to 3.28),
            "inches" to mapOf("meter" to 0.0254, "inches" to 1.0, "feet" to 0.0834),
            "feet" to mapOf("meter" to 0.3048, "inches" to 12.0, "feet" to 1.0)
        ),
        "Mass" to mapOf(
            "grams" to mapOf("grams" to 1.0, "pound" to 0.0022),
            "pound" to mapOf("grams" to 453.592, "pound" to 1.0)
        ),
        "Time" to mapOf(
            "seconds" to mapOf("seconds" to 1.0, "hour" to 1 / 3600.0, "minutes" to 1 / 60.0),
            "hour" to mapOf("seconds" to 3600.0, "hour" to 1.0, "minutes" to 60.0),
            "minutes" to mapOf("seconds" to 60.0, "hour" to 1 / 60.0, "minutes" to 1.0)
        )
    )
    val conversionFactor = conversionMap[category]!![fromUnit]!![toUnit]!!
    return  ceil( (value*conversionFactor)*10000)/10000
}