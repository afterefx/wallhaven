package com.mcwilliams.letscompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Arrangement
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
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
        topAppBar = {
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
            Text(text = "Hello World")
        },
        bottomAppBar = {
            BottomNavigation(
                content = {
                    listOf(
                        BottomNavigationItem(
                            selected = true, 
                            text = { Text(text = "Home")},
                            onSelected = {},
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp))}
                        ),
                        BottomNavigationItem(
                            selected = false,
                            text = { Text(text = "Chat")},
                            onSelected = {},
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_chat_white_24dp))}
                        ),
                        BottomNavigationItem(
                            selected = false,
                            text = { Text(text = "Profile")},
                            onSelected = {},
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_account_circle_white_24dp))}
                        )
                    )
                }
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LetsComposeTheme {
        Container()
    }
}