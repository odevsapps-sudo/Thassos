package com.odevs.thassosscooterandatwfinder.screens

import android.R.attr.icon
import android.app.Activity
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odevs.thassosscooterandatwfinder.MainActivity
import com.odevs.thassosscooterandatwfinder.R
import com.odevs.thassosscooterandatwfinder.localization.LocalStrings
import com.odevs.thassosscooterandatwfinder.util.LanguageManager
import com.odevs.thassosscooterandatwfinder.weather.WeatherWidget
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun HomeScreen(onCardClick: (String) -> Unit = {}) {
    val context = LocalContext.current
    val activity = context as? MainActivity
    val strings = LocalStrings.current
    val scooterIcon = painterResource(id = R.drawable.scooter_icon)
    val restaurantIcon = painterResource(id = R.drawable.restaurants_icon)
    val beachIcon = painterResource(id = R.drawable.beaches_icon)
    val ferryIcon = painterResource(id = R.drawable.ferry_icon)

    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        )
    ).value

    val languages = listOf(
        "en" to R.drawable.flag_en,
        "hu" to R.drawable.flag_hu,
        "bg" to R.drawable.flag_bg,
        "ro" to R.drawable.flag_ro
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues()) // <-- EZ A LÉNYEG
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Bal oldal – App logó
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White, shape = RoundedCornerShape(28.dp))
                    .padding(4.dp)
            ) {
                val scale = rememberInfiniteTransition().animateFloat(
                    initialValue = 1f,
                    targetValue = 1.1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                ).value

                Image(
                    painter = painterResource(id = R.drawable.thassos_ride_logo_500x500),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(64.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                        .padding(4.dp)
                )
            }

            // Jobb oldal – Zászlók
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                languages.forEach { (code, flagRes) ->
                    IconButton(
                        onClick = {
                            LanguageManager.setAppLanguage(context, code)
                            activity?.recreate()
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Image(
                            painter = painterResource(id = flagRes),
                            contentDescription = code,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = { onCardClick("settings") },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(60.dp)
                    .graphicsLayer { rotationZ = rotation }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(48.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.thassos_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.6f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Kártyák felső része ---
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- Felső kártyák ---
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Nagy járműkártya középen
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            HomeCard(
                                icon = scooterIcon,
                                label = strings.vehicles,
                                onClick = { onCardClick("vehicles") },
                                iconSize = 90,
                                cardSize = 160
                            )
                        }

                        // Három kártya sorban
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            HomeCard(
                                icon = restaurantIcon,
                                label = strings.restaurants,
                                iconSize = 40,
                                cardSize = 100,
                                onClick = { onCardClick("restaurants") },
                                modifier = Modifier.weight(1f)
                            )
                            HomeCard(
                                icon = beachIcon,
                                label = strings.beaches,
                                iconSize = 40,
                                cardSize = 100,
                                onClick = { onCardClick("beaches") },
                                modifier = Modifier.weight(1f)
                            )
                            HomeCard(
                                icon = ferryIcon,
                                label = strings.ferries,
                                iconSize = 40,
                                cardSize = 100,
                                onClick = { onCardClick("ferries") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Kavala highlights kártya – új elem
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Bal oszlop: 3 kártya egymás alatt
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                HomeCard(
                                    icon = rememberVectorPainter(image = Icons.Default.Map),
                                    label = strings.mapLabel,
                                    iconSize = 40,
                                    cardSize = 90,
                                    onClick = { onCardClick("map") }
                                )
                                HomeCard(
                                    icon = painterResource(id = R.drawable.kavala_icon),
                                    label = "Kavala",
                                    iconSize = 40,
                                    cardSize = 90,
                                    onClick = { onCardClick("kavala_highlights") }
                                )
                                HomeCard(
                                    icon = painterResource(id = R.drawable.road_icon),
                                    label = strings.reachThassos,
                                    iconSize = 40,
                                    cardSize = 90,
                                    onClick = { onCardClick("reach_thassos") }
                                )
                            }

                            // Jobb oldalt: időjárás widget
                            WeatherWidget(
                                apiKey = "864efc4e08dd434103380b1d7b6768b5",
                                modifier = Modifier
                                    .width(200.dp)
                                    .wrapContentHeight() // ez alkalmazkodik a tartalomhoz
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun HomeCard(
    icon: Painter,
    label: String,
    onClick: () -> Unit,
    iconSize: Int = 40,
    cardSize: Int = 160,
    modifier: Modifier = Modifier // ÚJ
) {
    Column(
        modifier = modifier
            .size(cardSize.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(iconSize.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black,
            lineHeight = 16.sp
        )
    }
}

