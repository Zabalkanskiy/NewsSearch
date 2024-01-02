package com.aston.astonintensivfinal.headlines.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeadlinesNewsModelError(

    val status: String? = null,

    val code: String? = null,

    val message: String? = null
) : HeadlineNewsResponce()
