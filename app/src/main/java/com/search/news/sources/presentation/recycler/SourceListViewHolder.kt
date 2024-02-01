package com.search.news.sources.presentation.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.search.news.R
import com.search.news.sources.presentation.viewmodel.model.sourseList.SourceNewsVM

class SourceListViewHolder(itemView: View) : ViewHolder(itemView){

    val nameAgency: TextView = itemView.findViewById(R.id.source_list_item_text_view_name_news_agency)
    val imageAgency: ImageView = itemView.findViewById(R.id.source_list_item_source_image)
    val categoryAgency: TextView = itemView.findViewById(R.id.source_list_item_text_view_description_agency)

    fun bind(model: SourceNewsVM){
        val sourceName = model.name ?: "Unknown source"
        nameAgency.text = sourceName

        categoryAgency.text = model.category

        when {
            sourceName.contains("Unknown source")  -> imageAgency.setImageResource(R.drawable.placeholder_bage)
            sourceName.contains("Daily Mail", ignoreCase = true) -> imageAgency.setImageResource(R.drawable.daily_maill)
            sourceName.contains("The New York Times", ignoreCase = true) -> imageAgency.setImageResource(R.drawable.tegraph)
            sourceName.contains("CNN", ignoreCase = true) -> imageAgency.setImageResource(R.drawable.cnn)
            sourceName.contains("FOX NEWS", ignoreCase = true) -> imageAgency.setImageResource(R.drawable.fox)
            sourceName.contains("Bloomberg", ignoreCase = true) -> imageAgency.setImageResource(R.drawable.bloomberg)
            else -> imageAgency.setImageResource(R.drawable.placeholder_bage)
        }
    }
}