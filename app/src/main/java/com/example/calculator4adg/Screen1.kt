package com.example.calculator4adg
import android.content.Context
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.calculator4adg.ui.theme.ACcolor
import com.example.calculator4adg.ui.theme.Background
import kotlin.math.ceil

@Composable
fun Screen1(navController: NavHostController) {
   val rows=listOf(0,1,2)
   val cols=listOf(0,1,2)
   val context:Context= LocalContext.current
   var optResult by remember {
      mutableDoubleStateOf(0.toDouble())
   }
  var buttonIsClicked by remember{
     mutableStateOf(false)
   }
   var result by remember {
      mutableDoubleStateOf(0.toDouble())
   }
   var decimalResult by remember {
      mutableIntStateOf(0)
   }
   var index by remember {
      mutableIntStateOf(1)
   }
   var dot by remember {
      mutableStateOf(false)
   }
   var history =remember {mutableListOf(0.0)}
   val state= rememberScrollState()
   Column(modifier = Modifier
      .background(Background)
      .fillMaxSize()
      .verticalScroll(state)
      , verticalArrangement = Arrangement.Center) {

      Card(modifier = Modifier
         .padding(10.dp)
         .fillMaxWidth(), elevation = CardDefaults.cardElevation(16.dp),colors = CardDefaults.cardColors(containerColor = Background)) {
         DisplayScreen(result = result)
         var i=9
         rows.forEach{
            row->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
               cols.forEach {
                  col->
                  val value=i
                  CreateButton(stringText = i.toString(), onClick = {
                     if(dot){
                        decimalResult=value
                        result=(result.toString()+value.toString()).toDouble()
                     }
                     else {
                        result = when (result) {
                           result.toInt().toDouble() -> ("${result.toInt()}" + "$value").toDouble()
                           else -> value.toDouble()
                        }
                     }
                  }
                  )
                  i-- } } }
         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            CreateButton(stringText = "0",onClick ={result =(result.toInt().toString()+"0").toDouble()})
            CreateButton(stringText = "÷",onClick = {optResult=getOptResult(result,optResult,index,context);result=0.toDouble();index=4})
            CreateButton(stringText = "=",onClick ={result = getOptResult(result, optResult, index,context);optResult=0.toDouble();index=1;history.add(result)})
         }
         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            CreateButton(stringText = "+", onClick = {optResult=getOptResult(result,optResult,index,context);result=0.toDouble();index=1})
            CreateButton(stringText = "-", onClick = {optResult=getOptResult(result,optResult,index,context);result=0.toDouble();index=2})
            CreateButton(stringText = "x", onClick = {optResult=getOptResult(result,optResult,index,context);result=0.toDouble();index=3})
         }
         Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceEvenly) {
            //CreateButton(stringText = ".", onClick = {dot=true})
            CreateButton(stringText = "↑", onClick ={result=moveUp(history,result)})
            CreateButton(stringText = "AC",onClick ={result=0.0;optResult=0.0;index=1;history.clear()},shape = 20, color = ACcolor)
            CreateButton(stringText = "↓", onClick ={result=moveDown(history,result)})

         }

      }
      Spacer(modifier = Modifier.weight(1F))
      Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
         Spacer(modifier = Modifier.weight(1F))
         CreateButton(stringText = "Switch", onClick = {navController.navigate(Screen2.route)}, textSize = 30)
      }

   }
}
private fun getOptResult(result:Double,optResult:Double,index:Int,context: Context):Double{
   return when(index){
      1->ceil( (optResult+result)*1000000)/1000000
      2-> ceil( (optResult-result)*1000000)/1000000
      3-> ceil((optResult * result)*1000000)/1000000
      4->
         if(optResult/result=="Infinity".toDouble()) {
            context.createToastMessage("Division by zero not possible",0)
            optResult
         }
         else{
            ceil( (optResult/result)*1000000)/1000000
         }
      else->{optResult}
   }
}
private fun moveDown(history:MutableList<Double>,result: Double):Double{
   val index= history.indexOf(result)
   val indexD=index-1
   if(indexD<0){
      return result
   }
   return history[indexD]
}
private fun moveUp(history:MutableList<Double>,result: Double):Double{
   val index= history.indexOf(result)
   val indexU=index+1
   if(indexU>=history.size){
      return result
   }
   return history[indexU]
}




