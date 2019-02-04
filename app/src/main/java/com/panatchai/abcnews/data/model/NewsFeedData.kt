package com.panatchai.abcnews.data.model

import org.json.JSONObject

/**
 * Feed API response.
 */
data class JsonResponse(private val  json: String) : JSONObject(json) {
    val feedItems = optJSONArray("items")
        ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
        ?.map { NewsFeedData(it.toString()) } // transforms each JSONObject of the array into NewsFeedData
}

/**
 * Represent data-layer feed vo.
 */
data class NewsFeedData(private val json: String) : JSONObject(json) {
    val title = optString("title")
    val pubDate = optString("pubDate")
    val thumbnail = optString("thumbnail")
    val enclosure: Enclosure = Enclosure(optString("enclosure"))
}

data class Enclosure(private val json: String) : JSONObject(json) {
    val link = optString("link")
    val type = optString("type")
}