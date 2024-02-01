package com.search.news.core.data.sourcemodel

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class SourceResponceDeserializer : JsonDeserializer<SourceResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SourceResponse {
        val jsonObject = json.asJsonObject

        return when {
            jsonObject.has("status") && jsonObject.get("status").asString == "error" -> {
                Gson().fromJson(json, NewsSourceErrorResponse::class.java)
            }

            jsonObject.has("sources") -> {
                Gson().fromJson(json, NewsSourceResponse::class.java)
            }

            else -> throw JsonParseException("Unknown type of the response")
        }
    }
}