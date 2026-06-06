package ba.etf.rma26.projekat.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.etf.rma26.projekat.R
import ba.etf.rma26.projekat.data.KvizStaticData
import ba.etf.rma26.projekat.model.Kviz
import ba.etf.rma26.projekat.ui.theme.ljubS2
import ba.etf.rma26.projekat.ui.theme.ljubT1
import ba.etf.rma26.projekat.ui.theme.ljubT3
import java.time.LocalDateTime
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image

fun getStatus(kviz : Kviz): Triple<Int, LocalDateTime, String> {
    val referentniDatum = KvizStaticData.getReferentDate()
    if(kviz.datumRada != null) {
        return Triple(R.drawable.plava, kviz.datumRada, "Plava")
    }
    else if(kviz.datumPocetak.isAfter(referentniDatum)) {
        return Triple(R.drawable.zuta, kviz.datumPocetak, "Zuta")
    }
    else if(kviz.datumKraj.isBefore(referentniDatum)) {
        return Triple(R.drawable.crvena, kviz.datumKraj, "Crvena")
    }
        return Triple(R.drawable.zelena, kviz.datumKraj, "Zelena")

}

@Composable
fun KvizCard (kviz: Kviz) {
    val boja = getStatus(kviz).first
    val datum = getStatus(kviz).second
    val desc = getStatus(kviz).third
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.dp)
            .testTag("kviz_item_${kviz.naziv}") ,
        colors = CardDefaults.cardColors(containerColor = ljubT3),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = kviz.nazivPredmeta,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Monospace,
                    color = ljubT1,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Image(
                    painter = painterResource(id = boja),
                    contentDescription = desc,
                   modifier = Modifier
                       .size(17.dp)
                       .align(Alignment.TopEnd)
                       .testTag("kviz_status_icon")
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = CardDefaults.cardColors(containerColor = ljubS2)
            ) {
                Column() {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(
                            text = kviz.naziv,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.Center),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Light,
                            color = ljubT1,
                            letterSpacing = 2.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    ) {
                        Text (
                            text = "%02d.%02d.%d".format(
                                datum.dayOfMonth,
                                datum.monthValue,
                                datum.year),
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.Center),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Light,
                            color = ljubT1,
                            letterSpacing = 3.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "${kviz.trajanje} min",
                            modifier = Modifier.align(Alignment.CenterStart),
                            fontSize = 18.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Light,
                            color = ljubT1,
                            letterSpacing = 3.sp
                        )
                        if(kviz.osvojeniBodovi != null) {
                            Text(
                                text = "${kviz.osvojeniBodovi}",
                                modifier = Modifier.align(Alignment.CenterEnd),
                                fontSize = 18.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Light,
                                color = ljubT1,
                                letterSpacing = 3.sp
                            )
                        }
                    }
                }
            }
        }
    }
}