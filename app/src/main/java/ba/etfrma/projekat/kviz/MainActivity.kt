package ba.etfrma.projekat.kviz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import ba.etfrma.projekat.kviz.ui.theme.ljubS3
import androidx.compose.ui.tooling.preview.Preview
import ba.etfrma.projekat.kviz.data.KvizStaticData
import ba.etfrma.projekat.kviz.ui.components.ScreenKvizovi
import ba.etfrma.projekat.kviz.ui.components.ShowKviz
import ba.etfrma.projekat.kviz.ui.theme.KvizTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KvizTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ScreenKvizovi()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenKvizoviPreview() {
    ScreenKvizovi()
}