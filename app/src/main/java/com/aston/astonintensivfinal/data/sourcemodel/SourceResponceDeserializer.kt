package com.aston.astonintensivfinal.data.sourcemodel

import com.aston.astonintensivfinal.data.ApiResponse
import com.aston.astonintensivfinal.data.ErrorResponce
import com.aston.astonintensivfinal.data.NewApiResponce
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
            /* Здесь может быть логика для определения и десериализации второго типа успешного ответа */
            else -> throw JsonParseException("Unknown type of the response")
        }
    }
}