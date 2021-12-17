package com.example.submissionakhiraplikasigithubuser.ui.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissionakhiraplikasigithubuser.viewmodel.MainViewModel
import com.example.submissionakhiraplikasigithubuser.R
import com.example.submissionakhiraplikasigithubuser.database.Favorite
import com.example.submissionakhiraplikasigithubuser.databinding.ActivityDetailUserBinding
import com.example.submissionakhiraplikasigithubuser.model.DetailResponse
import com.example.submissionakhiraplikasigithubuser.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var username: String
    private lateinit var dialog: Dialog
    private var isFavorite = false

    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(USERNAME_KEY).toString()
        mainViewModel.findDetail(username)

        dialog = Dialog(this)

        dialog.setContentView(R.layout.dialog_progress)
        dialog.setCancelable(false)

        favoriteAddUpdateViewModel = obtainViewModel(this@DetailUserActivity)

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
                    .placeholder(R.drawable.placeholder_profile)
                    .error(R.drawable.error_image)
                    .circleCrop()
                    .into(imgAvatar)

                tvName.text = name.toString()
                tvFollowers.text = followers.toString()
                tvFollowing.text = following.toString()
                tvRepository.text = publicRepos.toString()
                tvLocation.text = location.toString()
                tvCompany.text = company.toString()

                val favorite = Favorite()
                favorite.username = username
                favorite.id = id!!
                favorite.avatar = avatarUrl

                favoriteAddUpdateViewModel.getFavoriteById(favorite.id)
                    .observe(this@DetailUserActivity, { listFavorite ->
                        isFavorite = listFavorite.isNotEmpty()

                        if (listFavorite.isEmpty()) {
                            binding.starButton.setImageResource(android.R.drawable.star_big_off)
                        } else {
                            binding.starButton.setImageResource(android.R.drawable.star_big_on)

                        }

                    })


                binding.starButton.apply {
                    setOnClickListener {
                        if (isFavorite) {
                            favoriteAddUpdateViewModel.delete(favorite)
                            makeText(
                                this@DetailUserActivity,
                                R.string.favorite_dihapus,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            favoriteAddUpdateViewModel.insert(favorite)
                            makeText(
                                this@DetailUserActivity,
                                R.string.favorite_ditambah,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val shareMessage = resources.getString(R.string.message)


            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, username)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

            startActivity(Intent.createChooser(shareIntent, "Sharing"))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) dialog.show() else dialog.dismiss()
    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteAddUpdateViewModel::class.java]
    }

    companion object {
        const val USERNAME_KEY = "username_key"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2

        )
    }


}