package com.github.emmpann.aplikasigithubuser.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.aplikasigithubuser.R
import com.github.emmpann.aplikasigithubuser.data.local.preferences.SettingsPreferences
import com.github.emmpann.aplikasigithubuser.data.local.preferences.dataStore
import com.github.emmpann.aplikasigithubuser.data.remote.response.Item
import com.github.emmpann.aplikasigithubuser.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(SettingsPreferences.getInstance(requireActivity().application.dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.getThemeSettings().observe(viewLifecycleOwner) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // search section
        with (binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionid, event ->
                    searchView.hide()
                    homeViewModel.findUser(searchView.text.toString())
                    false
                }

            searchBar.inflateMenu(R.menu.main_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menuSetting -> {
                        view.findNavController()
                            .navigate(R.id.action_homeFragment_to_settingsFragment)
                        true
                    }

                    R.id.menuFavorite -> {
                        view.findNavController()
                            .navigate(R.id.action_homeFragment_to_favoriteUserFragment)
                        true
                    }

                    else -> false
                }
            }
        }

        // recylcer view setup
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.statusMessage.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        homeViewModel.listUser.observe(viewLifecycleOwner) {listUser ->
            setListUserData(listUser)
        }
    }

    private fun setListUserData(listUser: List<Item>?) {
        val adapter = ListUserAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter
        adapter.setOnClickCallback(object : ListUserAdapter.OnClickCallback {
            override fun onItemClick(item: Item) {
                // move to detail page
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                toDetailFragment.username = item.login
                view?.findNavController()?.navigate(toDetailFragment)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }
}