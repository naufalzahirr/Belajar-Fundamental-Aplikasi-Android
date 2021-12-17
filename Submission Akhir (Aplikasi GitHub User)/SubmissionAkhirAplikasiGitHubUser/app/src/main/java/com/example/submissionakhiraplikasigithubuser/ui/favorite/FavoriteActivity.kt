package com.example.submissionakhiraplikasigithubuser.ui.favorite

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionakhiraplikasigithubuser.R
import com.example.submissionakhiraplikasigithubuser.database.Favorite
import com.example.submissionakhiraplikasigithubuser.databinding.ActivityFavoriteBinding
import com.example.submissionakhiraplikasigithubuser.ui.detail.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = resources.getString(R.string.favorite)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllFavorites().observe(this@FavoriteActivity, { items ->
            showRecyclerList(items)
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showRecyclerList(items: List<Favorite>) {
        binding.tvFavoriteIsempty.visibility = if (items.isEmpty()) View.VISIBLE else View.INVISIBLE
        binding.rvFavorite.apply {
            layoutManager =
                if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(baseContext, 2)
                } else {
                    LinearLayoutManager(baseContext)
                }

            val listFavoriteAdapter = ListFavoriteAdapter(items)

            setHasFixedSize(true)
            adapter = listFavoriteAdapter

            listFavoriteAdapter.setOnItemClickCallback(object :
                ListFavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Favorite) {
                    showSelectedUser(data)
                }
            })
        }
    }

    private fun showSelectedUser(itemsItem: Favorite) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.USERNAME_KEY, itemsItem.username)
        startActivity(intent)
    }

}