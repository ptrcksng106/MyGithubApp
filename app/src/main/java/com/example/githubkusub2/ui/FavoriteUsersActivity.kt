package com.example.githubkusub2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubkusub2.R
import com.example.githubkusub2.ViewModelFactory
import com.example.githubkusub2.adapter.UsersFavoriteAdapter
import com.example.githubkusub2.database.UsersFavorite
import com.example.githubkusub2.databinding.ActivityFavoriteUsersBinding
import com.example.githubkusub2.model.FavoriteUsersViewModel


class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUsersBinding
    private lateinit var viewModel: FavoriteUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this.application)
        viewModel = ViewModelProvider(
            this,
            factory
        ).get(FavoriteUsersViewModel::class.java)

        viewModel.getSearchUsers().observe(LifecycleOwner { lifecycle }, {
            if (it != null) {

                val layoutManager = LinearLayoutManager(applicationContext)
                binding.rvFavoriteUsers.layoutManager = layoutManager
                val itemDecoration =
                    DividerItemDecoration(applicationContext, layoutManager.orientation)
                binding.rvFavoriteUsers.addItemDecoration(itemDecoration)

                val adapter = UsersFavoriteAdapter(it)
                binding.rvFavoriteUsers.adapter = adapter

                adapter.setOnItemClickCallBack(object : UsersFavoriteAdapter.OnItemClickCallBack {
                    override fun onItemClicked(data: UsersFavorite) {
                        Intent(this@FavoriteUsersActivity, UserDetailActivity::class.java).also {
                            it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                            startActivity(it)
                        }
                    }
                })
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu22 -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

}