package com.example.spotifyapp.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.spotifyapp.base.BaseFragment
import com.example.spotifyapp.databinding.FragmentHomeBinding
import com.example.spotifyapp.presentation.SharedViewModel
import com.example.spotifyapp.presentation.ui.home.adapter.SearchAdapter
import com.example.spotifyapp.presentation.ui.home.adapter.SelectedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel : HomeViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private val artistAdapter : SearchAdapter = SearchAdapter(){
        viewModel.selectItem(it)
    }
    private val tracksAdapter : SearchAdapter = SearchAdapter(){
        viewModel.selectItem(it)
    }

    private val selectedAdapter : SelectedAdapter = SelectedAdapter(){
        viewModel.unselectItem(it)
    }

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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.selectedItems.collect(){
                    selectedAdapter.submitList(it)
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
                viewModel.getRecommendedItems(sharedViewModel.token.value,happyCount.text.toString().toFloat(),
                    danceableCount.text.toString().toFloat(),
                    energeticCount.text.toString().toFloat())
            }
        }
        setupArtistRecycler()
        setupTracksRecycler()
        setupSelectedAdapter()
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

    fun setupSelectedAdapter(){
        val selectedRecycler = binding.selectedItemRecycler
        selectedRecycler.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL,false)
        selectedRecycler.adapter = selectedAdapter
    }

}