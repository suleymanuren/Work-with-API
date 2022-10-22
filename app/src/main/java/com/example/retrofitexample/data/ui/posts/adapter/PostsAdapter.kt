package com.example.retrofitexample.data.ui.posts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.data.model.PostDTO
import com.example.retrofitexample.databinding.ItemPostLayoutBinding


class PostsAdapter(private val listener: OnPostClickListener) : ListAdapter<PostDTO, PostsAdapter.PostViewHolder>(
    PostsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       return PostViewHolder(
            ItemPostLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PostViewHolder(private val binding: ItemPostLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostDTO, listener: OnPostClickListener) {
            binding.dataHolder = post
            binding.PostCardView.setOnClickListener {
                listener.onPostDetailClick(post, it)
            }
            binding.ivPostImage.setOnClickListener {
                listener.onPostFavoriteClick(post)
            }
            binding.executePendingBindings()
        }
    }

    class PostsDiffUtil : DiffUtil.ItemCallback<PostDTO>() {
        override fun areItemsTheSame(oldItem: PostDTO, newItem: PostDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostDTO, newItem: PostDTO): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnPostClickListener {

    fun onPostDetailClick(post: PostDTO,view: View)
    fun onPostFavoriteClick(post: PostDTO)
}
