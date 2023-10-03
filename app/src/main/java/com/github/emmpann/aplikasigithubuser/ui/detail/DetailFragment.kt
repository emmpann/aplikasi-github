package com.github.emmpann.aplikasigithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.github.emmpann.aplikasigithubuser.R
import com.github.emmpann.aplikasigithubuser.data.local.database.FavoriteUser
import com.github.emmpann.aplikasigithubuser.data.local.repository.FavoriteUsersRepository
import com.github.emmpann.aplikasigithubuser.databinding.FragmentDetailBinding
import com.github.emmpann.aplikasigithubuser.ui.follow.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(FavoriteUsersRepository(requireActivity().application))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        USERNAME = DetailFragmentArgs.fromBundle(arguments as Bundle).username

        detailViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        detailViewModel.userDetail.observe(requireActivity()) {
            Glide
                .with(requireActivity())
                .load(it.avatarUrl)
                .circleCrop()
                .into(binding.userPhotoProfile)

            with(binding) {
                tvProfileName.text = it.name
                tvUsername.text = USERNAME
                tvFollowers.text = resources.getString(R.string.tv_followers).format(it.followers)
                tvFollowing.text = resources.getString(R.string.tv_following).format(it.following)
            }
        }

        detailViewModel.getFavoriteUserByUsername(USERNAME).observe(viewLifecycleOwner) { favUser ->

            if (favUser != null) {
                binding.addFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.addFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            binding.addFavorite.setOnClickListener {
                val favoriteUser = FavoriteUser()
                //is user favorite
                if (favUser == null) {
                    //no, add favorite user
                    favoriteUser.username = DetailFragmentArgs.fromBundle(arguments as Bundle).username
                    favoriteUser.avatarUrl = detailViewModel.userDetail.value?.avatarUrl
                    detailViewModel.addFavoriteUser(favoriteUser = favoriteUser)
                } else {
                    //yes, delete favorite user
                    detailViewModel.deleteFavoriteUser(favUser)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    companion object {
        var USERNAME = ""
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}