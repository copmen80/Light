package com.electro.light.location.detailed.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.electro.light.databinding.FragmentDetailedBinding
import com.electro.light.location.detailed.ui.adapter.DayAdapter
import com.electro.light.location.detailed.ui.adapter.ScheduleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedFragment : Fragment() {
    private var _binding: FragmentDetailedBinding? = null
    private val binding get() = _binding!!

    private val args: DetailedFragmentArgs by navArgs()

    private val viewModel by viewModel<DetailedViewModel>()

    private val dayAdapter = DayAdapter {
        viewModel.setActiveDay(it)
        viewModel.getScheduleForSelectedDay(it)
    }

    private val scheduleAdapter = ScheduleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        handleEvents()
        viewModel.getScheduleForWeek(args.locationUiModel.group)
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        binding.ivIcon.setImageResource(args.locationUiModel.icon)
        binding.tvName.text = args.locationUiModel.name
        binding.tvGroup.text = "Група " + args.locationUiModel.group

        binding.rvDays.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSchedule.layoutManager = LinearLayoutManager(context)
        binding.rvDays.adapter = dayAdapter
        binding.rvSchedule.adapter = scheduleAdapter

        binding.bDelete.setOnClickListener {
            viewModel.deleteLocation(args.locationUiModel.name)
            navigateExploreFragment()
        }
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun handleEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is DetailedEvent.ScheduleWeek -> dayAdapter.updateList(event.list)
                    is DetailedEvent.ScheduleDay -> scheduleAdapter.updateList(event.scheduleWeek)
                }
            }
        }
    }

    private fun navigateExploreFragment() {
        findNavController().navigate(DetailedFragmentDirections.actionDetailedFragmentToExploreFragment())
    }
}
