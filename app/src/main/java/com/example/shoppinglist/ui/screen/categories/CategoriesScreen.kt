package com.example.shoppinglist.ui.screen.categories

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShoppingCategory
import com.example.shoppinglist.data.ShoppingItem
import com.example.shoppinglist.navigation.MainNavigation
import com.example.shoppinglist.ui.screen.shopping.ShoppingCard
import com.example.shoppinglist.ui.screen.shopping.ShoppingViewModel
import com.example.shoppinglist.ui.screen.shopping.Spinner


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel()
){
    var category by rememberSaveable { mutableStateOf(ShoppingCategory.OTHER) }
    val categoryList by viewModel.getItemsByCategory(category.toString()).collectAsState(initial = emptyList())


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title ={Text(stringResource(R.string.categories),)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                actions = {
                    Spinner(
                        list = ShoppingCategory.entries,
                        preselected = ShoppingCategory.OTHER,
                        onSelectionChanged = {category = it},
                        modifier = Modifier.weight(1f)
                    )
                }
            )
        }
    ){
        innerpadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerpadding)
        ){
            Column(modifier = modifier.fillMaxSize()){
                if (categoryList.isEmpty()) {
                    Text(
                        stringResource(R.string.no_category_selected),
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyColumn{
                        items(categoryList) { shoppingItem ->
                            CategoryCard(
                                shoppingItem = shoppingItem
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    shoppingItem: ShoppingItem,
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


                    IconButton(onClick = {expanded = !expanded}){
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if(expanded) stringResource(R.string.show_less) else stringResource(
                                R.string.show_more
                            )
                        )
                    }
                }
            }

            if(expanded){
                Text(
                    text = stringResource(R.string.description, shoppingItem.description),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(R.string.price, shoppingItem.price),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}