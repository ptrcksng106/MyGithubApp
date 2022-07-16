package com.example.githubkusub2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubkusub2.R
import com.example.githubkusub2.ViewModelFactory
import com.example.githubkusub2.adapter.SectionsPagerAdapter
import com.example.githubkusub2.database.UsersFavorite
import com.example.githubkusub2.databinding.ActivityUserDetailBinding
import com.example.githubkusub2.model.DetailUserViewModel
import com.example.githubkusub2.repository.UsersFavoriteRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    private lateinit var mUsersFavoriteRepository: UsersFavoriteRepository

    private var usersFavorite: UsersFavorite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mUsersFavoriteRepository = UsersFavoriteRepository(application)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

//        view model sebelumnya
//        viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.NewInstanceFactory()
//        ).get(DetailUserViewModel::class.java)

        viewModel = obtainViewModel(this@UserDetailActivity)


        showLoading(true)

        if (username != null) {
            viewModel.setUserDetail(username)
            viewModel.getUserDetail().observe(this, {
                if (it != null) {
                    binding.tvName.text = it.name
                    binding.tvFollowing.text = it.following.toString()
                    binding.tvFollowers.text = it.followers.toString()
                    binding.tvCompany.text = it.company
                    binding.tvLocation.text = it.location
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatarUrl)
                        .circleCrop()
                        .into(binding.ivProfile)
                    binding.tvRepository.text = it.publicRepos.toString()
                    binding.tvUsername.text = it.login
                    showLoading(false)

                    usersFavorite = UsersFavorite(it.login, it.avatarUrl)
                }
            })

            val sectionPagerAdapter = SectionsPagerAdapter(this, bundle)
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()

            var isFavorite = false
            viewModel.checkUserFavorite(username).observe(this) {
                isFavorite = it
                if (isFavorite) {
                    binding.fabLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_favorite_24
                        )
                    )
                } else {
                    binding.fabLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_favorite_border_24
                        )
                    )
                }
            }

            binding.fabLove.setOnClickListener {
                if (isFavorite) {
                    Toast.makeText(this, "Unfavorite", Toast.LENGTH_SHORT).show()
                    usersFavorite?.let { it -> mUsersFavoriteRepository.delete(it) }
                    binding.fabLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_favorite_border_24
                        )
                    )
                    isFavorite = false

                } else {
                    Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
                    usersFavorite?.let { it -> mUsersFavoriteRepository.insert(it) }
                    binding.fabLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_favorite_24
                        )
                    )
                    isFavorite = true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, FavoriteUsersActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.menu2 -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetailActivity.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )

        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }
}