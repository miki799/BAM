package com.example.lab1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1.ui.theme.LAB1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init database
        AppDatabase.getDatabase(applicationContext)

        setContent {
            LAB1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppLayout()
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppLayout(modifier: Modifier = Modifier) {
    var nameInput by remember {
        mutableStateOf("")
    }

    val ctx = LocalContext.current

    Column(
        modifier = modifier
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            label = { Text(stringResource(R.string.name)) },
            singleLine = true,
            value = nameInput,
            onValueChange = { nameInput = it },
            modifier = modifier
                .fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = nameInput.isNotEmpty(),
            onClick = {
                val intent = Intent(ctx, UserActivity::class.java)
                intent.putExtra("name", nameInput)
                ctx.startActivity(intent)
            }
        ) {
            Text(stringResource(R.string.press_me))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    LAB1Theme {
        AppLayout()
    }
}