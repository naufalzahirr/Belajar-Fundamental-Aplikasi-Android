package com.example.submission2aplikasigithubusernavigationdanapi.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2aplikasigithubusernavigationdanapi.R
import com.example.submission2aplikasigithubusernavigationdanapi.databinding.ItemRowUserBinding
import com.example.submission2aplikasigithubusernavigationdanapi.model.ItemsItem

class ListUserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

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

    override fun getItemCount() = listUser.size

    class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onItemClickCallback: OnItemClickCallback, user: ItemsItem) {
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

                    userItem.setOnClickListener { onItemClickCallback.onItemClicked(user) }
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }


}