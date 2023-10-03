package com.github.emmpann.aplikasigithubuser.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.aplikasigithubuser.data.remote.response.Item
import com.github.emmpann.aplikasigithubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        var position: Int? = 0
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        if (position == 1) { // followers
            viewModel.listFollowers.observe(requireActivity()) {
                showFollowList(it)
            }
        } else { // following
            viewModel.listFollowing.observe(requireActivity()) {
                showFollowList(it)
            }
        }
    }

    private fun showFollowList(userList: List<Item>) {
        val adapter = ListFollowAdapter()
        adapter.submitList(userList)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "username"
    }
}