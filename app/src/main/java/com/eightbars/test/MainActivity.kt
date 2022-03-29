package com.eightbars.test

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), WordCounter  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
           // countWords(textView.text.toString()) {  startNext(it.toCountResult()) }
            lifecycleScope.launch {
                startNext(countWordsOneThreadPerLine(textView.text.toString()).toCountResult())

                wordsOnLine(textView.text.toString())
                    .map { it.toCountResult() }
                    .collect {  print(it) }
            }

        }
    }

    private fun startNext(count: CountResult) {
        assert(Looper.myLooper() == Looper.getMainLooper())
        startActivity(Intent(this@MainActivity, ResultActivity::class.java).apply {
            putExtra("MyResult", count)
        })
    }
}




