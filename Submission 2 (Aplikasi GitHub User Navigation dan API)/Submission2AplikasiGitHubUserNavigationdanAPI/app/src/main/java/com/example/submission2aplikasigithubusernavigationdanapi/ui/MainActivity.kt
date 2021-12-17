package com.example.submission2aplikasigithubusernavigationdanapi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2aplikasigithubusernavigationdanapi.MainViewModel
import com.example.submission2aplikasigithubusernavigationdanapi.R
import com.example.submission2aplikasigithubusernavigationdanapi.databinding.ActivityMainBinding
import com.example.submission2aplikasigithubusernavigationdanapi.model.ItemsItem
import com.example.submission2aplikasigithubusernavigationdanapi.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = resources.getString(R.string.title_home)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.svSearch

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                mainViewModel.user.observe(this@MainActivity, { items ->
                    showRecyclerList(items)
                })
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        mainViewModel.isLoading.observe(this@MainActivity, {
            showLoading(it)
        })
    }

    private fun showRecyclerList(items: List<ItemsItem>) {
        binding.tvDataIsempty.visibility = if (items.isEmpty()) View.VISIBLE else View.INVISIBLE
        binding.rvUsers.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(baseContext, 2)
            } else {
                LinearLayoutManager(baseContext)
            }

            val listUserAdapter = ListUserAdapter(items)

            setHasFixedSize(true)
            adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    showSelectedUser(data)
                }
            })
        }
    }

    private fun showSelectedUser(itemsItem: ItemsItem) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.USERNAME_KEY, itemsItem.login)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}