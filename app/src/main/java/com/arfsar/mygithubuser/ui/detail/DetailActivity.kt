package com.arfsar.mygithubuser.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.arfsar.mygithubuser.R
import com.arfsar.mygithubuser.data.adapter.SectionsPagerAdapter
import com.arfsar.mygithubuser.data.local.entity.UserFavoriteEntity
import com.arfsar.mygithubuser.data.response.GithubDetailResponse
import com.arfsar.mygithubuser.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val detailUser = intent.getStringExtra(EXTRA_USER).toString()
        detailViewModel.getUser(detailUser)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR).toString()

        detailViewModel.user.observe(this) {user ->
            user?.let {
                setDetailUser(it)
            }
            detailViewModel.isLoading.observe(this) { isLoading ->
                showLoading(isLoading)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(
            activity = this@DetailActivity,
            username = detailUser
        )

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        fabIconUpdate(binding.fabFavorite, isFavorite)

        detailViewModel.isFavorite(detailUser).observe(this) { favorite ->
            isFavorite = favorite
            fabIconUpdate(binding.fabFavorite, isFavorite)
        }

        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                addToFavorite(UserFavoriteEntity(detailUser, avatarUrl))
            } else {
                removeFromFavorites(UserFavoriteEntity(detailUser))
            }
        }
    }

    private fun setDetailUser(detailUser: GithubDetailResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(detailUser.avatarUrl)
                .into(profileImage)
            tvFollowers.text = StringBuilder(detailUser.followers.toString()).append(" Followers")
            tvFollowing.text = StringBuilder(detailUser.followers.toString()).append(" Following")
            tvName.text = detailUser.name
            tvUsername.text = detailUser.login
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun fabIconUpdate(fab: FloatingActionButton, isFavorite: Boolean) {
        val iconResource = if (isFavorite) R.drawable.favorite_filled else R.drawable.favorite_outlined
        fab.setImageResource(iconResource)
    }

    private fun addToFavorite(username: UserFavoriteEntity) {
        val userFavorite = UserFavoriteEntity(
            username.username,
            username.avatarUrl
        )
        detailViewModel.insert(userFavorite)
        Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
        fabIconUpdate(binding.fabFavorite, true)
    }

    private fun removeFromFavorites(username: UserFavoriteEntity) {
        detailViewModel.delete(username)
        Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show()
        isFavorite = false
        fabIconUpdate(binding.fabFavorite, false)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "avatar_url"
    }

}