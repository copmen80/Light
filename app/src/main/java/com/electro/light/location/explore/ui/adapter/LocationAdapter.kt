package com.electro.light.location.explore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.electro.light.R
import com.electro.light.databinding.LocationItemBinding
import com.electro.light.location.explore.ui.model.LocationUiModel
import com.electro.light.utils.DefaultDiffCallback

sealed class LocationEvent
data class OpenDetailed(val location: LocationUiModel) : LocationEvent()
data class DeleteLocation(val name: String) : LocationEvent()

class LocationAdapter(
    private val callback: (LocationEvent) -> Unit
) : ListAdapter<LocationUiModel, LocationAdapter.LocationViewHolder>(
    DefaultDiffCallback { oldItem, newItem -> oldItem == newItem }
) {

    // TODO read about inner class
    class LocationViewHolder(item: View, private val callback: (LocationEvent) -> Unit) :
        ViewHolder(item) {
        private val binding = LocationItemBinding.bind(item)

        fun bind(locationUiModel: LocationUiModel) = with(binding) {
            ivIcon.setImageResource(locationUiModel.icon)
            tvName.text = locationUiModel.name
            tvRemainingTime.text = locationUiModel.remainingTime

            cvLocation.setOnClickListener {
                callback(OpenDetailed(locationUiModel))
            }

            cvLocation.setOnLongClickListener {
                callback(DeleteLocation(locationUiModel.name))
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
