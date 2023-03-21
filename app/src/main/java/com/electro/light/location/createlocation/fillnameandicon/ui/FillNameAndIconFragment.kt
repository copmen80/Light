package com.electro.light.location.createlocation.fillnameandicon.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.electro.light.CreateLocationFlowDirections
import com.electro.light.R
import com.electro.light.location.createlocation.fillnameandicon.ui.adapter.IconAdapter
import com.electro.light.databinding.FragmentFillNameAndIconBinding
import com.electro.light.location.common.data.Location
import org.koin.androidx.viewmodel.ext.android.viewModel

class FillNameAndIconFragment : Fragment() {
    private val group: String by lazy { requireNotNull(arguments?.getString(GROUP_KEY)) }
    private var icon: Int = R.drawable.ic_location

    private var _binding: FragmentFillNameAndIconBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FillNameAndIconViewModel>()

    private val adapter by lazy {
        IconAdapter(
            viewModel.getIcons()
        ) {
            icon = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFillNameAndIconBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarTitle.text = "${resources.getText(R.string.group)} $group"
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initUi() {
        binding.rvIcons.layoutManager = GridLayoutManager(context, 5)
        binding.rvIcons.adapter = adapter
        binding.bSave.setOnClickListener {
            viewModel.addLocation(
                Location(
                    name = binding.etNameLocation.text.toString(),
                    icon = icon,
                    group = group
                )
            )
            navigateExploreFragment()
        }
    }

    private fun navigateExploreFragment() {
        findNavController().navigate(CreateLocationFlowDirections.actionGlobalExploreFragment())
    }

    companion object {
        private const val GROUP_KEY = "group"
    }
}
