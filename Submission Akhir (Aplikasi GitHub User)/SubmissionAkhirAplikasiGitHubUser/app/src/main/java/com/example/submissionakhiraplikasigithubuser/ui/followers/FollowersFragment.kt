package com.example.submissionakhiraplikasigithubuser.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionakhiraplikasigithubuser.viewmodel.MainViewModel
import com.example.submissionakhiraplikasigithubuser.databinding.FragmentFollowersBinding
import com.example.submissionakhiraplikasigithubuser.model.FollowersResponseItem
import com.example.submissionakhiraplikasigithubuser.ui.detail.DetailUserActivity


class FollowersFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding as FragmentFollowersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailUserActivity = activity as DetailUserActivity
        val username = activity.getMyData()


        mainViewModel.findFollowers(username)
        mainViewModel.followers.observe(viewLifecycleOwner, { followersResponse ->

            showData(followersResponse)
        })

        mainViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showData(followersResponse: List<FollowersResponseItem>) {
        binding.tvFollowersIsempty.visibility =
            if (followersResponse.isEmpty()) View.VISIBLE else View.INVISIBLE

        binding.rvFollowers.apply {

            val followersAdapter = FollowersAdapter(followersResponse)

            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = followersAdapter

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}