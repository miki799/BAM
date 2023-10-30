package com.example.lab1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterService : Service() {
    object CounterServiceConstants {
        const val TAG = "counter_service"
    }

    private val serviceInstancesMap = mutableMapOf<Int, ServiceInstance>()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        serviceInstancesMap[startId] = ServiceInstance(startId, intent?.getStringExtra("name").orEmpty())

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        val amount = serviceInstancesMap.size
        serviceInstancesMap.forEach {
            val serviceInstance = it.value
            val intent = Intent("number_receiver")
            intent.putExtra("service_id", serviceInstance.getId())
            intent.putExtra("name", serviceInstance.getName())
            intent.putExtra("number", serviceInstance.getCounterValue())
            sendBroadcast(intent)
            serviceInstance.cancelJob()
        } // cancel jobs in coroutines
        Log.d(CounterServiceConstants.TAG, "$amount CounterService instances stopped")
    }

    override fun onBind(intent: Intent?): IBinder? {
        // not needed
        return null
    }

    private class ServiceInstance(private var startId: Int, private var name: String) {
        private var counter = 0
        private var job: Job? = null

        init {
            Log.d(CounterServiceConstants.TAG, "CounterService instance $startId created")
            job = CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    Log.d(CounterServiceConstants.TAG, "CounterService instance $startId, Counter: $counter")
                    counter++
                    delay(1000) // wait 1s
                }
            }
        }

        fun getId(): Int {
            return startId
        }

        fun getName(): String {
            return name
        }

        fun getCounterValue(): Int {
            return counter
        }

        fun cancelJob() {
            job?.cancel()
        }
    }
}

