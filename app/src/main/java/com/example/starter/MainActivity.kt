package com.example.grabngocoffee  // Adjust this to match your actual package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.starter.ui.theme.StarterTheme
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class MenuItem(
    val name: String,
    val image: Int = R.drawable.placeholder,
    val price: Double,
    val description: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val (errorMessage, setErrorMessage) = remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        TitleName(name = stringResource(R.string.grab_and_go_coffee))

        errorMessage?.let {
            Text(
                text = "An error occured: $it",
                color = Color.Red,
            )
        } ?: run {
            MenuContent(setErrorMessage)
        }
    }
}

@Composable
fun MenuContent(setErrorMessage: (String) -> Unit) {
    val categories = listOf(
        "Coffee" to listOf(
            MenuItem(
                "Cappuccino",
                R.drawable.cappuccino,
                3.99,
                "A classic Italian coffee drink made with equal parts espresso, steamed milk, and milk foam."
            ),
            MenuItem(
                "Latte",
                R.drawable.latte,
                4.25,
                "A coffee drink made with espresso and steamed milk, topped with a small layer of milk foam."
            ),
            MenuItem(
                "Americano",
                R.drawable.americano,
                3.50,
                "A simple coffee drink made by diluting espresso with hot water, resulting in a similar strength to drip coffee but different flavor."
            ),
            MenuItem(
                "Matcha Latte",
                R.drawable.matcha_latte,
                4.50,
                "An espresso-based drink with steamed milk, similar to a latte but with less milk and a thinner layer of microfoam."
            )
        ),
        "Tea" to listOf(
            MenuItem(
                "Earl Grey",
                R.drawable.earl_grey,
                2.99,
                "A classic black tea flavored with oil of bergamot, known for its distinctive citrusy aroma."
            ),
            MenuItem(
                "Green Tea",
                R.drawable.green_tea,
                3.25,
                "A light, refreshing tea made from unoxidized leaves, rich in antioxidants and with a subtle, grassy flavor."
            ),
            MenuItem(
                "Chamomile",
                R.drawable.chamomile,
                2.75,
                "A caffeine-free herbal tea made from chamomile flowers, known for its calming properties and delicate, apple-like flavor."
            ),
            MenuItem(
                "Chai Latte",
                R.drawable.chai_latte,
                4.50,
                "A spiced tea-based drink made with black tea, milk, and a mixture of aromatic herbs and spices."
            )
        ),
        "Pastries" to listOf(
            MenuItem(
                "Croissant",
                R.drawable.croissant,
                2.50,
                "A buttery, flaky pastry of Austrian origin but largely associated with France. Crisp on the outside with a soft, airy interior."
            ),
            MenuItem(
                "Blueberry Muffin",
                R.drawable.blueberry_muffin,
                3.25,
                "A sweet, cake-like bread filled with juicy blueberries and topped with a crumbly streusel."
            ),
            MenuItem(
                "Cinnamon Roll",
                R.drawable.cinnamon_roll,
                3.75,
                "A sweet roll served commonly in Northern Europe and North America, flavored with cinnamon and topped with cream cheese frosting."
            ),
            MenuItem(
                "Danish Pastry",
                R.drawable.danish_pastry,
                3.50,
                "A multilayered, laminated sweet pastry in the viennoiserie tradition, often filled with fruit or cream cheese."
            )
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical =  4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categories.forEach { (category, items) ->
            item {
                CategoryName(name = category)
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(items) { item ->
                        DisplayItem(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun TitleName(name: String) {
    Text(
        text = name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.SemiBold
        ),
        textAlign = TextAlign.Center,
        color = Color.White
    )
}

@Composable
fun CategoryName(name: String) {
    Text(
        text = name,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineMedium,
        color = Color.White
    )
}

@Composable
fun DisplayItem(item: MenuItem) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(8.dp)

    ) {
        AsyncImage(
            model = item.image,
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            error = painterResource(id = R.drawable.placeholder)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$${String.format("%.2f", item.price)}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Text(
            text = item.description,
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {},
            Modifier.height(36.dp)

        ){
            Text("+ add to cart")
        }
    }
}