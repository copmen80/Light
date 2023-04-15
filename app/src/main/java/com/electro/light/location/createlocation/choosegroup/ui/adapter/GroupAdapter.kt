package com.electro.light.location.createlocation.choosegroup.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.electro.light.R
import com.electro.light.location.createlocation.choosegroup.ui.model.GroupUiModel
import com.electro.light.databinding.ChooseGroupItemBinding

class GroupAdapter(
    private val groupsList: ArrayList<GroupUiModel>,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(item: View, private val callback: (Int) -> Unit) :
        RecyclerView.ViewHolder(item) {
        private val binding = ChooseGroupItemBinding.bind(item)

        fun bind(groupUiModel: GroupUiModel) = with(binding) {
            bGroup.text = groupUiModel.numberOfGroup.toString()

            bGroup.setOnClickListener {
                callback.invoke(groupUiModel.numberOfGroup)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.choose_group_item, parent, false)
        return GroupViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groupsList[position])
    }

    override fun getItemCount(): Int {
        return groupsList.size
    }
}
