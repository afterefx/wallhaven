package com.mcwilliams.letscompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.mcwilliams.letscompose.ui.LetsComposeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsComposeTheme {
                Container()
            }
        }
    }
}

@Composable
fun Container() {
    Scaffold(
         topBar = {
            TopAppBar(
                title = { Text(text = "Lets Compose") }
//                navigationIcon = {
//                    IconButton(onClick = { }) {
//                        Icon(vectorResource(R.drawable.ic_input_add))
//                    }
//                }
            )
        },
        bodyContent = {
            homeBody()
        },
        bottomBar = {
            BottomNavigation(
                content = {
                    listOf(
                        BottomNavigationItem(
                            selected = true,
                            text = { Text(text = "Home") },
                            onSelected = {},
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp)) }
                        ),
                        BottomNavigationItem(
                            selected = false,
                            text = { Text(text = "Chat") },
                            onSelected = {},
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_chat_white_24dp)) }
                        ),
                        BottomNavigationItem(
                            selected = false,
                            text = { Text(text = "Profile") },
                            onSelected = {},
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_account_circle_white_24dp)) }
                        )
                    )
                }
            )
        }
    )
}

@Composable
fun homeBody() {
    VerticalScroller {
        for (x in 0..15) {
            Row(modifier = Modifier.fillMaxWidth().absolutePadding(16.dp, 8.dp, 16.dp, 8.dp)) {
                Card(modifier = Modifier.fillMaxWidth().height(60.dp),shape = MaterialTheme.shapes.medium, elevation = 4.dp) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = x.toString(),
                            style = MaterialTheme.typography.h6,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LetsComposeTheme {
        Container()
    }
}