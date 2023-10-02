package com.arfsar.mygithubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arfsar.mygithubuser.data.adapter.GithubUserAdapter
import com.arfsar.mygithubuser.data.response.ItemsItem
import com.arfsar.mygithubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val followViewModel by viewModels<DetailViewModel>()

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()
        val position = arguments?.getInt(ARG_POSITION)

        if (position == 1) {
            getFollowers(username)
        } else {
            getFollowing(username)
        }

    }

    private fun getFollowers(username: String) {
        followViewModel.getFollowers(username)
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followViewModel.followers.observe(viewLifecycleOwner) {
            if (it != null) {
                getUser(it as ArrayList<ItemsItem>)
            }
        }
    }

    private fun getFollowing(username: String) {
        followViewModel.getFollowing(username)
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followViewModel.following.observe(viewLifecycleOwner) {
            if (it != null) {
                getUser(it as ArrayList<ItemsItem>)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getUser(users: ArrayList<ItemsItem>) {
        binding?.rvFollow?.adapter = GithubUserAdapter(users)
        binding?.rvFollow?.layoutManager = LinearLayoutManager(this.context)
    }


}