package com.aston.astonintensivfinal.headlines.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.data.Article

class HeadlinesGeneralViewHolder(itemView: View) : ViewHolder(itemView) {
    val nameNews: TextView = itemView.findViewById(R.id.recycler_view_headlines_text_view_name_news)
    val description = itemView.findViewById<TextView>(R.id.recycler_view_headlines_text_view_description_news)
    val bageImage: ImageView = itemView.findViewById(R.id.recycler_view_headlines_image_bage)
    val newsImage: ImageView = itemView.findViewById(R.id.recycler_view_headlines_image_news)

    fun bind(model: Article){
        nameNews.text = model.title
        description.text = model.description
        //need add coil or another library load image from url

    }
}