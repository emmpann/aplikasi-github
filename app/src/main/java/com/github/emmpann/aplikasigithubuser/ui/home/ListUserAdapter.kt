package com.github.emmpann.aplikasigithubuser.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.emmpann.aplikasigithubuser.data.remote.response.Item
import com.github.emmpann.aplikasigithubuser.databinding.ItemRowUserBinding

class ListUserAdapter : ListAdapter<Item, ListUserAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Item) {
            binding.tvProfileName.text = user.login
            Glide.with(binding.root).load(user.avatarUrl).circleCrop().into(binding.photoProfile)
        }
    }

    interface OnClickCallback {
        fun onItemClick(item: Item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }

    private lateinit var onItemClickCallback: OnClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClick(getItem(holder.adapterPosition)) }
    }

    fun setOnClickCallback(onItemClickCallback: OnClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}