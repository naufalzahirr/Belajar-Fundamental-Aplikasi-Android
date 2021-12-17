package com.example.submissionaplikasigithubusernaufalzahirrizqullah

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submissionaplikasigithubusernaufalzahirrizqullah.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra<User>(DETAIL_USER) as User

        showData()
    }

    private fun showData() {
        binding.apply {
            user.apply {
                title = username

                Glide.with(root)
                    .load(avatar)
                    .circleCrop()
                    .into(imgAvatar)

                tvName.text = name
                tvFollowers.text = followers
                tvFollowing.text = following
                tvRepository.text = repository
                tvLocation.text = location
                tvCompany.text = company
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_user_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val shareMessage = "Silahkan klik https://github.com/${user.username}"

            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, user.name)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

            startActivity(Intent.createChooser(shareIntent, "Sharing"))
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DETAIL_USER = "detail_user"
    }
}