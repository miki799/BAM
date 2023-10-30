package com.example.lab1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumberReceiver : BroadcastReceiver() {

    object NumberReceiverConstants {
        const val TAG = "number_receiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra("service_id", 0)
        val name = intent.getStringExtra("name")
        val number = intent.getIntExtra("number", 0)
        Log.d(NumberReceiverConstants.TAG, "Service instance Id: $id, Name: $name, Number: $number")

        CoroutineScope(Dispatchers.IO).launch {
            val data = DataEntity(name = name, number = number)
            AppDatabase.getDatabase(context.applicationContext).dataDao().insert(data)
        }

    }
}