package com.example.retrofitexample.data.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.data.model.UserDTO
import com.example.retrofitexample.data.ui.users.fragment.UserFragment
import com.example.retrofitexample.databinding.ItemUserLayoutBinding


class UserAdapter(userFragment: UserFragment) : ListAdapter<UserDTO, UserAdapter.UserViewHolder>(
    UsersDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(private val binding: ItemUserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDTO) {
            binding.userHolder = user
          /*  binding.ivPostImage.setOnClickListener {
                listener.onPostClick(post)
            }*/
            binding.executePendingBindings()
        }
    }

    class UsersDiffUtil : DiffUtil.ItemCallback<UserDTO>() {
        override fun areItemsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
            return oldItem == newItem
        }
    }
}

/*interface OnPostClickListener {
    fun onPostClick(user: UserDTO)
}*/
