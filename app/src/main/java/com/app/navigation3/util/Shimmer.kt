package com.app.navigation3.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.app.navigation3.ui.screens.compositeOver

/**
 * Applies a shimmer loading effect to the [Modifier].
 *
 * This effect animates a linear gradient across the background of the component,
 * creating a "loading" visual.
 *
 * @param color The base color of the background and the shimmer's edges.
 * @return A [Modifier] with the shimmer effect applied.
 */
fun Modifier.shimmerEffect(color: Color): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "Shimmer")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerOffset"
    )
    val highlightColor = Color.White.copy(alpha = 0.2f).compositeOver(color)
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                color,          
                highlightColor, 
                color       
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned { size = it.size }
}
