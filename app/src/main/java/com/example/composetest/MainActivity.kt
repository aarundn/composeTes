package com.example.composetest

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                MyApp(
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }
}


@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}
@Composable
fun AnimeStyleCharacter(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        // Draw Head
        drawCircle(
            color = Color(0xFFFAD6A5),
            center = center.copy(y = center.y - 150),
            radius = 100f
        )

        // Draw Hair (spiky style)
        drawPath(
            color = Color.Black,
            path = Path().apply {
                moveTo(center.x, center.y - 250) // Top spike
                lineTo(center.x - 50, center.y - 150)
                lineTo(center.x + 50, center.y - 150)
                close()

                moveTo(center.x - 70, center.y - 200) // Left spike
                lineTo(center.x - 100, center.y - 100)
                lineTo(center.x - 20, center.y - 150)
                close()

                moveTo(center.x + 70, center.y - 200) // Right spike
                lineTo(center.x + 100, center.y - 100)
                lineTo(center.x + 20, center.y - 150)
                close()
            }
        )

        // Draw Body
        drawRect(
            color = Color(0xFF3F51B5), // Blue outfit
            topLeft = center.copy(x = center.x - 60, y = center.y - 50),
            size = Size(120f, 150f)
        )

        // Draw Arms
        drawRoundRect(
            color = Color(0xFFFAD6A5), // Skin color
            topLeft = center.copy(x = center.x - 90, y = center.y - 40),
            size = Size(30f, 100f),
            cornerRadius = CornerRadius(15f)
        )
        drawRoundRect(
            color = Color(0xFFFAD6A5),
            topLeft = center.copy(x = center.x + 60, y = center.y - 40),
            size = Size(30f, 100f),
            cornerRadius = CornerRadius(15f)
        )

        // Draw Legs
        drawRoundRect(
            color = Color.Black, // Black pants
            topLeft = center.copy(x = center.x - 40, y = center.y + 100),
            size = Size(40f, 120f),
            cornerRadius = CornerRadius(10f)
        )
        drawRoundRect(
            color = Color.Black,
            topLeft = center.copy(x = center.x, y = center.y + 100),
            size = Size(40f, 120f),
            cornerRadius = CornerRadius(10f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeStyleCharacterPreview() {
    AnimeStyleCharacter(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview() {
    ComposeTestTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    ComposeTestTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeTestTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}