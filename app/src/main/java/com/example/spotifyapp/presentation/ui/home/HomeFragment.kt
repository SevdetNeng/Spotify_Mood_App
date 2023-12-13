package com.example.spotifyapp.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.provider.MediaStore.Audio.Artists
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.spotifyapp.R
import com.example.spotifyapp.base.BaseFragment
import com.example.spotifyapp.databinding.FragmentHomeBinding
import com.example.spotifyapp.presentation.SharedViewModel
import com.example.spotifyapp.presentation.adapter.RecommendedAdapter
import com.example.spotifyapp.presentation.ui.home.adapter.ArtistsAdapter
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel : HomeViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private val artistAdapter : ArtistsAdapter = ArtistsAdapter()
    private val tracksAdapter : ArtistsAdapter = ArtistsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.searchArtists.collect(){
                    artistAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.searchTracks.collect(){
                    tracksAdapter.submitList(it)
                }
            }
        }

        binding.apply {
            danceableSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    danceableCount.text = progress.toString()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            happySeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    happyCount.text = progress.toString()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            energeticSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    energeticCount.text = progress.toString()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            searchBar.addTextChangedListener {
                it?.let { text ->
                    if(text.length % 2 == 0){
                        viewModel.searchArtists(sharedViewModel.token.value,text.toString())
                        viewModel.searchTracks(sharedViewModel.token.value,text.toString())
                    }
                }
                if(it!!.isEmpty()){
                    artistAdapter.submitList(emptyList())
                    tracksAdapter.submitList(emptyList())
                }
            }

            buttonGetRec.setOnClickListener{

            }
        }
        setupArtistRecycler()
        setupTracksRecycler()
        super.onViewCreated(view, savedInstanceState)
    }

    fun setupArtistRecycler(){
        val artistRecycler = binding.artistsRecycler
        artistRecycler.layoutManager = LinearLayoutManager(requireContext(),VERTICAL,false)
        artistRecycler.adapter = artistAdapter

    }

    fun setupTracksRecycler(){
        val tracksRecycler = binding.tracksRecycler
        tracksRecycler.layoutManager = LinearLayoutManager(requireContext(), VERTICAL,false)
        tracksRecycler.adapter = tracksAdapter
    }

}