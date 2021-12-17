package com.example.submissionakhiraplikasigithubuser.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionakhiraplikasigithubuser.R
import com.example.submissionakhiraplikasigithubuser.database.Favorite
import com.example.submissionakhiraplikasigithubuser.databinding.ItemRowUserBinding

class ListFavoriteAdapter(private val listFavorite: List<Favorite>) :
    RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(onItemClickCallback, listFavorite[position])
    }

    override fun getItemCount() = listFavorite.size

    class ListViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onItemClickCallback: OnItemClickCallback, user: Favorite) {
            binding.apply {
                user.apply {

                    Glide.with(root)
                        .load(avatar)
                        .placeholder(R.drawable.placeholder_profile)
                        .error(R.drawable.error_image)
                        .circleCrop()
                        .into(imgAvatar)

                    tvId.text = id.toString()
                    tvUsername.text = username

                    userItem.setOnClickListener { onItemClickCallback.onItemClicked(user) }
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }


}