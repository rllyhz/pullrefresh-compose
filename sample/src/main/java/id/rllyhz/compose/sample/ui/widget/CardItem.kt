package id.rllyhz.compose.sample.ui.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CardItem(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    contentColor: Color = Color.Black,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        modifier = modifier,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            textAlign = TextAlign.Center,
        )
    }
}