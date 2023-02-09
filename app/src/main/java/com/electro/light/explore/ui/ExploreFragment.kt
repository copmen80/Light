package com.electro.light.explore.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.electro.light.R
import com.electro.light.databinding.FragmentExploreBinding


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    private fun navigateMenuFragment() {
        findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToMenuFragment())
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
                    Toast.makeText(context, "Send", Toast.LENGTH_LONG).show()
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true
        }
    }
}