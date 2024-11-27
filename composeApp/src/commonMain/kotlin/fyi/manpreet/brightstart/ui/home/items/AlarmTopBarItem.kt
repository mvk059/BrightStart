package fyi.manpreet.brightstart.ui.home.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fyi.manpreet.brightstart.ui.home.items.shape.TopBarMiniShape
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlarmTopBarItem(
    modifier: Modifier = Modifier
) {

    Column {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.White)
                .padding(vertical = 16.dp),
        ) {

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Bright Start",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(color = Color.White, shape = TopBarMiniShape())
        )
    }
}

@Composable
@Preview
fun AlarmTopBarItemPreview() {
//    MaterialTheme {
//        AlarmTopBarItem()
//    }
}