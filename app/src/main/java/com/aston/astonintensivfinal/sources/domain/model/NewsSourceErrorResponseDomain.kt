package com.aston.astonintensivfinal.sources.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsSourceErrorResponseDomain(
    val status: String? ,


    val code: String? ,


    val message: String? ) : SourceResponseDomain()