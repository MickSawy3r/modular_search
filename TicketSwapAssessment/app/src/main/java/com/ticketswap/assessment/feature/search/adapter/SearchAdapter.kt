package com.ticketswap.assessment.feature.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.ItemSearchBinding
import com.ticketswap.assessment.feature.search.datamodel.SearchListItemDataModel
import javax.inject.Inject
import kotlin.properties.Delegates

class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.SearchVH>() {

    internal var collection: List<SearchListItemDataModel> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (SearchListItemDataModel, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchVH {
        val layoutInflater = LayoutInflater.from(p0.context)
        val venueItemBinding = ItemSearchBinding.inflate(layoutInflater, p0, false)
        return SearchVH(venueItemBinding)
    }

    override fun onBindViewHolder(viewHolder: SearchVH, position: Int) {
        viewHolder.bind(collection[position], clickListener)
    }

    override fun getItemCount(): Int = collection.size

    class SearchVH(private val itemRow: ItemSearchBinding) :
        RecyclerView.ViewHolder(itemRow.root) {
        fun bind(
            searchItem: SearchListItemDataModel,
            clickListener: (SearchListItemDataModel, Navigator.Extras) -> Unit
        ) {
            itemRow.name.text = searchItem.name
            itemView.setOnClickListener {
                clickListener(
                    searchItem,
                    Navigator.Extras(itemView)
                )
            }
        }
    }
}
