package com.example.shoppinglist.ui.screen.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShoppingCategory
import com.example.shoppinglist.data.ShoppingItem
import com.example.shoppinglist.ui.screen.shopping.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel()
){
    val totalCost by viewModel.getTotalPrice().collectAsState(initial = 0)

    val numFood by viewModel.getItemsCountByCategory(stringResource(R.string.food)).collectAsState(initial = 0)
    val numTech by viewModel.getItemsCountByCategory(stringResource(R.string.tech)).collectAsState(initial = 0)
    val numBook by viewModel.getItemsCountByCategory(stringResource(R.string.book)).collectAsState(initial = 0)
    val numDrink by viewModel.getItemsCountByCategory(stringResource(R.string.drink)).collectAsState(initial = 0)
    val numMedicine by viewModel.getItemsCountByCategory(stringResource(R.string.medicine)).collectAsState(initial = 0)
    val numOther by viewModel.getItemsCountByCategory(stringResource(R.string.other)).collectAsState(initial = 0)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = stringResource(R.string.shopping_cart),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(stringResource(R.string.shopping_list_summary))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) { innerpadding ->
        Box(modifier = modifier
            .fillMaxSize()
            .padding(innerpadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = ShoppingCategory.FOOD.getIcon()),
                        contentDescription = stringResource(R.string.FOOD),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.foodNum, numFood),
                        modifier = Modifier.weight(1f)
                    )

                    Image(
                        painter = painterResource(id = ShoppingCategory.TECH.getIcon()),
                        contentDescription = stringResource(R.string.TECH),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.techNum, numTech),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = ShoppingCategory.BOOK.getIcon()),
                        contentDescription = stringResource(R.string.BOOK),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.bookNum, numBook),
                        modifier = Modifier.weight(1f)
                    )

                    Image(
                        painter = painterResource(id = ShoppingCategory.DRINK.getIcon()),
                        contentDescription = stringResource(R.string.DRINK),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.drinkNum, numDrink),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = ShoppingCategory.MEDICINE.getIcon()),
                        contentDescription = stringResource(R.string.MEDICINE),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.medicineNum, numMedicine),
                        modifier = Modifier.weight(1f)
                    )

                    Image(
                        painter = painterResource(id = ShoppingCategory.OTHER.getIcon()),
                        contentDescription = stringResource(R.string.OTHER),
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.otherNum, numOther),
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = stringResource(R.string.total_cost, totalCost),
                    modifier = Modifier.padding(20.dp),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
            }
        }
    }
}