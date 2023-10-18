package com.example.lab1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NumberReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        val number = intent.getIntExtra("number", 0)
        Log.d("NumberReceiver", "Service instance Id: $id, Name: $name, Number: $number")

        CoroutineScope(Dispatchers.IO).launch {
            val data = DataEntity(uid = id, name = name, number = number)
            DatabaseSingleton.getDatabase(context.applicationContext).dataDao().insert(data)
        }

    }
}