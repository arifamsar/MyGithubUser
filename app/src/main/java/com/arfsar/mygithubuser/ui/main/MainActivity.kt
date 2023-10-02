package com.arfsar.mygithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.arfsar.mygithubuser.R
import com.arfsar.mygithubuser.data.adapter.GithubUserAdapter
import com.arfsar.mygithubuser.data.response.ItemsItem
import com.arfsar.mygithubuser.databinding.ActivityMainBinding
import com.arfsar.mygithubuser.ui.favorite.FavoriteActivity
import com.arfsar.mygithubuser.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.text = searchView.text
                    mainViewModel.findUsers(searchView.text.toString())
                    searchView.hide()
                    false
                }
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_1 -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu_2 -> {
                        val intent = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }
        mainViewModel.listUsers.observe(this) { user ->
            if (user != null) {
                setUsers(user)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUsers(listUsers: ArrayList<ItemsItem>) {
        binding.rvUsers.adapter = GithubUserAdapter(listUsers)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}