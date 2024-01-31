package com.aston.astonintensivfinal.core.data.sourcemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsSourceErrorResponse : SourceResponse(){
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}