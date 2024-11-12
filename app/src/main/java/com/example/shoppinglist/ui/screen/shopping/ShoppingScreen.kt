package com.example.shoppinglist.ui.screen.shopping


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShoppingCategory
import com.example.shoppinglist.data.ShoppingItem
import java.util.Date
import kotlin.time.measureTime



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel()
) {

    val shoppingList by viewModel.getAllItems().collectAsState(emptyList())

    var showAddDialog by rememberSaveable { mutableStateOf(false) }

    var itemToEdit: ShoppingItem? by rememberSaveable { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.shopping_list))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = stringResource(
                            R.string.add
                        )
                        )
                    }

                    IconButton(onClick = { viewModel.clearAll() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(
                            R.string.delete
                        )
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp) // Ensure space for the FAB
            ) {
                if (shoppingList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_items_in_the_list),
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyColumn {
                        items(shoppingList) { shoppingItem ->
                            ShoppingCard(
                                shoppingItem = shoppingItem,
                                onDelete = { item -> viewModel.removeItem(item) },
                                onEdit = { item ->
                                    itemToEdit = item
                                    showAddDialog = true
                                },
                                onBought = { item ->
                                    viewModel.changeStatus(
                                        item,
                                        !item.status
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Dialog handling (if needed)
            if (showAddDialog) {
                ShoppingDialog(
                    viewModel = viewModel,
                    itemToEdit = itemToEdit,
                    onCancel = {
                        showAddDialog = false
                        itemToEdit = null
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingDialog(
    viewModel: ShoppingViewModel,
    itemToEdit: ShoppingItem? = null,
    onCancel: () -> Unit
){
    var name by rememberSaveable { mutableStateOf(itemToEdit?.name ?:"") }
    var description by rememberSaveable { mutableStateOf(itemToEdit?.description ?:"") }
    var category by rememberSaveable { mutableStateOf(itemToEdit?.category ?: ShoppingCategory.OTHER) }
    var price by rememberSaveable { mutableStateOf(itemToEdit?.price ?: "") }
    var bought by rememberSaveable { mutableStateOf(itemToEdit?.status ?: false) }

    var inputErrorState by rememberSaveable { mutableStateOf(false) }

    var emptyText = stringResource(R.string.this_cannot_be_empty)
    var emptyNameState by rememberSaveable { mutableStateOf(false)}
    var emptyDescriptionState by rememberSaveable { mutableStateOf(false)}

    fun validatePrice(input: String): Unit {
        try {
            val num = input.toInt()
            inputErrorState = false
        } catch (e: NumberFormatException) {
            inputErrorState = true
        }
    }

    Dialog(
        onDismissRequest = {onCancel()}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ){
            Column(modifier = Modifier.padding(15.dp)){
                Text(text = if(itemToEdit == null) stringResource(R.string.add_new_item) else stringResource(
                    R.string.edit_item
                ),
                     style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    label = {Text(stringResource(R.string.name))},
                    value = name,
                    onValueChange = {name = it
                                    emptyNameState = name.isEmpty()
                                    },
                    isError = emptyNameState,
                    supportingText = {
                        if (emptyNameState) {
                            Text(text = emptyText)
                        }
                    },
                    trailingIcon = {
                        if (emptyNameState)
                            Icon(
                                Icons.Filled.Warning, stringResource(R.string.error),
                                tint = MaterialTheme.colorScheme.error)
                    }
                )

                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    label = {Text(stringResource(R.string.description_add))},
                    value = description,
                    onValueChange = {description = it
                                    emptyDescriptionState = description.isEmpty()},
                    isError = emptyDescriptionState,
                    supportingText = {
                        if (emptyDescriptionState) {
                            Text(text = emptyText)
                        }
                    },
                    trailingIcon = {
                        if (emptyDescriptionState)
                            Icon(
                                Icons.Filled.Warning,stringResource(R.string.error),
                                tint = MaterialTheme.colorScheme.error)
                    }
                )

                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){

                    OutlinedTextField(modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                        label = {Text(stringResource(R.string.price_add))},
                        value = price,
                        onValueChange = {
                            price = it
                            validatePrice(price) },
                        isError = inputErrorState,
                        supportingText = {
                            if(inputErrorState) {
                                Text(text = stringResource(R.string.must_be_a_number))
                            }
                        },
                        trailingIcon = {
                            if (inputErrorState)
                                Icon(
                                    Icons.Filled.Warning, stringResource(R.string.error),
                                    tint = MaterialTheme.colorScheme.error
                                )
                        }
                    )

                    Spinner(
                        list = ShoppingCategory.entries,
                        preselected = ShoppingCategory.OTHER,
                        onSelectionChanged = {category = it},
                        modifier = Modifier.weight(1f)
                    )

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Checkbox(
                        checked = bought,
                        onCheckedChange = {bought = it},

                    )

                    Text(
                        text = stringResource(R.string.bought),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 2.dp)
                    )

                    TextButton(
                        enabled = !emptyNameState && !emptyDescriptionState && !inputErrorState,
                        modifier = Modifier.padding(start = 50.dp),
                        onClick = {
                        if(itemToEdit == null) {
                            viewModel.addItem(
                                ShoppingItem(
                                    name = name,
                                    description = description,
                                    category = category,
                                    price = price,
                                    status = bought
                                )
                            )
                        } else {
                            var itemToEditCopy = itemToEdit.copy(
                                name = name,
                                description = description,
                                category = category,
                                price = price,
                                status = bought
                            )

                            viewModel.editItem(itemToEditCopy)
                        }
                        onCancel()
                    }
                    ){
                        Text(stringResource(R.string.save))

                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingCard(
    shoppingItem: ShoppingItem,
    onDelete: (ShoppingItem) -> Unit,
    onBought: (ShoppingItem) -> Unit,
    onEdit: (ShoppingItem) -> Unit,
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .animateContentSize()
    ){
        var expanded by rememberSaveable { mutableStateOf(false) }
        var itemBought by rememberSaveable { mutableStateOf(shoppingItem.status) }

        Column(modifier = Modifier
            .padding(20.dp)
            .animateContentSize()){
            Row(verticalAlignment = Alignment.CenterVertically){

                Image(
                    painter = painterResource(id = shoppingItem.category.getIcon()),
                    contentDescription = stringResource(R.string.category),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )

                Column(modifier = Modifier.weight(1f)){
                    Text(
                        text = shoppingItem.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = shoppingItem.category.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically){
                    Checkbox(
                        checked = itemBought,
                        onCheckedChange = { isChecked ->
                            itemBought = isChecked
                            onBought(shoppingItem.copy(status = isChecked))
                        }
                    )

                    IconButton(onClick = {onDelete(shoppingItem)}){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                    }

                    IconButton(onClick = {onEdit(shoppingItem)}) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(R.string.edit))
                    }

                    IconButton(onClick = {expanded = !expanded}){
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if(expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more)
                        )
                    }
                }
            }

            if(expanded){
                Text(
                    text = stringResource(R.string.description_item, shoppingItem.description),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(R.string.price_item, shoppingItem.price),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Spinner(
    list: List<ShoppingCategory>,
    preselected: ShoppingCategory,
    onSelectionChanged: (ShoppingCategory) -> Unit,
    modifier: Modifier = Modifier
){
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(modifier = modifier.clickable{
        expanded = !expanded
    }){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ){
            Text(
                text = selected.toString(),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach{ listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(listEntry)
                        },
                        text = {
                            Text(
                                text = listEntry.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )

                        }
                    )

                }
            }
        }
    }
}



