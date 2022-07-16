package com.example.githubkusub2.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubkusub2.*
import com.example.githubkusub2.adapter.Adapter
import com.example.githubkusub2.databinding.ActivityMainBinding
import com.example.githubkusub2.model.UserViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val skeleton = binding.skeletonLayout

        binding.edName.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                skeleton.showSkeleton()
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        mainViewModel.getSearchUsers().observe(this, {
            val layoutManager = LinearLayoutManager(this)
            binding.rvItems.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvItems.addItemDecoration(itemDecoration)
            val adapter = Adapter(it)
            binding.rvItems.adapter = adapter
            binding.edName.setText("")
            skeleton.showOriginal()

            adapter.setOnItemClickCallBack(object : Adapter.OnItemClickCallBack {
                override fun onItemClicked(data: ItemsItem) {
                    Intent(this@MainActivity, UserDetailActivity::class.java).also {
                        it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        startActivity(it)
                    }
                }

            })
        })
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

    private fun searchUser() {
        val query = binding.edName.text.toString()
        mainViewModel.setSearchUser(query)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }
}