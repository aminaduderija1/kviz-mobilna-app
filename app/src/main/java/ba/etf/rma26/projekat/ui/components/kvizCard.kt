package ba.etf.rma26.projekat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.etf.rma26.projekat.R
import ba.etf.rma26.projekat.data.models.Kviz
import ba.etf.rma26.projekat.data.models.KvizStatusBoja
import ba.etf.rma26.projekat.data.models.getStatus

@Composable
fun KvizCard(
    kviz: Kviz,
    modifier: Modifier = Modifier
) {
    val status = kviz.getStatus()

    val (ikonaRes, contentDesc) = when (status.boja) {
        KvizStatusBoja.PLAVA  -> R.drawable.plava  to "Urađen"
        KvizStatusBoja.ZUTA   -> R.drawable.zuta   to "Budući"
        KvizStatusBoja.ZELENA -> R.drawable.zelena to "Aktivan"
        KvizStatusBoja.CRVENA -> R.drawable.crvena to "Istekao"
    }

    Card(
        modifier = modifier
            .padding(8.dp)
            .testTag("kviz_item_${kviz.naziv}"),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = kviz.nazivPredmeta ?: "",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(end = 24.dp),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Image(
                    painter = painterResource(id = ikonaRes),
                    contentDescription = contentDesc,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.TopEnd)
                        .testTag("kviz_status_icon")
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = kviz.naziv,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Datum: %02d.%02d.%d".format(
                    status.datum.dayOfMonth,
                    status.datum.monthValue,
                    status.datum.year
                ),
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${kviz.trajanje} min",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                if (kviz.osvojeniBodovi != null) {
                    Text(
                        text = "Bodovi: ${kviz.osvojeniBodovi}",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}