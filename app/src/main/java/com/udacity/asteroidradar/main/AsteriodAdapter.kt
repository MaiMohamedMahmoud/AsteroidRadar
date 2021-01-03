package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentListItemBinding
import com.udacity.asteroidradar.domain.Asteroid

class AsteriodAdapter() :
    ListAdapter<Asteroid, AsteriodAdapter.AsteriodViewHolder>(callBackDiff()) {
    class AsteriodViewHolder(val binding: FragmentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteriodObj = asteroid
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteriodViewHolder {
        val binding: FragmentListItemBinding =
            FragmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteriodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteriodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class callBackDiff : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

    }
}