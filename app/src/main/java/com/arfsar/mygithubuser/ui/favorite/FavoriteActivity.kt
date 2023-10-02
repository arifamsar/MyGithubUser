package com.arfsar.mygithubuser.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arfsar.mygithubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}