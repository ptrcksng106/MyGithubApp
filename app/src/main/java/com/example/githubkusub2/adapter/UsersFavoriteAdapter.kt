package com.example.githubkusub2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubkusub2.database.UsersFavorite
import com.example.githubkusub2.databinding.ItemUserBinding

class UsersFavoriteAdapter(private val listUsersFavorite: List<UsersFavorite>) :
    RecyclerView.Adapter<UsersFavoriteAdapter.ViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listUsersFavorite[position].avatar_url)
            .circleCrop()
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemName.text = listUsersFavorite[position].login

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(listUsersFavorite[holder.adapterPosition])
        }
    }


    override fun getItemCount(): Int = listUsersFavorite.size

    inner class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: UsersFavorite)
    }
}