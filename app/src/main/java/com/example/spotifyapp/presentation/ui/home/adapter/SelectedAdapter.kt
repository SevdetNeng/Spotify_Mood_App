package com.example.spotifyapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifyapp.databinding.SelectedSearchItemBinding
import com.example.spotifyapp.domain.model.local.SearchItem

class SelectedAdapter(private val onClick : (SearchItem) -> Unit) : ListAdapter<SearchItem,SelectedAdapter.ViewHolder>(SelectedDiffCallBack()) {
    inner class ViewHolder(val binding : SelectedSearchItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : SearchItem){
            binding.itemText.text = item.title
            binding.imageView2.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectedSearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SelectedDiffCallBack() : DiffUtil.ItemCallback<SearchItem>(){
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }

}