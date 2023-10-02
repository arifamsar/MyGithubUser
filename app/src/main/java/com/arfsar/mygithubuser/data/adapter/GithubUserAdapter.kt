package com.arfsar.mygithubuser.data.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arfsar.mygithubuser.R
import com.arfsar.mygithubuser.data.response.GithubDetailResponse
import com.arfsar.mygithubuser.data.response.ItemsItem
import com.arfsar.mygithubuser.data.retrofit.ApiConfig
import com.arfsar.mygithubuser.databinding.ItemRowBinding
import com.arfsar.mygithubuser.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserAdapter(private val listUsers: ArrayList<ItemsItem>):
    RecyclerView.Adapter<GithubUserAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemRowBinding.bind(itemView)

        val imgUser: ImageView = binding.profileImage
        val tvUsername: TextView = binding.tvUsername
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listUsers[position]
        Glide
            .with(holder.itemView.context)
            .load(item.avatarUrl)
            .into(holder.imgUser)
        holder.tvUsername.text = item.login
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USER, listUsers[position].login)
            intentDetail.putExtra(DetailActivity.EXTRA_AVATAR, listUsers[position].avatarUrl)
            holder.itemView.context.startActivity(intentDetail)
        }
        val client = ApiConfig.getApiService().getDetailUser(item.login)
        client.enqueue(object : Callback<GithubDetailResponse>{
            override fun onResponse(
                call: Call<GithubDetailResponse>,
                response: Response<GithubDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        holder.tvUsername.text = responseBody.login
                    }
                }
            }

            override fun onFailure(call: Call<GithubDetailResponse>, t: Throwable) {
                Toast.makeText(holder.itemView.context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                Log.e("GithubUserAdapter", "onFailure ${t.message}")
            }

        })
    }
}
