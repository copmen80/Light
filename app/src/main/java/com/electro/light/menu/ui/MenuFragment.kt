package com.electro.light.menu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.electro.light.R
import com.electro.light.databinding.FragmentExploreBinding
import com.electro.light.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initClickListeners()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initClickListeners() {
        binding.ivDonat.setOnClickListener {
            Toast.makeText(context, "Thanks for donat", Toast.LENGTH_LONG).show()
        }
        binding.ivAbout.setOnClickListener {
            Toast.makeText(context, "It my Application", Toast.LENGTH_LONG).show()
        }
        binding.bTelegram.setOnClickListener {
            Toast.makeText(context, "Telegram", Toast.LENGTH_LONG).show()
        }
        binding.bQuestion.setOnClickListener {
            Toast.makeText(context, "Questions", Toast.LENGTH_LONG).show()
        }
        binding.bEmail.setOnClickListener {
            Toast.makeText(context, "Email", Toast.LENGTH_LONG).show()
        }
    }
}