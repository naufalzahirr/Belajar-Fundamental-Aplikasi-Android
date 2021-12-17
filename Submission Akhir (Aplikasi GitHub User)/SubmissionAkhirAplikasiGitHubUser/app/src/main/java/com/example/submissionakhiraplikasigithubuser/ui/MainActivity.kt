package com.example.submissionakhiraplikasigithubuser.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionakhiraplikasigithubuser.*
import com.example.submissionakhiraplikasigithubuser.databinding.ActivityMainBinding
import com.example.submissionakhiraplikasigithubuser.helper.SettingPreferences
import com.example.submissionakhiraplikasigithubuser.model.ItemsItem
import com.example.submissionakhiraplikasigithubuser.ui.favorite.FavoriteActivity
import com.example.submissionakhiraplikasigithubuser.ui.detail.DetailUserActivity
import com.example.submissionakhiraplikasigithubuser.viewmodel.MainViewModel
import com.example.submissionakhiraplikasigithubuser.viewmodel.ThemeViewModel
import com.example.submissionakhiraplikasigithubuser.viewmodel.ThemeViewModelFactory

class MainActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var themeDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = resources.getString(R.string.title_home)

        mainViewModel.findUser("a")

        mainViewModel.user.observe(this@MainActivity, { items ->
            showRecyclerList(items)
        })

        mainViewModel.isLoading.observe(this@MainActivity, {
            showLoading(it)
        })
        changeThemes()

    }

    private fun showRecyclerList(items: List<ItemsItem>) {
        binding.tvDataIsempty.visibility = if (items.isEmpty()) View.VISIBLE else View.INVISIBLE
        binding.rvUsers.apply {
            layoutManager =
                if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                mainViewModel.findUser(query)

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.favorite) {
            val intent = Intent(
                this, FavoriteActivity::class.java
            )
            startActivity(intent)
        } else if (id == R.id.theme) {
            showThemes()

        }
        return true
    }

    private fun showThemes() {
        changeThemes()
        themeDialog.show()
    }


    private fun changeThemes() {
        themeDialog = Dialog(this)
        themeDialog.setContentView(R.layout.dialog_theme)
        val switchTheme = themeDialog.findViewById<SwitchCompat>(R.id.switch_Theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            })

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

}