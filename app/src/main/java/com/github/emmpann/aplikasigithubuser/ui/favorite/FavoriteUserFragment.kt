package com.github.emmpann.aplikasigithubuser.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.aplikasigithubuser.data.local.database.FavoriteUser
import com.github.emmpann.aplikasigithubuser.data.local.repository.FavoriteUsersRepository
import com.github.emmpann.aplikasigithubuser.databinding.FragmentFavoriteUserBinding

class FavoriteUserFragment : Fragment() {

    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding!!

    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel>() {
        FavoriteUserViewModelFactory(FavoriteUsersRepository(requireActivity().application))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteUsersAdapter()

        adapter.setOnClickCallback(object : FavoriteUsersAdapter.OnClickCallback {
            override fun onItemClick(favUser: FavoriteUser) {
                val toDetailFragment = FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailFragment()
                toDetailFragment.username = favUser.username.toString()
                view.findNavController().navigate(toDetailFragment)
            }
        })

        with (binding) {
            rvFavorite.layoutManager = LinearLayoutManager(requireActivity())
            rvFavorite.adapter = adapter
        }

        favoriteUserViewModel.getAllFavoriteUsers().observe(viewLifecycleOwner) {
            adapter.setListFavoriteUsers(it)
        }
    }
}