package fm.mrc.t20clubapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun KiteIcon(
    modifier: Modifier = Modifier,
    score: Int
) {
    val kiteColor = when {
        score >= 90 -> MaterialTheme.colorScheme.primary
        score >= 75 -> MaterialTheme.colorScheme.secondary
        score >= 60 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    Canvas(modifier = modifier.size(48.dp)) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2

        // Draw kite body
        val kitePath = Path().apply {
            moveTo(centerX, centerY - height * 0.4f)
            lineTo(centerX + width * 0.3f, centerY)
            lineTo(centerX, centerY + height * 0.4f)
            lineTo(centerX - width * 0.3f, centerY)
            close()
        }
        drawPath(
            path = kitePath,
            color = kiteColor,
            style = Stroke(width = 2.dp.toPx())
        )

        // Draw kite tail
        drawLine(
            color = kiteColor,
            start = Offset(centerX, centerY + height * 0.4f),
            end = Offset(centerX, centerY + height * 0.7f),
            strokeWidth = 2.dp.toPx()
        )

        // Draw kite string
        drawLine(
            color = kiteColor,
            start = Offset(centerX, centerY - height * 0.4f),
            end = Offset(centerX - width * 0.2f, centerY - height * 0.7f),
            strokeWidth = 1.dp.toPx()
        )
    }
} 