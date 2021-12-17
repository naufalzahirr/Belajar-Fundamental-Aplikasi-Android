package com.example.submission2aplikasigithubusernavigationdanapi.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission2aplikasigithubusernavigationdanapi.MainViewModel
import com.example.submission2aplikasigithubusernavigationdanapi.R
import com.example.submission2aplikasigithubusernavigationdanapi.databinding.ActivityDetailUserBinding
import com.example.submission2aplikasigithubusernavigationdanapi.model.DetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var username : String

    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        const val USERNAME_KEY = "username_key"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2

        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(USERNAME_KEY).toString()
        mainViewModel.findDetail(username)

        mainViewModel.detail.observe(this@DetailUserActivity, {
            showData(it)
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        mainViewModel.isLoading.observe(this@DetailUserActivity, {
            showLoading(it)
        })

    }

    fun getMyData(): String {
        return username
    }

    private fun showData(item: DetailResponse) {
        binding.apply {
            item.apply {
                title = username

                Glide.with(root)
                    .load(avatarUrl)
                    .circleCrop()
                    .into(imgAvatar)

                tvName.text = name.toString()
                tvFollowers.text = followers.toString()
                tvFollowing.text = following.toString()
                tvRepository.text = publicRepos.toString()
                tvLocation.text = location.toString()
                tvCompany.text = company.toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_user_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val shareMessage = R.string.message

            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, username)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

            startActivity(Intent.createChooser(shareIntent, "Sharing"))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}