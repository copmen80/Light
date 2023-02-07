package com.electro.light.explore.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.iSend -> {
            Toast.makeText(context, "Send", Toast.LENGTH_LONG).show()
            true
        }

        R.id.iMenu -> {
            navigateFillCodeFragment()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun navigateFillCodeFragment() {
        findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToMenuFragment())
    }
}