package com.example.retrofitexample.data.ui.postDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.data.model.PostDTO
import com.example.retrofitexample.data.ui.postDetail.fragment.PostDetailFragment
import com.example.retrofitexample.databinding.ItemDetailLayoutBinding

class PostDetailAdapter(postDetailFragment: PostDetailFragment) : ListAdapter<PostDTO, PostDetailAdapter.PostDetailViewHolder>(
    PostDetailDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder {
        return PostDetailViewHolder(
            ItemDetailLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostDetailViewHolder(private val binding: ItemDetailLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostDTO) {
            binding.dataHolder = post


            binding.executePendingBindings()
        }
    }
    class PostDetailDiffUtil : DiffUtil.ItemCallback<PostDTO>() {
        override fun areItemsTheSame(oldItem: PostDTO, newItem: PostDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostDTO, newItem: PostDTO): Boolean {
            return oldItem == newItem
        }
    }

}
