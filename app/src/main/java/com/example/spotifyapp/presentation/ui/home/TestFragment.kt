package com.example.spotifyapp.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.spotifyapp.R
import com.example.spotifyapp.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : Fragment() {

    private var _binding : FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.buttonTest.setOnClickListener {
            viewModel.authorizeUser(requireActivity())
        }
        return binding.root
    }
}