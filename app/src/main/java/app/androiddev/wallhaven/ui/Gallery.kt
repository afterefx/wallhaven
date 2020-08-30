package app.androiddev.wallhaven.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Gallery(
    gridList: List<List<WallpaperDetails>>,
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit
) {
    gridList.forEach { row ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            row.forEach { wallpaper ->
                ThumbNail(
                    wallpaper,
                    Modifier.clickable(onClick = {
                        updateId(wallpaper.id)
                        updateScreen(ScreenState.Detail)
                    })
                )
            }

        }
    }
}

@Composable
fun ThumbNail(wallpaper: WallpaperDetails, modifier: Modifier = Modifier) {
    CoilImage(
        data = wallpaper.thumbs.small,
        modifier = Modifier.size(150.dp) + modifier,
        loading = @Composable {
            Column(modifier = Modifier.fillMaxSize().gravity(align = Alignment.CenterVertically)) {
                Text("Loading", modifier = Modifier.gravity(align = Alignment.CenterVertically))
            }
        },
    )
}

@Composable
fun PageButtons(
    page: Int, onNext: () -> Unit, onPrev: () -> Unit, showPrev: () -> Boolean = {
        page > 1
    }
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
            .gravity(align = Alignment.CenterVertically),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (page > 1) {
            Button(onClick = onPrev) {
                Text(text = "Prev")
            }
        } else {
            Spacer(modifier = Modifier.width(40.dp))
        }

        Text(
            text = page.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Button(onClick = onNext) {
            Text(text = "Next")
        }

    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize().gravity(align = Alignment.CenterVertically).background(
            Color.Black
        )
    ) {
        Text(text = "Loading", fontSize = 40.sp, color = Color.White, textAlign = TextAlign.Center)
    }
}
