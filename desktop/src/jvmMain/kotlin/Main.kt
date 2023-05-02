import de.hsflensburg.common.App
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
        state = WindowState(width = 1000.dp, height = 900.dp),
        title = "BookCircle") {
        MaterialTheme {
            App()
        }
    }
}