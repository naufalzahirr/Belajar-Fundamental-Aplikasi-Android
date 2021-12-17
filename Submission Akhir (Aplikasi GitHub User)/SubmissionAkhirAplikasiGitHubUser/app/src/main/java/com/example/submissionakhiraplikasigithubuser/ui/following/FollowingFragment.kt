package com.example.submissionakhiraplikasigithubuser.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionakhiraplikasigithubuser.viewmodel.MainViewModel
import com.example.submissionakhiraplikasigithubuser.databinding.FragmentFollowingBinding
import com.example.submissionakhiraplikasigithubuser.model.FollowingResponseItem
import com.example.submissionakhiraplikasigithubuser.ui.detail.DetailUserActivity

class FollowingFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailUserActivity = activity as DetailUserActivity
        val username = activity.getMyData()

        mainViewModel.findFollowing(username)
        mainViewModel.following.observe(viewLifecycleOwner, { followingResponse ->
            showData(followingResponse)
        })

        mainViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showData(followingResponse: List<FollowingResponseItem>) {
        binding.tvFollowingIsempty.visibility =
            if (followingResponse.isEmpty()) View.VISIBLE else View.INVISIBLE

        binding.rvFollowing.apply {

            val followingAdapter = FollowingAdapter(followingResponse)

            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = followingAdapter

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