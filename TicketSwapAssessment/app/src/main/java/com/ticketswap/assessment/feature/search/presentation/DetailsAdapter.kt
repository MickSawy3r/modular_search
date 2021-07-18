package com.ticketswap.assessment.feature.search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ticketswap.assessment.core.navigation.Navigator
import com.ticketswap.assessment.databinding.ItemDetailsBinding
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.extention.loadFromUrl
import javax.inject.Inject
import kotlin.properties.Delegates

class DetailsAdapter@Inject constructor() : RecyclerView.Adapter<DetailsAdapter.DetailsVH>() {

    internal var collection: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (SpotifyDataModel, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailsVH {
        val layoutInflater = LayoutInflater.from(p0.context)
        val venueItemBinding = ItemDetailsBinding.inflate(layoutInflater, p0, false)
        return DetailsVH(venueItemBinding)
    }

    override fun onBindViewHolder(viewHolder: DetailsVH, position: Int) {
        viewHolder.bind(collection[position])
    }

    override fun getItemCount(): Int = collection.size

    class DetailsVH(private val itemRow: ItemDetailsBinding) :
        RecyclerView.ViewHolder(itemRow.root) {
        fun bind(
            imageUrl: String
        ) {
            itemRow.ivImage.loadFromUrl(imageUrl)
        }
    }
}
