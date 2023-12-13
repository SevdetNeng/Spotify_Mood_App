package com.example.spotifyapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifyapp.databinding.RecommendedRowBinding
import com.example.spotifyapp.domain.model.recommend.Track

class RecommendedAdapter : androidx.recyclerview.widget.ListAdapter<Track,RecommendedAdapter.ViewHolder>(Diffcallback()) {
    inner class ViewHolder(val binding : RecommendedRowBinding,val context : Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Track){
            binding.trackArtistText.text = item.artists[0].name
            binding.trackNameText.text = item.name
            Glide.with(context).load(item.album.images[0].url).centerCrop().into(binding.trackImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecommendedRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class Diffcallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }

}