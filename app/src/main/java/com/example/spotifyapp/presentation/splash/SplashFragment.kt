package com.example.spotifyapp.presentation.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.spotifyapp.R
import com.example.spotifyapp.databinding.FragmentSplashBinding
import com.example.spotifyapp.domain.model.top.TopItems
import com.example.spotifyapp.presentation.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedViewModel.authorizeUser(requireActivity())
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedViewModel.isLoggedIn.collect(){
                    if(it){
                        val token = requireActivity().getSharedPreferences("Token",Context.MODE_PRIVATE).getString("token","")
                        findNavController().navigate(R.id.homeFragment)
                        if (token != null) {
                            println("token  is "+token)
                            //sharedViewModel.getTopTracks()
                            //sharedViewModel.getTopArtists()
                        }
                    }
                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedViewModel.isDataParsed.collect(){
                    if(it){
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        val cardView = binding.logoCard
        val anim = AnimationUtils.loadAnimation(requireContext(),R.anim.custom_splash_anim)
        cardView.startAnimation(anim)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}