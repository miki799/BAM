package com.example.lab1

import android.content.Intent
import android.content.IntentFilter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.lab1.ui.theme.LAB1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserActivity : ComponentActivity() {

    private val numberReceiver = NumberReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name").orEmpty()

        // register broadcast receiver
        val intentFilter = IntentFilter("number_receiver")
        registerReceiver(numberReceiver, intentFilter)

        setContent {
            LAB1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserActivityLayout(name = name)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(numberReceiver)
    }
}

@Composable
fun UserActivityLayout(name: String, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    val intent = Intent(ctx, CounterService::class.java)
    intent.putExtra("name", name)

    var dbNotEmpty by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.display_name, name),
            fontSize = TextUnit.Unspecified
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (dbNotEmpty) {
                    CoroutineScope(Dispatchers.IO).launch {
                        DatabaseSingleton.getDatabase(ctx).dataDao().deleteAll()
                    }
                    dbNotEmpty = false
                }
                ctx.startService(intent)
            }
        ) {
            Text(stringResource(R.string.start_activity))
        }
        Button(
            onClick = {
                ctx.stopService(intent)
                dbNotEmpty = true
            }
        ) {
            Text(stringResource(R.string.stop_activities))
        }
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val size = DatabaseSingleton.getDatabase(ctx).dataDao().getAll().size
                    Log.d("UserActivity", "table size $size")
                }
            }
        ) {
            Text(stringResource(R.string.read_all_data_from_db))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LAB1Theme {
        UserActivityLayout("Android")
    }
}