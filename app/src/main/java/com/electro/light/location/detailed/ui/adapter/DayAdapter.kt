package com.electro.light.location.detailed.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.electro.light.R
import com.electro.light.databinding.DayItemBinding
import com.electro.light.location.detailed.ui.model.DayUiModel

class DayAdapter(
    private val callback: (DayUiModel) -> Unit
) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    private var daysList = arrayListOf<DayUiModel>()

    inner class DayViewHolder(item: View, private val callback: (DayUiModel) -> Unit) :
        RecyclerView.ViewHolder(item) {
        private val binding = DayItemBinding.bind(item)

        fun bind(dayUiModel: DayUiModel) = with(binding) {
            tvName.text = dayUiModel.day
            tvName.setTextColor(
                ContextCompat.getColor(
                    clDay.context,
                    if (dayUiModel.isCurrentDay && !dayUiModel.isSelected) R.color.orange else R.color.white
                )
            )
            clDay.background =
                ContextCompat.getDrawable(
                    clDay.context, if (dayUiModel.isSelected) {
                        R.drawable.day_item_pressed
                    } else {
                        R.drawable.day_item_unpressed
                    }
                )

            clDay.setOnClickListener {
                callback.invoke(dayUiModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        return DayViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(daysList[position])
    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    fun updateList(list: ArrayList<DayUiModel>) {
        daysList = list
        notifyDataSetChanged()
    }
}