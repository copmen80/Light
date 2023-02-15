package com.electro.light.menu.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.electro.light.R
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
            Toast.makeText(context, "We don't need your donat", Toast.LENGTH_LONG).show()
        }
        binding.ivAbout.setOnClickListener {
            Toast.makeText(context, "It my Application", Toast.LENGTH_LONG).show()
        }
        binding.bTelegram.setOnClickListener {
            val telegramIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/DEVVitcher"))
            startActivity(telegramIntent)
        }
        binding.bQuestion.setOnClickListener {
            Toast.makeText(context, "We don't have answers", Toast.LENGTH_LONG).show()
        }
        binding.bEmail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "vladislavboiko.developer@gmail.com", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
    }
}