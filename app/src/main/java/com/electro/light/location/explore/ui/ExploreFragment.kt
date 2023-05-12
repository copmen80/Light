package com.electro.light.location.explore.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    // TODO required not null

    private var permissionAccess = false

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("Permission", "Granted")
            } else {
                Log.d("Permission", "Denied")
            }
        }

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
        requestPermissionNotification()
        handleViewModel()
    }

    private fun requestPermissionNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is granted
                    permissionAccess = true
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
                else -> {
                    Snackbar.make(
                        binding.ccLayout,
                        "allow permission please", Snackbar.LENGTH_SHORT
                    )
                        .show()

                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
        }
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
