package com.example.githubkusub2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubkusub2.ItemsItem
import com.example.githubkusub2.databinding.ItemUserBinding

class Adapter(private val listReview: List<ItemsItem>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

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
            .load(listReview[position].avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemName.text = listReview[position].login

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(listReview[holder.adapterPosition])
        }
    }


    override fun getItemCount(): Int = listReview.size

    inner class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: ItemsItem)
    }
}