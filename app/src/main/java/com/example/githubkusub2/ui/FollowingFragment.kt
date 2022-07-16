package com.example.githubkusub2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubkusub2.ItemsItem
import com.example.githubkusub2.adapter.Adapter
import com.example.githubkusub2.databinding.FragmentFollowingBinding
import com.example.githubkusub2.model.FollowingViewModel

private var _binding: FragmentFollowingBinding? = null
private val binding get() = _binding!!
private lateinit var viewModel: FollowingViewModel

class FollowingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = arguments
        val username = args?.getString(UserDetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowingBinding.bind(view)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        showLoading(true)

        viewModel.setListFollowingUser(username)
        viewModel.getListFollowingUser().observe(viewLifecycleOwner, {
            if (it != null) {
                showLoading(false)
                val layoutManager = LinearLayoutManager(context)
                binding.rvFragmentFollowing.layoutManager = layoutManager
                val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
                binding.rvFragmentFollowing.addItemDecoration(itemDecoration)

                val adapter = Adapter(it)
                binding.rvFragmentFollowing.adapter = adapter

                adapter.setOnItemClickCallBack(object : Adapter.OnItemClickCallBack {
                    override fun onItemClicked(data: ItemsItem) {

                    }
                })
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}