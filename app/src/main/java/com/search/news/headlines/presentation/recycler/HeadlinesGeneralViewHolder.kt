package com.search.news.headlines.presentation.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.search.news.SearchApplication
import com.search.news.R
import com.search.news.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class HeadlinesGeneralViewHolder(itemView: View) : ViewHolder(itemView) {
    val nameNews: TextView = itemView.findViewById(R.id.recycler_view_headlines_item_text_view_name_news_agency)
    val description = itemView.findViewById<TextView>(R.id.recycler_view_headlines_item_text_view_description)
    val bageImage: ImageView = itemView.findViewById(R.id.recycler_view_headlines_image_bage)
    val newsImage: ImageView = itemView.findViewById(R.id.recycler_view_headlines_image)

    fun bind(model: HPresenterNewsModelData){
        val sourceName = model.source ?: "Unknown source"
        nameNews.text = sourceName

        description.text = model.content

        when {
           sourceName.contains("Unknown source")  -> bageImage.setImageResource(R.drawable.placeholder_bage)
            sourceName.contains("Daily Mail", ignoreCase = true) -> bageImage.setImageResource(R.drawable.daily_maill)
            sourceName.contains("The New York Times", ignoreCase = true) -> bageImage.setImageResource(R.drawable.tegraph)
            sourceName.contains("CNN", ignoreCase = true) -> bageImage.setImageResource(R.drawable.cnn)
            sourceName.contains("FOX NEWS", ignoreCase = true) -> bageImage.setImageResource(R.drawable.fox)
            sourceName.contains("Bloomberg", ignoreCase = true) -> bageImage.setImageResource(R.drawable.bloomberg)
            else -> bageImage.setImageResource(R.drawable.placeholder_bage)
        }

        Glide.with(SearchApplication.getAstonApplicationContext)
            .load(model.urlToImage)
            .placeholder(R.drawable.placeholder_bage)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(newsImage)


    }
}