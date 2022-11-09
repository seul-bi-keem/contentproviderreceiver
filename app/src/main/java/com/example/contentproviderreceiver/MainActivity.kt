package com.example.contentproviderreceiver

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.tvdata)

        findViewById<Button>(R.id.btnLog).setOnClickListener {
            Thread {
                val contentResolverHelper = ContentResolverHelper(this)
                contentResolverHelper.buildingDataBaseOfGiverApp()
                val items = contentResolverHelper.getAllItems()
                var result = ""
                runOnUiThread {
                    for(item in items) {
                        val str = "${item.itemId} / ${item.key} / ${item.value}"
                        result += str + "\n"
                    }
                    textView.text = result
                }
            }.start()
        }

    }
}