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
import com.example.grabngocoffee.ui.theme.GrabnGoCoffeeTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextDecoration

data class MenuItem(
    val name: String,
    val image: Int = R.drawable.placeholder,
    val price: Double,
    val description: String
)

data class UserProfile(
    val firstName: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val rewardsMember: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrabnGoCoffeeTheme {
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
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AsyncImage(
                    model = R.drawable.coffee_icon,
                    contentDescription = "Coffee Icon",
                    modifier = Modifier
                        .size(40.dp)
                )

                TitleName(name = stringResource(R.string.grab_and_go_coffee))
            }
        }
        var menuActivePage by remember {
            mutableStateOf(true)
        }
        var profileActivePage by remember {
            mutableStateOf(false)
        }
        var historyActivePage by remember {
            mutableStateOf(false)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "menu",
                color = if (menuActivePage) Color.White else Color.Unspecified,
                textDecoration = if (menuActivePage) TextDecoration.Underline else TextDecoration.None,
                modifier = Modifier.clickable {
                    menuActivePage = true
                    profileActivePage = false
                    historyActivePage = false
                })
            Text(text = "past orders",
                color = if (historyActivePage) Color.White else Color.Unspecified,
                textDecoration = if (historyActivePage) TextDecoration.Underline else TextDecoration.None,
                modifier = Modifier.clickable {
                    historyActivePage = true
                    menuActivePage = false
                    profileActivePage = false
                })
            Text(text = "profile",
                color = if (profileActivePage) Color.White else Color.Unspecified,
                textDecoration = if (profileActivePage) TextDecoration.Underline else TextDecoration.None,
                modifier = Modifier.clickable {
                    profileActivePage = true
                    menuActivePage = false
                    historyActivePage = false
                })
        }

        errorMessage?.let {
            Text(
                text = "An error occured: $it",
                color = Color.Red,
            )
        } ?: run {
            when {
                menuActivePage -> MenuContent(setErrorMessage)
                profileActivePage -> profileContent(setErrorMessage)
                historyActivePage -> historyContent(setErrorMessage)
                else -> MenuContent(setErrorMessage)
            }
        }
    }
}

@Composable
fun historyContent(setErrorMessage: (String) -> Unit) {

}

@Composable
fun profileContent(setErrorMessage: (String) -> Unit) {
    var makeNewProfile by remember { mutableStateOf(false) }
    var profiles by remember { mutableStateOf(listOf<UserProfile>()) }

    Column(modifier = Modifier.padding(vertical = 8.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (makeNewProfile == true)
            NewProfile(
                onProfileCreated = { newProfile ->
                    profiles = profiles + newProfile
                    makeNewProfile = false
                },
                onCancel = { makeNewProfile = false }
            )
        else {
            ExistingProfiles(profiles = profiles)
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { makeNewProfile = true },
                modifier = Modifier.height(36.dp)
            ) {
                Text(text = "+ new profile")
            }
        }
    }
}

@Composable
fun NewProfile(
    onProfileCreated: (UserProfile) -> Unit,
    onCancel: () -> Unit
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var rewardsMember by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") }
        )
        TextField(value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last Name") })
        TextField(value = birthday,
            onValueChange = { birthday = it },
            label = { Text(text = "birthday") })
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = rewardsMember,
                onCheckedChange = { rewardsMember = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = "RewardsMember",
                color = Color.White
            )
        }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onCancel() },
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        if (firstName.isNotBlank() && lastName.isNotBlank() && birthday.isNotBlank()) {
                            val newProfile = UserProfile(firstName, lastName, birthday, rewardsMember)
                            onProfileCreated(newProfile)
                        }
                    },
                ) {
                    Text(text = "Create Profile")
                }
            }
    }
}

@Composable
fun ExistingProfiles(profiles: List<UserProfile>) {
    Column (modifier = Modifier.padding(16.dp)) {
        Text(
            "Existing Profiles",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (profiles.isEmpty()) {
            Text("No profiles created yet", color = Color.White)
        } else {
            profiles.forEach { profile ->
                ProfileItem(profile)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProfileItem(profile: UserProfile){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${profile.firstName} ${profile.lastName}", style = MaterialTheme.typography.titleMedium)
            Text("Birthday: ${profile.birthday}", style = MaterialTheme.typography.bodyMedium)
            Text(
                "Rewards Member: ${if (profile.rewardsMember) "Yes" else "No"}",
                style = MaterialTheme.typography.bodyMedium
            )
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
            .padding(horizontal = 16.dp, vertical = 6.dp),
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

        ) {
            Text("+ add to cart")
        }
    }
}