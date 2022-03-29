package com.eightbars.test

import android.os.Parcelable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface WordCounter {

    /**
     * implementation should count words using a single thread and callback on main Thread
     */
    fun countWords(input: String, completion: (Map<String, Int>) -> Unit) {
        // default implementation counts words on single thread
        completion(input.countWords())
    }

    /**
     * implementation should count words on a single thread and callback on main Thread, using coroutines
     */
    suspend fun countWords(input: String) : Map<String, Int> = emptyMap()

    /**
     * implementation should count words on a single thread per line and callback on main Thread, using coroutines
     */
    suspend fun countWordsOneThreadPerLine(input: String) : Map<String, Int> = emptyMap()

    /**
     * Create a Flow that emits the counts for each row
     */
    suspend fun wordsOnLine(input: String) : Flow<Map<String, Int>> = emptyFlow()
}

@Parcelize
data class CountResult(val results: @RawValue List<WordCount>) : Parcelable

@Parcelize
data class WordCount(val string: String, val count: Int) : Parcelable

/**
 * Utility function to count words in text.
 */
fun String.countWords() : Map<String, Int> {
    val re = Regex("[^A-Za-z0-9 ]")
    return split(" ")
        .map {
            re.replace(it, "").lowercase(Locale.getDefault())
        }
        .groupBy { it }
        .mapValues { it.value.size }
}

fun Map<String, Int>.toCountResult() : CountResult =
    CountResult(this.entries.map { WordCount(it.key, it.value) })
