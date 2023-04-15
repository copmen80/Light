package com.electro.light.location.createlocation.fillnameandicon.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.electro.light.R
import com.electro.light.location.createlocation.fillnameandicon.ui.model.IconUiModel
import com.electro.light.databinding.IconItemBinding

class IconAdapter(
    private val iconsList: ArrayList<IconUiModel>,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<IconAdapter.IconViewHolder>() {

    inner class IconViewHolder(
        item: View, private val callback: (Int) -> Unit
    ) : RecyclerView.ViewHolder(item) {
        private val binding = IconItemBinding.bind(item)

        fun bind(iconUiModel: IconUiModel) = with(binding) {
            ivIcon.setImageResource(iconUiModel.image)
            clIcon.background =
                ContextCompat.getDrawable(
                    clIcon.context, if (iconUiModel.isSelected) {
                        R.drawable.icon_item_pressed
                    } else {
                        R.drawable.icon_item_unpressed
                    }
                )

            clIcon.setOnClickListener {
                callback.invoke(iconUiModel.image)
                setListIcon(iconUiModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.icon_item, parent, false)
        return IconViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.bind(iconsList[position])
    }

    override fun getItemCount(): Int {
        return iconsList.size
    }

    private fun setListIcon(iconUiModel: IconUiModel) {
        iconsList.forEach {
            it.isSelected = false
        }

        iconsList.forEach {
            if (it.image == iconUiModel.image) {
                it.isSelected = true
            }
        }
        notifyDataSetChanged()
    }
}