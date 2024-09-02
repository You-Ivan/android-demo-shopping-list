package com.example.shoppinglist

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int
)


@Composable
@Preview
fun ShoppingList() {
    val sItems = remember { mutableStateListOf<ShoppingItem>() }
    var showCreateDiag by remember {
        mutableStateOf(false)
    }
    var showEditDiag by remember {
        mutableStateOf(false)
    }
    var itemName by remember {
        mutableStateOf("")
    }
    var itemQuantity by remember {
        mutableStateOf("")
    }
    var itemId by remember {
        mutableIntStateOf(1)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = {
            showCreateDiag = true
            itemName = ""
            itemQuantity = ""
        }) {
            Text(text = "Add Item")
        }
        LazyColumn {
            items(sItems) { item ->
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            border = BorderStroke(2.dp, Color.Black), // Border thickness and color
                            shape = RoundedCornerShape(8.dp),
                        )
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.name, modifier = Modifier.fillMaxWidth(0.40f))
                    Text(text = item.quantity.toString(), modifier = Modifier.fillMaxWidth(0.2f))
                    IconButton(
                        onClick = {
                            sItems.removeIf { it.id == item.id }
                        },
                        modifier = Modifier.fillMaxWidth(0.2f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "check item"
                        )
                    }
                    IconButton(
                        onClick = {
                            showEditDiag = true
                            itemName = item.name
                            itemQuantity = item.quantity.toString()
                            itemId = item.id
                        },
                        modifier = Modifier.fillMaxWidth(0.2f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "edit item",
                        )
                    }
                }

            }
        }
        if (showCreateDiag) {
            AlertDialog(onDismissRequest = { showCreateDiag = false },
                title = { Text(text = "Add Item") },
                text = {
                    Column {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Name", modifier = Modifier.width(60.dp))
                            OutlinedTextField(
                                value = itemName,
                                onValueChange = { itemName = it },
                                singleLine = true
                            )
                        }
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Quantity", modifier = Modifier.width(60.dp))
                            OutlinedTextField(
                                value = itemQuantity,
                                onValueChange = { itemQuantity = it },
                                singleLine = true
                            )
                        }
                    }
                },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { showCreateDiag = false }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = {
                            showCreateDiag = false
                            val item = ShoppingItem(sItems.size + 1, itemName, itemQuantity.toInt())
                            sItems += item
                            Log.d("shoppingList", sItems.toString())
                        }) {
                            Text(text = "Add")
                        }
                    }
                })
        }

        if (showEditDiag) {
            AlertDialog(onDismissRequest = { showCreateDiag = false },
                title = { Text(text = "Edit Item") },
                text = {
                    Column {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Name", modifier = Modifier.width(60.dp))
                            OutlinedTextField(
                                value = itemName,
                                onValueChange = { itemName = it },
                                singleLine = true
                            )
                        }
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Quantity", modifier = Modifier.width(60.dp))
                            OutlinedTextField(
                                value = itemQuantity,
                                onValueChange = { itemQuantity = it },
                                singleLine = true
                            )
                        }
                    }
                },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { showEditDiag = false }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = {
                            sItems[itemId - 1] = ShoppingItem(itemId, itemName, itemQuantity.toInt())
                            Log.d("shoppingList", sItems.toString())
                            showEditDiag = false
                        }) {
                            Text(text = "Confirm")
                        }
                    }
                })
        }
    }
}



