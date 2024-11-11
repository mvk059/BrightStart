package fyi.manpreet.brightstart.ui.model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

//fun List<Hour>.xyz() =
//    this.map { if (it.value.toString().length == 1) "0${it.value}" else it.value.toString() }
//
//fun List<Minute>.xyz() =
//    this.map { if (it.value.toString().length == 1) "0${it.value}" else it.value.toString() }
//
//fun List<TimePeriod>.xyz() = this.map { it.value.toString() }

fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    ) {
        onClick()
    }
}
