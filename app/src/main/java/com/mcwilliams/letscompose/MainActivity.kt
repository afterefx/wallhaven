package com.mcwilliams.letscompose

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.lifecycle.Observer
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.viewinterop.AndroidView
import androidx.ui.viewmodel.viewModel
import com.mcwilliams.letscompose.ui.LetsComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel : LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.cityData.observe(this, Observer {
            Log.d("TAG", "onCreate: ")
        })

        setContent {
            LetsComposeTheme {
                Container()
            }
        }
    }
}

@Composable
fun Container() {
    var currentScreen by state { MainActivityScreen.Activity }
    Scaffold(
        topBar = {
            TitleContent(currentScreen = currentScreen)
        },
        bodyContent = {
           BodyContent(currentScreen = currentScreen)
        },
        bottomBar = {
            BottomNavigation(
                content = {
                    listOf(
                        BottomNavigationItem(
                            selected = currentScreen == MainActivityScreen.Activity,
                            text = { Text(text = "Activity") },
                            onSelected = {
                                currentScreen = MainActivityScreen.Activity
                            },
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp)) }
                        ),
                        BottomNavigationItem(
                            selected = currentScreen == MainActivityScreen.Routine,
                            text = { Text(text = "Routines") },
                            onSelected = {
                                currentScreen = MainActivityScreen.Routine
                            },
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_chat_white_24dp)) }
                        ),
                        BottomNavigationItem(
                            selected = currentScreen == MainActivityScreen.Exercises,
                            text = { Text(text = "Exercises") },
                            onSelected = {
                                currentScreen = MainActivityScreen.Exercises
                            },
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_account_circle_white_24dp)) }
                        ),
                        BottomNavigationItem(
                            selected = currentScreen == MainActivityScreen.Profile,
                            text = { Text(text = "Profile") },
                            onSelected = {
                                currentScreen = MainActivityScreen.Profile
                            },
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_account_circle_white_24dp)) }
                        )
                    )
                }
            )
        }
    )
}

enum class MainActivityScreen {
    Activity,
    Routine,
    Exercises,
    Profile
}

@Composable
fun BodyContent(currentScreen: MainActivityScreen, modifier: Modifier = Modifier) {
    when (currentScreen) {
        MainActivityScreen.Activity -> ActivityContent(currentScreen, modifier)
        MainActivityScreen.Routine -> ActivityContent(currentScreen, modifier)
        MainActivityScreen.Exercises -> ActivityContent(currentScreen, modifier)
        MainActivityScreen.Profile -> SettingsContent(modifier)
    }
}

@Composable
fun TitleContent(currentScreen: MainActivityScreen, modifier: Modifier = Modifier) {
    when (currentScreen) {
        MainActivityScreen.Activity -> TopAppBar(currentScreen)
        MainActivityScreen.Routine -> TopAppBar(currentScreen)
        MainActivityScreen.Exercises -> TopAppBar(currentScreen)
        MainActivityScreen.Profile -> TopAppBar(currentScreen)
    }
}

@Composable
fun TopAppBar(currentScreen: MainActivityScreen){
    TopAppBar(
        title = { Text(text = currentScreen.name) }
    )
}

@Composable
fun ActivityContent(
    currentScreen: MainActivityScreen,
    modifier: Modifier
) {
    ScrollableColumn(modifier = modifier,children = {
        Row(modifier = Modifier.padding(8.dp)) {
            Button(onClick = {}, modifier = Modifier.padding(2.dp)) {
                Text(text = currentScreen.name)
            }
            TextButton(onClick = {}, modifier = Modifier.padding(2.dp)) {
                Text(text = currentScreen.name)
            }
        }
    })
}

@Composable
fun SettingsContent(modifier: Modifier){
    Column(modifier = modifier.height(600.dp)) {
        WebComponent(url = "https://www.google.com", webContext = WebContext())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LetsComposeTheme {
        Container()
    }
}