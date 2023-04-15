package com.electro.light.location.detailed.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.electro.light.R
import com.electro.light.databinding.ScheduleItemBinding
import com.electro.light.location.detailed.ui.model.DayUiModel
import com.electro.light.location.detailed.ui.model.ScheduleUiModel

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    private var scheduleList = arrayListOf<ScheduleUiModel>()

    inner class ScheduleViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ScheduleItemBinding.bind(item)

        fun bind(scheduleUiModel: ScheduleUiModel) = with(binding) {
            scheduleUiModel.icon?.let {
                ivIcon.setImageResource(it)
            }
            ivIcon.visibility = if (scheduleUiModel.icon == null) View.INVISIBLE else View.VISIBLE
            tvPeriodOfTime.text = scheduleUiModel.periodOfTime
            tvRemainingTime.text = scheduleUiModel.remainingTime
        }
    }

    fun updateList(day: DayUiModel) {
        scheduleList = day.daySchedule as ArrayList<ScheduleUiModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(scheduleList[position])
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }
}
