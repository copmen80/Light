package com.electro.light.location.explore.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.electro.light.R
import com.electro.light.databinding.FragmentExploreBinding
import com.electro.light.location.explore.ui.adapter.DeleteLocation
import com.electro.light.location.explore.ui.adapter.LocationAdapter
import com.electro.light.location.explore.ui.adapter.OpenDetailed
import com.electro.light.location.explore.ui.model.LocationUiModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    // TODO required not null

    private val viewModel by viewModel<ExploreViewModel>()

    private val locationsAdapter = LocationAdapter { locationEvent ->
        when (locationEvent) {
            is DeleteLocation -> viewModel.deleteLocation(locationEvent.name)
            is OpenDetailed -> navigateDetailedFragment(locationEvent.location)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initUi()
        handleViewModel()
    }

    private fun handleViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.content.collectLatest { list ->
                stateListUi(list)
                locationsAdapter.submitList(list)
            }
        }
    }

    private fun navigateMenuFragment() {
        findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToMenuFragment())
    }

    private fun navigateCreateLocationFlow() {
        findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToCreateLocationFlow())
    }

    private fun navigateDetailedFragment(locationUiModel: LocationUiModel) {
        findNavController().navigate(
            ExploreFragmentDirections.actionExploreFragmentToDetailedFragment(
                locationUiModel
            )
        )
    }

    private fun initToolbar() {
        binding.toolbar.inflateMenu(R.menu.explore_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.iMenu -> {
                    navigateMenuFragment()
                    return@setOnMenuItemClickListener true
                }
                R.id.iSend -> {
                    Toast.makeText(context, "Something send", Toast.LENGTH_LONG).show()
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun initUi() {
        binding.rvLocations.layoutManager = LinearLayoutManager(context)
        binding.rvLocations.adapter = locationsAdapter

        binding.cbNewLocation.setOnClickListener {
            navigateCreateLocationFlow()
        }
        binding.fabNewLocation.setOnClickListener {
            navigateCreateLocationFlow()
        }
    }

    // TODO add state
    private fun stateListUi(list: List<LocationUiModel>) {
        if (list.isEmpty()) {
            binding.llEmptyList.visibility = View.VISIBLE
            binding.rvLocations.visibility = View.INVISIBLE
            binding.fabNewLocation.visibility = View.INVISIBLE
        } else {
            binding.llEmptyList.visibility = View.INVISIBLE
            binding.rvLocations.visibility = View.VISIBLE
            binding.fabNewLocation.visibility = View.VISIBLE
        }
    }
}
