package com.example.submissionakhiraplikasigithubuser.ui.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionakhiraplikasigithubuser.R
import com.example.submissionakhiraplikasigithubuser.databinding.ItemRowUserBinding
import com.example.submissionakhiraplikasigithubuser.model.FollowingResponseItem

class FollowingAdapter(private val listFollowing: List<FollowingResponseItem>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listFollowing[position])
    }

    override fun getItemCount() = listFollowing.size

    class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowingResponseItem) {

            binding.apply {
                user.apply {

                    Glide.with(root)
                        .load(avatarUrl)
                        .placeholder(R.drawable.placeholder_profile)
                        .error(R.drawable.error_image)
                        .circleCrop()
                        .into(imgAvatar)


                    tvId.text = id.toString()
                    tvUsername.text = login

                }
            }
        }
    }
}