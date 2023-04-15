package com.electro.light.location.createlocation.choosegroup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.electro.light.R
import com.electro.light.location.createlocation.choosegroup.ui.adapter.GroupAdapter
import com.electro.light.databinding.FragmentChooseGroupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupsFragment : Fragment() {
    private var _binding: FragmentChooseGroupBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<GroupsViewModel>()

    private val groupAdapter by lazy {
        GroupAdapter(
            viewModel.getGroups()
        ) {
            navigateFillNameAndIcon(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecycler()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher
        }
    }

    private fun initRecycler() {
        binding.rvGroups.layoutManager = LinearLayoutManager(context)
        binding.rvGroups.adapter = groupAdapter
    }

    private fun navigateFillNameAndIcon(group: Int) {
        val action =
            GroupsFragmentDirections.actionChooseGroupFragmentToFillNameAndIconFragment(
                group
            )
        findNavController().navigate(action)
    }
}
