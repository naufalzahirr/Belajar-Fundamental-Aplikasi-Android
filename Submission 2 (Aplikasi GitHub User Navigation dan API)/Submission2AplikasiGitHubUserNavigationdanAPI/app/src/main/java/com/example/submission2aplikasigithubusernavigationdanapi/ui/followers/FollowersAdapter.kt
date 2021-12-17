package com.example.submission2aplikasigithubusernavigationdanapi.ui.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2aplikasigithubusernavigationdanapi.databinding.ItemRowUserBinding
import com.example.submission2aplikasigithubusernavigationdanapi.model.FollowersResponseItem

class FollowersAdapter(private val listFollowers : List<FollowersResponseItem>) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listFollowers[position])    }

    override fun getItemCount() = listFollowers.size

    class ViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowersResponseItem) {

            binding.apply {
                user.apply {

                    Glide.with(root)
                        .load(avatarUrl)
                        .circleCrop()
                        .into(imgAvatar)

                    tvId.text = id.toString()
                    tvUsername.text = login

                }
            }
        }
    }
}