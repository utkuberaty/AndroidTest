package com.eightbars.test

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), WordCounter {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
//            countWords(textView.text.toString()) { startNext(it.toCountResult()) }
            lifecycleScope.launch {
                /*val exampleReal = mutableMapOf<String, Int>()
                val exampleList = multiThreadedCountWorks(textView.text.toString()).toList()

                exampleList.forEach {
                    it.keys.forEach { key ->
                        val currentSize = exampleReal[key] ?: 0
                        exampleReal[key] = currentSize + it[key]!!
                    }
                }*/

                /* multiThreadedCountWorks(textView.text.toString()).collect {
                     startNext(it.toCountResult())
                     Log.i("Example", "${it.keys} ${it.values}")
                 }*/
                /*countWordsWithFlow(textView.text.toString()).collectLatest {
                    startNext(it.toCountResult())
                }*/

                val countWorlds = multiThreadedCountWorks(textView.text.toString())
                startNext(countWorlds.toCountResult())
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




