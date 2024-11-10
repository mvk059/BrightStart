package fyi.manpreet.brightstart.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
) {

    IconButton(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF36363D), CircleShape),
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@Composable
@Preview
fun RoundButtonPreview() {
    MaterialTheme {
        RoundButton(
            onClick = {},
            icon = CheckIcon,
        )
    }
}
