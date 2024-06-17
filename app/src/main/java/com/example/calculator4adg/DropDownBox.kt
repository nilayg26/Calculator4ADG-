package com.example.calculator4adg

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.calculator4adg.ui.theme.ACcolor
import com.example.calculator4adg.ui.theme.Background

@Composable
fun DropdownBox(
    selectedItem: String, // Initial selected item
    onItemSelected: (String,Int) -> Unit, // Callback for item selection
    items: List<String> // List of dropdown options
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().background(ACcolor), ) {
        Text(
            text = selectedItem,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis // Handle long text
        )
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.align(CenterEnd)
        ) {
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown", modifier = Modifier.size(170.dp))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed{index, item ->
                DropdownMenuItem(text = { Text(text = item)}, onClick = {
                    expanded = false
                    onItemSelected(item,index)
                } )
            }
        }
    }
}
