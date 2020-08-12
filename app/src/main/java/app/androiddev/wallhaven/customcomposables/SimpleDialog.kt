package app.androiddev.wallhaven.customcomposables

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Dialog
import androidx.ui.foundation.ProvideTextStyle
import androidx.ui.foundation.Text
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.unit.dp
import app.androiddev.wallhaven.ui.search.SearchViewModel

@Composable
fun showSearchDialog(
    onPopupDismissed: () -> Unit,
    contentView: @Composable () -> Unit,
    viewModel: SearchViewModel
) {
    val AlertDialogWidth = Modifier.preferredWidth(312.dp)
    val TitlePadding = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 0.dp)
    val emphasisLevels = EmphasisAmbient.current

    Dialog(onCloseRequest = onPopupDismissed) {
        Surface(
            modifier = AlertDialogWidth,
            shape = MaterialTheme.shapes.medium
        ) {
            Column() {
                Box(TitlePadding.gravity(Alignment.Start)) {
                    ProvideEmphasis(emphasisLevels.high) {
                        val textStyle = MaterialTheme.typography.subtitle1
                        ProvideTextStyle(textStyle) { Text(text = "Search") }
                    }
                }

                Spacer(Modifier.preferredHeight(14.dp))

                //This field broke when entering text
                //TODO how would you handle getting data out the content composable on Button Click
                var textValue by state { TextFieldValue("") }
                FilledTextField(value = textValue,
                    label = { Text(text = "City, State or Zip") },
                    modifier = Modifier.padding (16.dp) + Modifier.fillMaxWidth(),
                    // Update value of textValue with the latest value of the text field
                    onFocusChanged = {
                        textValue = TextFieldValue("")
                    },
                    onValueChange = {
                        textValue = it
                    }
                )
                contentView

                Spacer(Modifier.preferredHeight(28.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onPopupDismissed
                    ) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.preferredWidth(8.dp))
                    TextButton(
                        onClick = {
                            viewModel.search(textValue.text)
                        }
                    ) {
                        Text(text = "Search")
                    }
                }
            }
        }
    }
}