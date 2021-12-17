package com.example.submission2aplikasigithubusernavigationdanapi.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2aplikasigithubusernavigationdanapi.MainViewModel
import com.example.submission2aplikasigithubusernavigationdanapi.databinding.FragmentFollowersBinding
import com.example.submission2aplikasigithubusernavigationdanapi.model.FollowersResponseItem
import com.example.submission2aplikasigithubusernavigationdanapi.ui.detail.DetailUserActivity


class FollowersFragment : Fragment() {

    var username: String? = null
    private val mainViewModel by viewModels<MainViewModel>()
    private val binding : FragmentFollowersBinding by lazy {
        FragmentFollowersBinding.inflate(layoutInflater)
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(index: Int) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailUserActivity = activity as DetailUserActivity
        username = activity.getMyData()


        mainViewModel.findFollowers(username!!)
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
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showData(followersResponse: List<FollowersResponseItem>) {
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


}