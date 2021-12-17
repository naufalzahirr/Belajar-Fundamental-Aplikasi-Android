package com.example.submissionaplikasigithubusernaufalzahirrizqullah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionaplikasigithubusernaufalzahirrizqullah.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(onItemClickCallback, listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onItemClickCallback: OnItemClickCallback, user: User) {
            binding.apply {
                user.apply {

                    Glide.with(root)
                        .load(avatar)
                        .circleCrop()
                        .into(imgAvatar)

                    tvName.text = name
                    tvLocation.text = location

                    userItem.setOnClickListener { onItemClickCallback.onItemClicked(user) }
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}