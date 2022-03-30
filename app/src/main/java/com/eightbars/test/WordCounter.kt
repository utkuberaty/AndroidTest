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
    suspend fun singleThreadedCountWords(input: String) : Map<String, Int> = emptyMap()

    /**
     * implementation should count words on a single thread per line and callback on main Thread, using coroutines
     *
     * - how many threads should I user here?
     * - how may parts should I split the string into?
     * - how do you split the string?
     */
    suspend fun multiThreadedCountWorks(input: String) : Map<String, Int> = emptyMap()

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
    // how would you sanitise the string? are there any problems here
    return split(" ")
        .map {  it }
        .groupBy { it }
        .mapValues { it.value.size }
}

fun String.splitString() : List<String> {
    // how would you sanitise the string? are there any problems here
    return lines()
}

fun Map<String, Int>.toCountResult() : CountResult =
    CountResult(this.entries.map { WordCount(it.key, it.value) })
