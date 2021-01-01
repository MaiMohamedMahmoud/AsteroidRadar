package com.udacity.asteroidradar.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.domain.Asteroid

class AsteriodAdapter() :
    ListAdapter<Asteroid, AsteriodAdapter.AsteriodViewHolder>(callBackDiff()) {
    class AsteriodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteriodViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AsteriodViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class callBackDiff : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            TODO("Not yet implemented")
        }

    }
}