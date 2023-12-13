package com.example.spotifyapp.presentation.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifyapp.databinding.SearchRecyclerRowBinding
import com.example.spotifyapp.domain.model.local.SearchItem
import com.example.spotifyapp.domain.model.search.ArtistItem
import okhttp3.internal.wait

class ArtistsAdapter : ListAdapter<SearchItem,ArtistsAdapter.ViewHolder>(ArtistsDiffCallback()) {
    inner class ViewHolder(val binding : SearchRecyclerRowBinding,val context : Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : SearchItem){
            binding.apply {
                if(!item.imgUrl.isNullOrEmpty()){
                    Glide.with(context).load(item.imgUrl).into(binding.itemImageView)
                }
                titleText.text = item.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SearchRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ArtistsDiffCallback() : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }

}