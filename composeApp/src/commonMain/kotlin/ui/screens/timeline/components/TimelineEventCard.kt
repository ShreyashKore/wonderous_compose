package ui.screens.timeline.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.TenorSans
import ui.theme.black
import ui.theme.greyStrong
import ui.theme.white
import utils.StringUtils
import kotlin.math.absoluteValue


@Composable
fun TimelineEventCard(
    year: Int, text: String,
    darkMode: Boolean = false,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (darkMode) greyStrong else white
    val contentColor = if (darkMode) white else black

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(backgroundColor, contentColor),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date
            Column(
                modifier = Modifier
                    .width(75.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "${year.absoluteValue}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.W500,
                        lineHeight = 20.sp,
                        fontFamily = TenorSans
                    )
                )
                Text(
                    text = StringUtils.getYrSuffix(year),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = TenorSans,
                        fontWeight = FontWeight.W500
                    )
                )
            }

            // Divider
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(1.dp)
                    .fillMaxHeight(),
                color = contentColor
            )

            // Text content
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 20.sp
                )
            )
        }
    }

}