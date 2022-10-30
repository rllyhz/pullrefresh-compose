package id.rllyhz.compose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import id.rllyhz.compose.sample.ui.theme.PullToRefreshTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullToRefreshTheme {
                SampleScreen()
            }
        }
    }
}

@Composable
fun SampleScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Text(text = "Hello from Compose Sample App")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PullToRefreshTheme {
        SampleScreen()
    }
}