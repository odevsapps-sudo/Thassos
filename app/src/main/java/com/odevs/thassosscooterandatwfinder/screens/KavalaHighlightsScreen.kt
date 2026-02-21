package com.odevs.thassosscooterandatwfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.odevs.thassosscooterandatwfinder.R
import com.odevs.thassosscooterandatwfinder.localization.LocalStrings
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import android.content.Intent
import android.net.Uri

@Composable
fun KavalaHighlightsScreen(navController: NavController) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    val linkText = buildAnnotatedString {
        append("🛳️ ${strings.ferryInfoText} ")

        pushStringAnnotation(
            tag = "URL",
            annotation = "http://www.thassos-ferries.gr/en/Itineraries.php?cat_id=1"
        )
        withStyle(style = SpanStyle(color = Color(0xFF1E88E5))) {
            append(strings.ferryWebsite)
        }
        pop()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(16.dp)
            .navigationBarsPadding()
    ) {
        Text(
            text = "🌆 ${strings.sights_label} - Kavala",
            fontSize = 24.sp,
            color = Color(0xFF2D2D2D),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = strings.prinosKavalaInfo,
            fontSize = 16.sp,
            color = Color(0xFF444444),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        KavalaHighlightItem(
            title = "The Fortress of Kavala",
            imageRes = R.drawable.kavala_castle,
            description = strings.kavala_castle_desc
        )

        KavalaHighlightItem(
            title = "Kamares Aqueduct",
            imageRes = R.drawable.kavala_aqueduct,
            description = strings.kavala_aqueduct_desc
        )

        KavalaHighlightItem(
            title = "Kavala Old Port",
            imageRes = R.drawable.kavala_port,
            description = strings.kavala_port_desc
        )

        KavalaHighlightItem(
            title = "Imaret",
            imageRes = R.drawable.imaret,
            description = strings.highlight_imaret
        )

        KavalaHighlightItem(
            title = "Old Town",
            imageRes = R.drawable.panagia_district,
            description = strings.highlight_old_town
        )

        KavalaHighlightItem(
            title = "Mohammed Ali’s House",
            imageRes = R.drawable.mohammed_ali_house,
            description = strings.highlight_ali_house
        )

        KavalaHighlightItem(
            title = "Tobacco Museum",
            imageRes = R.drawable.tobacco_museum,
            description = strings.highlight_tobacco_museum
        )

        Spacer(modifier = Modifier.height(24.dp))

        ClickableText(
            text = linkText,
            style = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color(0xFF444444)),
            onClick = { offset ->
                linkText.getStringAnnotations("URL", offset, offset).firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
            }
        )
    }
}

@Composable
fun KavalaHighlightItem(title: String, imageRes: Int, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color(0xFF1E1E1E),
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color(0xFF555555),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
