package com.aston.astonintensivfinal.core.data.sourcemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsSourceResponse : SourceResponse() {
    @SerializedName("status")
    @Expose
     var status: String? = null

    @SerializedName("sources")
    @Expose
     var sources: List<SourceNews?>? = null

}