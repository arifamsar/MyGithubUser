package com.arfsar.mygithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arfsar.mygithubuser.R
import com.arfsar.mygithubuser.data.adapter.GithubUserAdapter
import com.arfsar.mygithubuser.data.response.ItemsItem
import com.arfsar.mygithubuser.databinding.ActivityMainBinding
import com.arfsar.mygithubuser.databinding.ActivitySettingBinding
import com.arfsar.mygithubuser.ui.favorite.FavoriteActivity
import com.arfsar.mygithubuser.ui.setting.SettingActivity
import com.arfsar.mygithubuser.ui.setting.SettingPreferences
import com.arfsar.mygithubuser.ui.setting.SettingViewModel
import com.arfsar.mygithubuser.ui.setting.SettingViewModelFactory
import com.arfsar.mygithubuser.ui.setting.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingBinding: ActivitySettingBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
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

        val switchTheme = settingBinding.switch1
        switchThemeSetting(switchTheme)

    }

    private fun setUsers(listUsers: ArrayList<ItemsItem>) {
        binding.rvUsers.adapter = GithubUserAdapter(listUsers)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun switchThemeSetting(switch: SwitchMaterial) {
        val switchTheme = settingBinding.switch1
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switch.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
            settingViewModel.saveThemeSetting(checked)
        }
    }
}

