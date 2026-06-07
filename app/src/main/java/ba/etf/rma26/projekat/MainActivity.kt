package ba.etf.rma26.projekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import ba.etf.rma26.projekat.data.repositories.AccountRepository
import ba.etf.rma26.projekat.data.repositories.ApiConfig
import ba.etf.rma26.projekat.navigation.AppNavGraph
import ba.etf.rma26.projekat.ui.theme.KvizTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiConfig.postaviBaseURL("http://10.0.2.2:3000")
        ApiConfig.postaviApiKey(null)
        lifecycleScope.launch {
            AccountRepository.postaviHash("demo")
        }
        setContent {
            KvizTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}