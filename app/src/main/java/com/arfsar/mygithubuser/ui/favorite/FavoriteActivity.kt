package com.arfsar.mygithubuser.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arfsar.mygithubuser.data.adapter.FavoriteUserAdapter
import com.arfsar.mygithubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val adapter = FavoriteUserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(application)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        viewModel.getAllFavoriteUser().observe(this) { favUser ->
            adapter.setListUser(favUser)
        }


    }

}