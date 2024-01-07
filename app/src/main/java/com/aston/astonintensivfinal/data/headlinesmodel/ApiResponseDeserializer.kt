package com.aston.astonintensivfinal.data.headlinesmodel

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
            /* Здесь может быть логика для определения и десериализации второго типа успешного ответа */
            else -> throw JsonParseException("Unknown type of the response")
        }
    }
}

// Создайте экземпляр Gson с зарегистрированным десериализатором ApiResponseDeserializer
