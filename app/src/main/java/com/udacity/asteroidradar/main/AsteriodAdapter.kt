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
import com.udacity.asteroidradar.domain.DomainAsteriod

class AsteriodAdapter(val onClickListener: OnClickListener) :
    ListAdapter<DomainAsteriod, AsteriodAdapter.AsteriodViewHolder>(callBackDiff()) {

    class AsteriodViewHolder(val binding: FragmentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: DomainAsteriod) {
            binding.asteriodObj = asteroid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteriodViewHolder {
        val binding: FragmentListItemBinding =
            FragmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteriodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteriodViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListener.onClick(getItem(position))
        }
        holder.bind(getItem(position))
    }

    class callBackDiff : DiffUtil.ItemCallback<DomainAsteriod>() {
        override fun areItemsTheSame(oldItem: DomainAsteriod, newItem: DomainAsteriod): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainAsteriod, newItem: DomainAsteriod): Boolean {
            return oldItem == newItem
        }

    }

    class OnClickListener(val clickListener: (asteroid: DomainAsteriod) -> Unit) {
        fun onClick(asteroid: DomainAsteriod) = clickListener(asteroid)
    }

}