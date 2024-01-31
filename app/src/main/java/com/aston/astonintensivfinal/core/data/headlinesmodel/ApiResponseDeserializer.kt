package com.aston.astonintensivfinal.core.data.headlinesmodel

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ApiResponseDeserializer : JsonDeserializer<ApiResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ApiResponse {
        val jsonObject = json.asJsonObject

        return when {
            jsonObject.has("status") && jsonObject.get("status").asString == "error" -> {
                Gson().fromJson(json, ErrorResponce::class.java)
            }
            jsonObject.has("totalResults") -> {
                Gson().fromJson(json, NewApiResponce::class.java)
            }

            else -> throw JsonParseException("Unknown type of the response")
        }
    }
}


