package com.cesarschool.android_shared.ui.photo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cesarschool.android_shared.databinding.ItemPhotoBinding
import com.cesarschool.android_shared.model.Photo

class PhotoAdapter(
    private val onItemClick: (Photo) -> Unit,
) : ListAdapter<Photo, PhotoAdapter.MyViewHolder>(MyDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }

    inner class MyViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.apply {
                tvDescription.text = photo.name
                imgViewThumb.setImageURI(photo.uri)
                itemView.setOnClickListener {
                    onItemClick(photo)
                }
            }
        }
    }

    class MyDiff : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem.name == newItem.name
    }
}