package com.example.submissionaplikasigithubusernaufalzahirrizqullah

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionaplikasigithubusernaufalzahirrizqullah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listUser: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = resources.getString(R.string.title_home)

        initUserDataList()
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvUsers.layoutManager = GridLayoutManager(baseContext, 2)
            } else {
                rvUsers.layoutManager = LinearLayoutManager(baseContext)
            }

            val listUserAdapter = ListUserAdapter(listUser)

            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }
            })
        }
    }

    private fun initUserDataList() {
        listUser = ArrayList()

        resources.apply {
            val dataName = getStringArray(R.array.name)
            val dataUsername = getStringArray(R.array.username)
            val dataLocation = getStringArray(R.array.location)
            val dataAvatar = obtainTypedArray(R.array.avatar)
            val dataCompany = getStringArray(R.array.company)
            val dataFollowers = getStringArray(R.array.followers)
            val dataFollowing = getStringArray(R.array.following)
            val dataRepository = getStringArray(R.array.repository)

            for (i in dataName.indices) {
                listUser.add(
                    User(
                        name = dataName[i],
                        location = dataLocation[i],
                        avatar = dataAvatar.getResourceId(i, -1),
                        username = dataUsername[i],
                        repository = dataRepository[i],
                        company = dataCompany[i],
                        following = dataFollowing[i],
                        followers = dataFollowers[i]
                    )
                )
            }

            dataAvatar.recycle()
        }
    }

    private fun showSelectedUser(user: User) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.DETAIL_USER, user)
        startActivity(intent)
    }
}