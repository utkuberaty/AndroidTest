package com.eightbars.test

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val result = intent.getParcelableExtra<CountResult>("MyResult")

        val listView = findViewById<ListView>(R.id.listView)
        val back = findViewById<Button>(R.id.back)
        val values =
            result?.results?.sortedBy { it.count }?.reversed()?.map { "${it.string}, ${it.count}" }
                .orEmpty()

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, values)
        listView.adapter = adapter
        back.setOnClickListener {
            finish()
        }
    }
}