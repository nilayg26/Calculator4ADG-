package com.example.calculator4adg
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator4adg.ui.theme.ButtonColor
import com.example.calculator4adg.ui.theme.DisplayColor
@Composable
fun CreateButton(onClick: () -> Unit ={},stringText:String="",shape:Int=16,enable:Boolean=true,color: Color= ButtonColor,textSize:Int=50){
    Button(onClick = onClick, modifier = Modifier
        .padding(10.dp)
        .clip(RoundedCornerShape(shape.dp))
        .shadow(10.dp), enabled=enable, colors = ButtonDefaults.buttonColors(
        color), shape = RectangleShape) {
        Text(text =stringText, style = MaterialTheme.typography.bodySmall, fontSize = textSize.sp)
    }
}
@Composable
fun DisplayScreen(result:Double,text:String="",valSize: Int=75){
    Column(verticalArrangement = Arrangement.Center) {
        Card(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), elevation = CardDefaults.cardElevation(26.dp), colors = CardDefaults.cardColors(containerColor = DisplayColor)) {
            Row(horizontalArrangement = Arrangement.Center) {
                Spacer(modifier = Modifier.weight(1f)) // Spacer with weight 1f
                Text(
                    text = result.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = valSize.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Text(text =text,style = MaterialTheme.typography.bodySmall,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end=10.dp))
            }
        }
    }
}
@Composable
fun DisplayScreenText(text:String="Hello"){
    Column(verticalArrangement = Arrangement.Center) {
        Card(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), elevation = CardDefaults.cardElevation(26.dp), colors = CardDefaults.cardColors(containerColor = DisplayColor)) {
            Row {
                Spacer(modifier = Modifier.weight(1f)) // Spacer with weight 1f
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 75.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}
