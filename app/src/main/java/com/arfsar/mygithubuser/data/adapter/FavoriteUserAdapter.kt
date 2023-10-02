package com.arfsar.mygithubuser.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arfsar.mygithubuser.data.local.entity.UserFavoriteEntity
import com.arfsar.mygithubuser.databinding.ItemRowBinding
import com.arfsar.mygithubuser.ui.detail.DetailActivity
import com.arfsar.mygithubuser.utils.UserFavDiffCallback
import com.bumptech.glide.Glide

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteViewHolder>() {

    private val listFavUser = ArrayList<UserFavoriteEntity>()
    fun setListUser(listFavUser: List<UserFavoriteEntity>) {
        val diffCallback = UserFavDiffCallback(this.listFavUser, listFavUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listFavUser)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }

    override fun getItemCount(): Int = listFavUser.size

    inner class FavoriteViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(user: UserFavoriteEntity) {
                with(binding) {
                    Glide
                        .with(binding.root.context)
                        .load(user.avatarUrl)
                        .into(profileImage)
                    tvUsername.text = user.username
                    card.setOnClickListener {
                        val intent = Intent(binding.root.context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_USER, user.username)
                        intent.putExtra(DetailActivity.EXTRA_AVATAR, user.avatarUrl)
                        it.context.startActivity(intent)
                    }
                }
            }

    }
}